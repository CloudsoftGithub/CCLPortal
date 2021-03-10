package COM.JambPracPortal.BEAN;

import COM.JambPracPortal.DAO.DAO;
import java.io.IOException;
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

@ManagedBean
@SessionScoped
public class proceedBurronBEAN extends  DAO{
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
  
  public void retriveCurrentUsernameFromUI() {
    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    this.username = (String)ec.getRequestParameterMap().get("ProceedToExamDashboardForm:ProceedToExamDashboardPanel");
  }
  
  public void proceedButtonMethd() throws SQLException, ClassNotFoundException, IOException {
    retriveCurrentUsernameFromUI();
    String myusername = "";
    this.var1 = this.selectedSubjects.get(0);
    this.var2 = this.selectedSubjects.get(1);
    System.out.println("subj1" + this.var1);
    System.out.println("subj1" + this.var2);
    if (this.selectedSubjects.equals("")) {
      ExternalContext redcontext = FacesContext.getCurrentInstance().getExternalContext();
      redcontext.redirect("index.xhtml");
    } else {
      ExternalContext redcontext = FacesContext.getCurrentInstance().getExternalContext();
      redcontext.redirect("index.xhtml");
      System.out.println(" im testing here!");
    } 
  }
  
  public void startTestButtonMethd() throws SQLException, ClassNotFoundException, Exception {
      this.Connector();//
      
    retriveCurrentUsernameFromUI();
    String myusername = "";
    System.out.println(" OK user:" + this.username);
    for (int i = 0; i < this.selectedSubjects.size() - 1; i++) {
      this.var1 = this.selectedSubjects.get(0);
      this.var2 = this.selectedSubjects.get(1);
      try {
        Class.forName("com.mysql.jdbc.Driver");
         this.ps = this.getCn().prepareStatement("INSERT INTO selectedsubjects values(?,?,?) ON DUPLICATE KEY UPDATE Subject1=VALUES(Subject1),Subject2=VALUES(Subject2)");
        this.ps.setString(1, this.username);
        this.ps.setString(2, this.var1);
        this.ps.setString(3, this.var2);
        this.ps.executeUpdate();
        this.Close();
        ExternalContext redcontext = FacesContext.getCurrentInstance().getExternalContext();
        redcontext.redirect("ExamsDashboard.xhtml");
      } catch (Exception e) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error selecting subjects pls, try again ", "thank you!" + e
              
              .getMessage()));
      } finally {
      this.Close();
      }
    } 
  }
}
