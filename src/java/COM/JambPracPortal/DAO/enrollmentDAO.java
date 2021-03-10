package COM.JambPracPortal.DAO;

import COM.JambPracPortal.BEAN.APIKeyClass;
import COM.JambPracPortal.MODEL.enrollment;
import com.sendgrid.*;

import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import javax.faces.application.FacesMessage;
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

public class enrollmentDAO extends DAO implements Serializable {

    String myThisCCLNO;

    private Calendar calendar;

    int counter = 0;

    private String TheRetrievedlNo;

    private String TheRetrievedUsername;

    private String TheRetrievedProgram;

    private String TheRetrievedPhone;

    private String TheRetrievedEmail;

    private String TheRetrievedPassword;

    public String emailAddress;

    public String userName;

    public String Rpassword;

    public String phone;

    public String cclPNo;

    public String getTheRetrievedlNo() {
        return this.TheRetrievedlNo;
    }

    public void setTheRetrievedlNo(String TheRetrievedlNo) {
        this.TheRetrievedlNo = TheRetrievedlNo;
    }

    public String getTheRetrievedUsername() {
        return this.TheRetrievedUsername;
    }

    public void setTheRetrievedUsername(String TheRetrievedUsername) {
        this.TheRetrievedUsername = TheRetrievedUsername;
    }

    public String getTheRetrievedProgram() {
        return this.TheRetrievedProgram;
    }

    public void setTheRetrievedProgram(String TheRetrievedProgram) {
        this.TheRetrievedProgram = TheRetrievedProgram;
    }

    public String getTheRetrievedEmail() {
        return this.TheRetrievedEmail;
    }

    public void setTheRetrievedEmail(String TheRetrievedEmail) {
        this.TheRetrievedEmail = TheRetrievedEmail;
    }

    public String getTheRetrievedPassword() {
        return this.TheRetrievedPassword;
    }

    public void setTheRetrievedPassword(String TheRetrievedPassword) {
        this.TheRetrievedPassword = TheRetrievedPassword;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRpassword() {
        return this.Rpassword;
    }

    public void setRpassword(String Rpassword) {
        this.Rpassword = Rpassword;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCclPNo() {
        return this.cclPNo;
    }

    public void setCclPNo(String cclPNo) {
        this.cclPNo = cclPNo;
    }

    public void enrolmentMthd(enrollment enrllmnt) throws Exception {
        Connector();
        PreparedStatement st2 = getCn().prepareStatement("select serialNo,PinNo from enrollment where serialNo=? AND PinNo=?");
        st2.setString(1, enrllmnt.getSerialNo());
        st2.setString(2, enrllmnt.getPinNo());
        ResultSet rs2 = st2.executeQuery();
        PreparedStatement st1 = getCn().prepareStatement("select username from enrollment where username=?");
        st1.setString(1, enrllmnt.getUsername());
        ResultSet rs = st1.executeQuery();
        PreparedStatement st6 = getCn().prepareStatement("select username from enrollment where emailID=?");
        st6.setString(1, enrllmnt.getEmail());
        ResultSet rs6 = st6.executeQuery();
        if (rs2.next()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error in enrollment invalid scratch card detail, this card has been used.", "thank you!"));
            st1.close();
        } else if (rs.next()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error in enrollment This username has been used, pls use a different username", "thank you!"));
            Close();
            st1.close();
        } else if (rs6.next()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error in enrollment This Email ID has been used, pls use a different email ID", "thank you!"));
            this.Close();
            st1.close();
        } else {
            this.Connector();//
            this.calendar = new GregorianCalendar();
            int cal = this.calendar.get(1);
            int month = this.calendar.get(2);
            String myflag = "used";
            String myCCLNo = "CCL/" + enrllmnt.getProgram() + "/" + cal + "/";
            PreparedStatement st3 = getCn().prepareStatement("select serialNo,PinNo from enrolment_scratchcard where serialNo=? AND PinNo=?");
            st3.setString(1, enrllmnt.getSerialNo());
            st3.setString(2, enrllmnt.getPinNo());
            ResultSet rs3 = st3.executeQuery();
            if (rs3.next()) {
                try {
                    Connector();
                    PreparedStatement st = getCn().prepareStatement("INSERT INTO enrollment values(null,?,?,?,?,?,?,?,?,?,?, Date(Now()))");
                    st.setString(1, enrllmnt.getFullname());
                    st.setString(2, enrllmnt.getEmail());
                    st.setString(3, enrllmnt.getPhone());
                    st.setString(4, enrllmnt.getProgram());
                    st.setString(5, myCCLNo);
                    st.setString(6, enrllmnt.getUsername());
                    st.setString(7, enrllmnt.getPassword());
                    st.setString(8, enrllmnt.getPinNo());
                    st.setString(9, enrllmnt.getSerialNo());
                    st.setString(10, myflag);
                    st.executeUpdate();
                    st.close();
                    this.emailAddress = enrllmnt.getEmail();
                    this.userName = enrllmnt.getUsername();
                    this.Rpassword = enrllmnt.getPassword();
                    this.phone = enrllmnt.getPhone();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "You succcessfully Enrolled! thank you! ", "you can use your credentials to log in to the LMS"));
                    ExternalContext redcontext = FacesContext.getCurrentInstance().getExternalContext();
                    redcontext.redirect("successfulEnrollment.xhtml");
                    this.counter++;
                    if (this.counter > 0) {
                        Connector();
                        try {
                            PreparedStatement st4 = getCn().prepareStatement("UPDATE enrollment  SET cclNo= CONCAT(cclNo,counter) WHERE username=? ");
                            st4.setString(1, enrllmnt.getUsername());
                            st4.executeUpdate();
                            st4.close();
                        } catch (Exception e) {
                            throw e;
                        }
                        try {
                            Connector();
                            PreparedStatement st5 = getCn().prepareStatement("SELECT cclNo FROM enrollment WHERE username=? ");
                            st5.setString(1, enrllmnt.getUsername());
                            ResultSet rs4 = st5.executeQuery();
                            while (rs4.next()) {
                                this.myThisCCLNO = rs4.getString(1);
                                this.cclPNo = this.myThisCCLNO;
                                System.out.println(" CCLNOTEST: After Update" + this.cclPNo + this.myThisCCLNO);
                            }
                            st5.close();
                            this.Close();
                        } catch (Exception e) {
                            throw e;
                        }
                    }

                    sendMail();//invoked the method

                } catch (Exception e) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error in enrollment Pls, try again ", "thank you!" + e));
                } finally {
                    Close();
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Enrollment Error! invalid elrollment-scratch card detail, pls obtain your card from the company officials or  call: +234 9038605224 for help ", "thank you"));
                st1.close();
                this.Close();
            }
        }
    }

    public void retrievingCClNoMethd(enrollment enrllmnt) throws Exception {
        Connector();
        try {
            PreparedStatement st = getCn().prepareStatement("SELECT cclNo,username,program,phone,emailID,password FROM enrollment WHERE username=? ");
            st.setString(1, enrllmnt.getUsername());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                this.TheRetrievedlNo = rs.getString(1);
                enrllmnt.setCclNo(this.TheRetrievedlNo);
                this.TheRetrievedUsername = rs.getString(2);
                enrllmnt.setUsername(this.TheRetrievedUsername);
                this.TheRetrievedProgram = rs.getString(3);
                enrllmnt.setProgram(this.TheRetrievedProgram);
                this.TheRetrievedPhone = rs.getString(4);
                enrllmnt.setPhone(this.TheRetrievedPhone);
                this.TheRetrievedEmail = rs.getString(5);
                enrllmnt.setEmail(this.TheRetrievedEmail);
                this.TheRetrievedPassword = rs.getString(6);
                enrllmnt.setEmail(this.TheRetrievedPassword);
            }
            st.close();
            Close();
        } catch (Exception e) {
            throw e;
        }
    }


    public void sendMail() throws IOException, Exception {
        System.out.println("Im sending to: " + emailAddress);

        Email from = new Email("cloudsoftconsultingltd@gmail.com");
        String subject = "CLOUDSOFT CONSULTING LIMITED (CCL) Enrollment Details (Please, read and keep it saved...)";
        Email toRecipient = new Email(emailAddress);
        Content content = new Content("text/plain", "\n\n Congratulations for your enrollment into Cloudsoft Consulting Limited (CCL) program. \n\n Find below the details about your CCL account: \nCCL No: " + this.myThisCCLNO + "\n" + " Username: " + this.userName + "\n" + " Password: " + this.Rpassword + "\n" + "E-mail id:" + this.emailAddress + "\n");
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

    /*
    public void sendMail() throws IOException, Exception {
        String username = "cloudsoftconsultingltd@gmail.com";
        String password = "bukbukbuk123@";
        String toRecipient = this.emailAddress;
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        Session session = Session.getInstance(prop, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("cloudsoftconsultingltd@gmail.com", "bukbukbuk123@");
            }
        });
        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom((Address) new InternetAddress("cloudsoftconsultingltd@gmail.com"));
            mimeMessage.setRecipients(Message.RecipientType.TO,
                    (Address[]) InternetAddress.parse(toRecipient));
            mimeMessage.setRecipients(Message.RecipientType.BCC,
                    (Address[]) InternetAddress.parse("cclkd2020@gmail.com"));
            mimeMessage.setSubject("CCL Enrollment Details (Please, read and keep it save...)");
            mimeMessage.setText("\n\n Congratulations for your enrollment into Cloudsoft Consulting Limited (CCL) program. \n\n Find below the details about your CCL account: \nCCL No: " + this.myThisCCLNO + "\n" + " Username: " + this.userName + "\n" + " Password: " + this.Rpassword + "\n" + "E-mail id:" + this.emailAddress + "\n");
            Transport.send((Message) mimeMessage);
            System.out.println("Done! & Thank you.");
            System.out.println("Testing@");
            System.out.println("Email ID1:   " + this.emailAddress);
            System.out.println("Email ID2:   " + this.TheRetrievedEmail);
            System.out.println("UserName:   " + this.userName + "from Try-Block");
            System.out.println("UserName:   CCLNO: " + this.myThisCCLNO + "from Try-Block");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("EMAIL NOT SENT");
            System.out.println("Email ID1:   " + this.emailAddress);
            System.out.println("Email ID2:   " + this.TheRetrievedEmail);
            System.out.println("UserName:   " + this.userName + "\n" + "CCL No: " + this.cclPNo + this.TheRetrievedlNo + "\n" + "from Catch-Block");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error in E-Mail Messaging Email has NOT been sent", "Pls, use a valid email id!"));
        }
    }
     */
}//end of the class 
