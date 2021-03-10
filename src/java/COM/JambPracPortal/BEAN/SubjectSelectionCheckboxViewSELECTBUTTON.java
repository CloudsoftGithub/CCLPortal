package COM.JambPracPortal.BEAN;

import COM.JambPracPortal.DAO.DAO;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class SubjectSelectionCheckboxViewSELECTBUTTON extends DAO implements Serializable {

    Connection con;

    PreparedStatement ps;

    Connection con2;

    PreparedStatement ps2;

    private List<String> selectedSubjects;

    public String username;

    public String subject;

    public int sid;

    public String var1 = "";

    public String var2 = "";

    public String mySelectedSubject;

    public String getMySelectedSubject() {
        return this.mySelectedSubject;
    }

    public void setMySelectedSubject(String mySelectedSubject) {
        this.mySelectedSubject = mySelectedSubject;
    }

    public String getVar1() {
        return this.var1;
    }

    public void setVar1(String var1) {
        this.var1 = var1;
    }

    public String getVar2() {
        return this.var2;
    }

    public void setVar2(String var2) {
        this.var2 = var2;
    }

    public List<String> getSelectedSubjects() {
        return this.selectedSubjects;
    }

    public void setSelectedSubjects(List<String> selectedSubjects) {
        this.selectedSubjects = selectedSubjects;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getSid() {
        return this.sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public void retriveCurrentUsernameFromUI() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        this.username = (String) ec.getRequestParameterMap().get("SubjectSelectionForm:myusername");
    }

    public void selectSubjttButtonMethd() throws SQLException, ClassNotFoundException, IOException {
        retriveCurrentUsernameFromUI();
        String myusername = "";
        if (this.selectedSubjects.size() > 2) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "No. of selected Subject", "Pls, DO NOT select morethan two(2) subjects at a time! ");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        } else if (this.selectedSubjects.size() < 2) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "No. of selected Subject Error", "Pls, select exactly two subject before you proceed!");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        } else {
            RequestContext.getCurrentInstance().update("SubjectSelectionForm:jmbcomb");
            ExternalContext redcontext = FacesContext.getCurrentInstance().getExternalContext();
            redcontext.redirect("ProceedExamDashboard.xhtml");
        }
    }

    public void retriveUserSelectedSubjFromUI() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        this.mySelectedSubject = (String) ec.getRequestParameterMap().get("SubjectSelectionForm:checkBoxPanel");
    }

    public void startTestButtonMethd() throws SQLException, ClassNotFoundException, Exception {
        this.Connector();//
        
        retriveCurrentUsernameFromUI();
        String myusername = "";
        String myvar1 = "";
        String myvar2 = "";
        for (int i = 0; i < this.selectedSubjects.size() - 1; i++) {
            this.var1 = this.selectedSubjects.get(0);
            this.var2 = this.selectedSubjects.get(1);
        }
        if (this.selectedSubjects.size() > 2) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "No. of selected Subject", "Pls, DO NOT select morethan two(2) subjects at a time! ");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        } else if (this.selectedSubjects.size() < 2) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "No. of selected Subject Error", "Pls, select exactly two subject before you proceed!");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        } else {
            myvar1 = ((String) this.selectedSubjects.get(0)).trim();
            myvar2 = ((String) this.selectedSubjects.get(1)).trim();
            System.out.println(" my code test for the two subts:" + myvar1 + myvar2);
            try {
                Class.forName("com.mysql.jdbc.Driver");
                this.ps = this.getCn().prepareStatement("INSERT INTO selectedsubjects values(?,?,?) ON DUPLICATE KEY UPDATE Subject1=VALUES(Subject1),Subject2=VALUES(Subject2)");
                this.ps.setString(1, this.username);
                this.ps.setString(2, myvar1);
                this.ps.setString(3, myvar2);
                this.ps.executeUpdate();
                this.ps.close();
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "succeful Subject Selection", " successful subjts selection, you will be redirected ");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                ExternalContext redcontext = FacesContext.getCurrentInstance().getExternalContext();
                redcontext.redirect("ExamsDashboard.xhtml");
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error selecting subjects pls, try again ", "thank you!" + e
                        .getMessage()));
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error selecting subjects", e.getMessage() + "");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
            } finally {
                this.Close();//close connection 

            }
        }
    }
}
