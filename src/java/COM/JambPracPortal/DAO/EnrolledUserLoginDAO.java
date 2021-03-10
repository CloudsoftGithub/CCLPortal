package COM.JambPracPortal.DAO;

import COM.JambPracPortal.MODEL.login;
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
public class EnrolledUserLoginDAO extends DAO {

    int counter = 0;

    private String emailAddress;

    private String username;

    private String cclno;

    private String dateApplied;

    public String myCCLno;

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

    public void loginMethod(login mylogin) throws SQLException, Exception {
        this.Connector();//established db connection 
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            
             
            Class.forName("com.mysql.jdbc.Driver");
            
            ps = this.getCn().prepareStatement("select username, password, cclno from enrollment where username= ? and password= ? ");
            ps.setString(1, mylogin.getUsername());
            ps.setString(2, mylogin.getPassword());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String username = rs.getString(1);
                String cclNo = rs.getString(3);
                this.myCCLno = cclNo;
                this.cclno = this.myCCLno;
                System.err.println("username Testing: " + username);
                System.err.println("CCL Testing ccl: " + this.cclno);
                ExternalContext redcontext = FacesContext.getCurrentInstance().getExternalContext();
                redcontext.redirect("enrolledUserDashboard.xhtml");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Login! Pls, ensure that you have signedup, then supply correct username and password", "Please, Try Again"));
            }
        } catch (Exception ex) {
            System.out.println("Error in login() -->" + ex.getMessage());
        } finally {
            
             this.Close();
        }
    }

    public void clearVariables(login mylogin) {
        mylogin.setUsername("");
        mylogin.setPassword("");
    }
}
