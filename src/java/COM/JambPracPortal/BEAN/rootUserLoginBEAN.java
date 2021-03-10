package COM.JambPracPortal.BEAN;

import COM.JambPracPortal.DAO.DAO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class rootUserLoginBEAN extends DAO {

    private String username;

    private String password;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String rootLoginMethod() throws SQLException, IOException, Exception {
        int rootLoginFlag = 0;
        try {

            Connection con = null;
            PreparedStatement ps = null;
            String myusername = this.username.toString();
            String mypassword = this.password.toString();

            //Jealastic db url
           // con = DriverManager.getConnection("jdbc:mysql://node52761-cloudsoft.w1-us.cloudjiffy.net/jambpracportal?user=" + myusername + "&" + "password=" + mypassword);
                
            //Localhost db url
           con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jambpracportal?user=" + myusername + "&" + "password=" + mypassword);

            rootLoginFlag++;
            if (rootLoginFlag > 0 && this.username.equalsIgnoreCase("root")) {
                ExternalContext redcontext = FacesContext.getCurrentInstance().getExternalContext();
                redcontext.redirect("adminUserCreation.xhtml");
            } else if (rootLoginFlag > 0 && !this.username.equalsIgnoreCase("root")) {
                ExternalContext redcontext = FacesContext.getCurrentInstance().getExternalContext();
                redcontext.redirect("adminDashboard.xhtml");
            }
            return "";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Login! Pls, make sure you are an Admin/Root User, then supply correct credentials", "Please, Try Again"));
            return "rootUserLogin.xhtml";
        } finally {
            //
        }
    }

    public void adminUserLogin() throws Exception {
        this.Connector();
        try {
            PreparedStatement st1 = getCn().prepareStatement("select* from adminusers where username=? AND password=?");
            st1.setString(1, this.username);
            st1.setString(2, this.password);
            ResultSet rs = st1.executeQuery();
            if (rs.next()) {
                if (this.username.startsWith("dataentry_officer")) {
                    ExternalContext redcontext = FacesContext.getCurrentInstance().getExternalContext();
                    redcontext.redirect("adminDashboard.xhtml");
                } else if (this.username.startsWith("secretary")) {
                    ExternalContext redcontext = FacesContext.getCurrentInstance().getExternalContext();
                    redcontext.redirect("CCLadminUserDashboard.xhtml");
                } else if (this.username.startsWith("Lecturer")) {
                    ExternalContext redcontext = FacesContext.getCurrentInstance().getExternalContext();
                    redcontext.redirect("lecturerDashboard.xhtml");
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Login! Pls, make sure you are an Admin User, then supply correct credentials", "Please, Try Again"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.Close();
        }
        this.username = "";
    }

    public String signoutMethod() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.username = "";
        return "";
    }
}
