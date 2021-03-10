package COM.JambPracPortal.BEAN;

import COM.JambPracPortal.DAO.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class RetrieveCCLnoBEAN extends DAO {

    Connection con = null;

    PreparedStatement ps = null;

    private String username;

    private String cclno;

    private String emailAddress;

    private String fullname;

    public String getFullname() {
        return this.fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void retriveUserNameFromUI() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        this.username = (String) ec.getRequestParameterMap().get("RetrieveCCLNoform:myusername");
    }

    public void retrieveCCLNOMethod() throws Exception {
        this.Connector();//

        retriveUserNameFromUI();//

        this.cclno = "";
        this.fullname = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.ps = this.getCn().prepareStatement("select cclNo, fullname from enrollment where username= ? ");
            this.ps.setString(1, this.username);
            ResultSet rs = this.ps.executeQuery();
            if (rs.next()) {
                this.cclno = rs.getString(1);
                this.fullname = rs.getString(2);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, " Invalid username. Pls, supply a correct username. ", " Thank you."));

            }
            this.username = "";
        } catch (Exception e) {
            throw e;
        } finally {
            this.ps.close();
            this.Close();
        }
    }
}
