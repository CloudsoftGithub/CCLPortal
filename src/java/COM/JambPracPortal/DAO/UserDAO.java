package COM.JambPracPortal.DAO;

import COM.JambPracPortal.MODEL.signup;
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
public class UserDAO extends DAO {

    public void loginMthd(signup sigin) throws SQLException, Exception {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
             
            this.Connector();

            ps = this.getCn().prepareStatement("select username, password from signup where username= ? and password= ? ");
            ps.setString(1, sigin.getUsername());
            ps.setString(2, sigin.getPassword());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ExternalContext redcontext = FacesContext.getCurrentInstance().getExternalContext();
                redcontext.redirect("userValidity.xhtml");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Login! pls, make sure you signedup, then supply correct username and password", "Please, Try Again"));
            }
        } catch (Exception ex) {
            System.out.println("Error in login() -->" + ex.getMessage());
        } finally {

            ps.close();
            this.Close();
        }
    }
}
