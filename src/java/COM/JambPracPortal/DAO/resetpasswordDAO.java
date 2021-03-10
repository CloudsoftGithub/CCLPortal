package COM.JambPracPortal.DAO;

import COM.JambPracPortal.BEAN.APIKeyClass;
import COM.JambPracPortal.MODEL.resetpassword;
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
import java.sql.SQLException;
import java.time.Instant;
import java.util.Properties;
import java.util.UUID;
import java.util.function.Supplier;
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

public class resetpasswordDAO extends DAO {
  int passwordCounter = 0;
  
  int passwordChange = 0;
  
  String generated_token = "";
  
  String myEmailID = "";
  
  String retrieveEmail;
  
  public String emailAddress;
  
  private String TheRetrievedEmail;
  
  public String getEmailAddress() {
    return this.emailAddress;
  }
  
  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }
  
  public String getTheRetrievedEmail() {
    return this.TheRetrievedEmail;
  }
  
  public void setTheRetrievedEmail(String TheRetrievedEmail) {
    this.TheRetrievedEmail = TheRetrievedEmail;
  }
  
  public void PasswordResetDetailsConfirmation(resetpassword pswrst) throws SQLException, IOException, Exception {
    this.Connector();
    try {
      PreparedStatement st = getCn().prepareStatement("select emailID from enrollment where username=? AND program=?  AND emailID=? ");
      st.setString(1, pswrst.getUsername());
      st.setString(2, pswrst.getProgram());
      st.setString(3, pswrst.getEmail());
      ResultSet rs = st.executeQuery();
      if (rs.next()) {
        this.emailAddress = rs.getString(1);
        pswrst.setEmail(this.emailAddress);
        storeTheUserEmail();
        token_generation();
        sendPasswordResetTokenMail();
        st.close();
        System.out.println("Email Testing :   " + this.emailAddress);
      } else {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error in Password Reset. Invalid information detail, the information supplied is incorrect!.", "Pl, try again!"));
        st.close();
      } 
    } catch (Exception e) {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error in Password Reset. Invalid information detail, the information supplied is incorrect!.", "Pl, try again! "));
    } finally {
      this.Close();
    } 
  }
  
  
      public void sendPasswordResetTokenMail() throws IOException, Exception {
        System.out.println("Im sending to: " + emailAddress);

        Email from = new Email("cloudsoftconsultingltd@gmail.com");
        String subject = "CLOUDSOFT CONSULTING LIMITED Password Reset TOKEN";
        Email toRecipient = new Email(emailAddress);
        //Content content = new Content("text/plain", "\n\n Copy the following token and paste in the space provided: \" + this.generated_token  + "\n");
        Content content = new Content("text/plain", "\n\n Copy the following token and paste in the space provided: \n" + this.generated_token + "\n");
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
      
      public void sendPasswordResetTokenMail() throws IOException, Exception {
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
      mimeMessage.setFrom((Address)new InternetAddress("cloudsoftconsultingltd@gmail.com"));
      mimeMessage.setRecipients(Message.RecipientType.TO, 
          
          (Address[])InternetAddress.parse(toRecipient));
      mimeMessage.setRecipients(Message.RecipientType.BCC, 
          
          (Address[])InternetAddress.parse("cclkd2020@gmail.com"));
      mimeMessage.setSubject("CCL Password Reset TOKEN");
      mimeMessage.setText("\n\n Copy the following token and paste in the space provided: " + this.generated_token + "\n");
      Transport.send((Message)mimeMessage);
      System.out.println("Done! & Thank you.");
      System.out.println("Testing@");
      System.out.println("Email ID1:   " + this.emailAddress);
      System.out.println("Email ID2:   " + this.TheRetrievedEmail);
      System.out.println("TheRetrievedEmail:   " + this.TheRetrievedEmail + "from Try-Block");
    } catch (MessagingException e) {
      e.printStackTrace();
      System.out.println("EMAIL NOT SENT");
      System.out.println("TheRetrievedEmail:   " + this.TheRetrievedEmail + "from CATCH-Block");
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error in E-Mail Messaging. Email has NOT been sent.", "Pls, use a valid email id!"));
    } 
  }
      
      */
  
  public void storeTheUserEmail() throws Exception {
    this.Connector();
    try {
      PreparedStatement st = getCn().prepareStatement("INSERT INTO password_reset_request values(null,?,null,null)");
      st.setString(1, this.emailAddress);
      st.executeUpdate();
      st.close();
    } catch (Exception e) {
      throw e;
    } finally {
      this.Close();
    } 
  }
  
  public void token_generation() throws Exception {
    Supplier<String> tokenSupplier = () -> {
        StringBuilder token = new StringBuilder();
        long currentTimeInMilisecond = Instant.now().toEpochMilli();
        return token.append(currentTimeInMilisecond).append("-").append(UUID.randomUUID().toString()).toString();
      };
    System.out.println(tokenSupplier.get());
    this.generated_token = tokenSupplier.get();
    this.Connector();
    try {
      PreparedStatement st4 = getCn().prepareStatement("UPDATE password_reset_request  SET timestamp= NOW(), tokenhash = ?  WHERE email=? ");
      st4.setString(1, this.generated_token);
      st4.setString(2, this.emailAddress);
      st4.executeUpdate();
      st4.close();
      this.passwordCounter = 1;
      if (this.passwordCounter > 0) {
        ExternalContext redcontext = FacesContext.getCurrentInstance().getExternalContext();
        redcontext.redirect("token_verification.xhtml");
      } else {
        ExternalContext redcontext = FacesContext.getCurrentInstance().getExternalContext();
        redcontext.redirect("forgotpassword.xhtml");
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.Close();
    } 
    this.passwordCounter = 0;
  }
  
  public void verifyToken(resetpassword pswrst) throws Exception {
    this.Connector();
    try {
      PreparedStatement st = getCn().prepareStatement("select tokenhash from password_reset_request where email=? AND tokenhash=? AND timestamp >= date_sub(now(), interval 10 minute) ");
      st.setString(1, pswrst.getEmail());
      st.setString(2, pswrst.getToken());
      System.out.println("Toke-email: " + pswrst.getEmail());
      System.out.println("Toke-email: " + pswrst.getToken());
      ResultSet rs = st.executeQuery();
      if (rs.next()) {
        ExternalContext redcontext = FacesContext.getCurrentInstance().getExternalContext();
        redcontext.redirect("resetpassword_Linkpage.xhtml");
      } else {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, " Invalid token entered, the token supplied is invalid! (The token has lasted for morthern 10 mins )", "Pls, try again!"));
        st.close();
      } 
    } catch (Exception e) {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, " Incorrect token entered, the token supplied is invalid!.", "Pls, try again! "));
    } finally {
      this.Close();
    } 
  }
  
  public void resetPasswordMthd(resetpassword pswrst) throws Exception {
    this.Connector();
    try {
      PreparedStatement st4 = getCn().prepareStatement("UPDATE enrollment  SET password= ?  WHERE emailID=? ");
      st4.setString(1, pswrst.getNewpassword());
      st4.setString(2, pswrst.getEmail());
      st4.executeUpdate();
      this.passwordChange = 1;
      st4.close();
      if (this.passwordChange > 0) {
        try {
          this.Connector();
          PreparedStatement pst1 = getCn().prepareStatement("DELETE FROM password_reset_request WHERE email=? ");
          pst1.setString(1, pswrst.getEmail());
          pst1.executeUpdate();
          pst1.close();
        } catch (Exception e) {
          throw e;
        } finally {
         this.Close();
        } 
        ExternalContext redcontext = FacesContext.getCurrentInstance().getExternalContext();
        redcontext.redirect("successful_password_Reset.xhtml");
      } 
    } catch (Exception e) {
      throw e;
    } finally {
      this.Close();
    } 
    this.passwordChange = 0;
  }
}
