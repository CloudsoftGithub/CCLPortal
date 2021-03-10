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
public class adminUserCreationBEAN extends DAO {

    Connection con = null;

    PreparedStatement ps = null;

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

    public String adminuserCreationMethod() throws SQLException, IOException, ClassNotFoundException, Exception {

        Class.forName("com.mysql.jdbc.Driver");
        this.Connector();
        
        String myusername = this.username.toString();
        String mypassword = this.password.toString();

        PreparedStatement st1 = this.getCn().prepareStatement("select username from adminusers where username=?");
        st1.setString(1, this.username);
        System.out.println("Username Test: "+ username);
        ResultSet rs = st1.executeQuery();
        
        if (rs.next()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error in adminuser creation. This username has been used. Pls use a different username.", "thank you!"));
        } else {
            try {
                this.ps = this.getCn().prepareStatement("INSERT INTO adminusers VALUES(?,?,?)");
                this.ps.setString(1, null);
                this.ps.setString(2, this.username);
                this.ps.setString(3, this.password);
                this.ps.executeUpdate();
                
                ExternalContext redcontext = FacesContext.getCurrentInstance().getExternalContext();
                redcontext.redirect("adminUserSuccesfullyCreated.xhtml");
                return "";
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid User Creation! Pls, make sure you are a Super Admin, then supply correct credentials", "Please, Try Again" + e));
                return "adminUserCreation.xhtml";
            } finally {
                this.Close();
            }
        }
        return "adminUserCreation.xhtml";
    }
}
