package COM.JambPracPortal.BEAN;

import COM.JambPracPortal.DAO.DAO;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@ManagedBean
@SessionScoped
public class certApplicationBEAN extends DAO {

    int counter = 0;

    private String emailAddress;

    private String username;

    private String cclno;

    private String dateApplied;

    private String AccounterUsername;

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCclno() {
        return this.cclno;
    }

    public void setCclno(String cclno) {
        this.cclno = cclno;
    }

    public String getDateApplied() {
        return this.dateApplied;
    }

    public void setDateApplied(String dateApplied) {
        this.dateApplied = dateApplied;
    }

    public void retriveCurrentUsernameFromUI() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        this.username = (String) ec.getRequestParameterMap().get("certAppform:myusername");
        System.out.println("ABCD" + this.username);
    }

    public void applyfor_certMthd() throws Exception {

        retriveCurrentUsernameFromUI();
        this.Connector();//

        PreparedStatement st1 = getCn().prepareStatement("select username from cert_application where username=?");
        st1.setString(1, this.username);
        ResultSet rs = st1.executeQuery();
        PreparedStatement st2 = getCn().prepareStatement("select username from enrollment where username=?");
        st2.setString(1, this.username);
        ResultSet rs2 = st2.executeQuery();
        if (rs.next()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error in Cert_application. This username has already applied for a certificate in our database. Pls, check with the company staff", "thank you!"));
        } else if (rs2.next()) {
            String status = "";

            try {

                this.Connector();//

                PreparedStatement st = getCn().prepareStatement("INSERT INTO cert_application values(null,?, Date(Now()), status)");
                st.setString(1, this.username);
                st.executeUpdate();
                st.close();

                counter++;

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "You successfully applied for a certificate! thank you! ", "Details have been sent to your email address"));
                PreparedStatement st3 = getCn().prepareStatement("SELECT emailid,cclno,c.username,dateapplied FROM enrollment e inner join cert_application c ON e.username = c.username WHERE e.username=?");
                st3.setString(1, this.username);
                ResultSet rs3 = st3.executeQuery();

                while (rs3.next()) {
                    String myemail = rs3.getString(1);
                    this.emailAddress = myemail;
                    System.out.println(" Testing the email ID from the while-block:" + myemail + "counter :" + this.counter + "email2: " + this.emailAddress);
                    String mycclNo = rs3.getString(2);
                    this.cclno = mycclNo;
                    String myUsernamer = rs3.getString(3);
                    this.AccounterUsername = myUsernamer;
                    String myDate = rs3.getString(4);
                    this.dateApplied = myDate;
                    st3.close();

                    sendCertApplicationMail();

                    ExternalContext redcontext = FacesContext.getCurrentInstance().getExternalContext();
                    redcontext.redirect("successful_Cert_application.xhtml");
                }
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error in cert_application. Pls, try again ", "thank you!"));
            } finally {
                this.Close();
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error in Cert_application. This username doe NOT exist. Pls, try again", "thank you!"));
            st2.close();
            this.Close();
        }
    }

 public void sendCertApplicationMail() throws IOException, Exception {
        System.out.println("Im sending to: " + emailAddress);

        Email from = new Email("cloudsoftconsultingltd@gmail.com");
        String subject = "CLOUDSOFT CONSULTING LIMITED (CCL) Cert. Application Confirmation)";
        Email toRecipient = new Email(emailAddress);
        Content content = new Content("text/plain", "\n\n Thank you for applying for a certficate at CCL.  \n\n CCL Team will get back to you after verification. \n\n Find below the details used for the application: \nCCL No: " + this.cclno + "\n" + " Username: " + this.AccounterUsername + "\n" + "E-mail id:" + this.emailAddress + "\n" + "Date Applied: " + this.dateApplied + "\n");
         Mail mail = new Mail(from, subject, toRecipient, content);

         
       SendGrid sg = new SendGrid(APIKeyClass.apiKey);
       
        Request request = new Request();

        System.out.println("Testing before sending the mail");

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
            
            System.out.println("SendMail After Sending Testing message: Sendmail is SUccessful");
        } catch (Exception e) {
            throw e;
        }

    }//end of the sendMail method    
 
    public void certApproval() throws Exception {
        int counter2 = 0;

        this.Connector();//
        //CHECKS IF A USER HAS APPLIED FOR A CERTIFICATE 
        PreparedStatement st1 = getCn().prepareStatement("select username from cert_application where username=?");
        st1.setString(1, this.username);
        ResultSet rs = st1.executeQuery();

        //CHECK IF CERT APPROVAL HAS BEEN GRANTED TO A USER (AFTER he HAS APPLIED FOR THE CERT)
        PreparedStatement st2 = getCn().prepareStatement("select username from cert_application where username=? AND status= 'Approved' ");
        st2.setString(1, this.username);
        ResultSet rs3 = st2.executeQuery();
        if (rs3.next()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Information in Cert_application. This username HAS ALREADY applied for cert. and approval is been granted", " Thank you!"));
        } else if (rs.next()) {

            try {
                PreparedStatement st = getCn().prepareStatement("UPDATE cert_application  SET status= 'Approved' WHERE username=? ");
                st.setString(1, this.username);
                st.executeUpdate();
                st.close();

                counter2++;

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cer_Approval Approval", "Your Certificate Request has been approved for " + this.username + " . Details have been sent via the user e-mail."));
                if (counter2 > 0) {
                    sendApprovalMail();
                }
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error Cert_application", "Pls, check your entries " + e));
            } finally {
                this.Close();
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error in Cert_application. This username has NOT applied for a certificate", " Pls, ask him/her apply now. Thank you!"));
            st1.close();
            this.Close();
        }
    }

   
    
     public void sendApprovalMail() throws IOException, Exception {
        System.out.println("Im sending to: " + emailAddress);

        Email from = new Email("cloudsoftconsultingltd@gmail.com");
        String subject = "CLOUDSOFT CONSULTING LIMITED (CCL) Cert. Approval/Invitation for Collection";
        Email toRecipient = new Email(emailAddress);
        Content content = new Content("text/plain", "\n\n Your CCL certificate has been approved. \n\n Find below the details used for the approval: \nCCL No: " + this.cclno + "\n" + " Username: " + this.AccounterUsername + "\n" + "\n" + "E-mail id:" + this.emailAddress + "\n" + "The certificate hard copy will be ready after 7-days. " + "\n" + "Regards." + "\n\n" + "CCL Team" + "\n");
         Mail mail = new Mail(from, subject, toRecipient, content);

         
       SendGrid sg = new SendGrid(APIKeyClass.apiKey);
       
        Request request = new Request();

        System.out.println("Testing before sending the mail");

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
            
            System.out.println("SendMail After Sending Testing message: Sendmail is SUccessful");
        } catch (Exception e) {
            throw e;
        }

    }//end of the sendMail method
    
     
      
}//end of the class
