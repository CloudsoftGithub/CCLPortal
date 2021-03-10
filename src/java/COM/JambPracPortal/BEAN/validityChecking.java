package COM.JambPracPortal.BEAN;

import COM.JambPracPortal.DAO.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

@ManagedBean
@RequestScoped
public class validityChecking extends DAO {

    Connection con = null;

    PreparedStatement ps = null;

    Connection con2 = null;

    PreparedStatement ps2 = null;

    Connection con3 = null;

    PreparedStatement ps3 = null;

    Connection con4 = null;

    PreparedStatement ps4 = null;

    public String username;

    public String password;

    public int myLogcount;

    public String infomation;

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

    public int getMyLogcount() {
        return this.myLogcount;
    }

    public void setMyLogcount(int myLogcount) {
        this.myLogcount = myLogcount;
    }

    public String getInfomation() {
        return this.infomation;
    }

    public void setInfomation(String infomation) {
        this.infomation = infomation;
    }

    int mycounter = 0;

    public void retriveCurrentUsernameFromUI() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        this.username = (String) ec.getRequestParameterMap().get("valityForm:myusername");
    }

    public String loginTrackforFifteenTimesMethod() throws ClassNotFoundException, SQLException, Exception {

        this.Connector();//
        
        retriveCurrentUsernameFromUI();
        this.ps3 = this.getCn().prepareStatement("INSERT INTO logintrack values(null,?)");
        this.ps3.setString(1, this.username);
        this.ps3.executeUpdate();
        RequestContext.getCurrentInstance().update("valityForm:ValidityPanel");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.ps = this.getCn().prepareStatement("select count(*) username from logintrack where username=?");
            this.ps.setString(1, this.username);
            ResultSet rss2 = this.ps.executeQuery();
            while (rss2.next()) {
                int NoofLog = rss2.getInt(1);
                if (NoofLog > 11) {
                    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                    externalContext.redirect("NoOfLoginTracker.xhtml");
                    continue;
                }
                this.infomation = "you are a valid user";
                ExternalContext redcontext = FacesContext.getCurrentInstance().getExternalContext();
                redcontext.redirect("subjectSelection.xhtml");
            }
        } catch (Exception e) {
            System.out.println("Error in login() -->" + e.getMessage());
        } finally {
           this.Close();
        }
        return "";
    }
}
