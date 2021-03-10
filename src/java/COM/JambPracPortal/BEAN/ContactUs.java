package COM.JambPracPortal.BEAN;

import COM.JambPracPortal.DAO.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.Instant;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

@ManagedBean
@ViewScoped
public class ContactUs extends DAO {

    Connection con;

    PreparedStatement ps;

    public String interestedIn;

    public String name;

    public String email;

    public String phone;

    public String comment;

    public String getInterestedIn() {
        return this.interestedIn;
    }

    public void setInterestedIn(String interestedIn) {
        this.interestedIn = interestedIn;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void retriveCurrentinterestedInFromUI() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        this.interestedIn = (String) ec.getRequestParameterMap().get("commentForm:interestedIn");
    }

    public void retriveCurrentnameFromUI() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        this.name = (String) ec.getRequestParameterMap().get("commentForm:Name");
    }

    public void retriveCurrentemailFromUI() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        this.email = (String) ec.getRequestParameterMap().get("commentForm:email");
    }

    public void retriveCurrentPhoneFromUI() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        this.phone = (String) ec.getRequestParameterMap().get("commentForm:Phone");
    }

    public void retriveCurrentclientCommentFromUI() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        this.comment = (String) ec.getRequestParameterMap().get("commentForm:clientComment");
    }

    public void sendMailMethod() throws Exception {
       
        Class.forName("com.mysql.jdbc.Driver");
        this.Connector();//connection established

        try {
            if (this.phone.length() < 11) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error ClientComment-Phone No", " Pls, Supply a correct phone no. ");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
            } else if (this.phone.length() > 11) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error ClientComment-Phone No", " Pls, Supply a correct phone no. ");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
            } else {
 
                this.ps = this.getCn().prepareStatement("INSERT INTO clientcomments VALUES(?,?,?,?,?,?,now())");
                this.ps.setString(1, null);
                this.ps.setString(2, this.interestedIn);
                this.ps.setString(3, this.name);
                this.ps.setString(4, this.email);
                this.ps.setString(5, this.phone);
                this.ps.setString(6, this.comment);

                this.ps.executeUpdate();

                FacesMessage messages = new FacesMessage(FacesMessage.SEVERITY_INFO, "Client Comments", "Thank you for contacting us!");
                RequestContext.getCurrentInstance().showMessageInDialog(messages);
            }
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error ClientComment", " pls, try again " + e);
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        } finally {
            this.Close();
        }
    }
}
