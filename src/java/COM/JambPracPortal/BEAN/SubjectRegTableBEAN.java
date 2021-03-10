package COM.JambPracPortal.BEAN;

import COM.JambPracPortal.DAO.DAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class SubjectRegTableBEAN extends DAO {
  private int sid;
  
  private String subject;
  
  private int year;
  
  private int time;
  
  public int getSid() {
    return this.sid;
  }
  
  public void setSid(int sid) {
    this.sid = sid;
  }
  
  public String getSubject() {
    return this.subject;
  }
  
  public void setSubject(String subject) {
    this.subject = subject;
  }
  
  public int getYear() {
    return this.year;
  }
  
  public void setYear(int year) {
    this.year = year;
  }
  
  public int getTime() {
    return this.time;
  }
  
  public void setTime(int time) {
    this.time = time;
  }
  
  public void retriveCurrentJambNumberFromUI() {
    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    this.subject = (String)ec.getRequestParameterMap().get("subjtRegForm:Subject");
  }
  
  Statement stmt = null;
  
  PreparedStatement ps;
  
  ResultSet rs;
  
  public List<SubjectRegTableBEAN> getRegisteredSubjects() throws SQLException, Exception {
    List<SubjectRegTableBEAN> subjectInfo = new ArrayList<>();
    try {
      Connector();
      PreparedStatement ps = getCn().prepareStatement("select * from subjectreg");
      this.rs = ps.executeQuery();
      while (this.rs.next()) {
        SubjectRegTableBEAN tbl = new SubjectRegTableBEAN();
        tbl.setSid(this.rs.getInt("sid"));
        tbl.setSubject(this.rs.getString("Subject"));
        tbl.setYear(this.rs.getInt("Year"));
        tbl.setTime(this.rs.getInt("Time"));
        subjectInfo.add(tbl);
      } 
    } catch (Exception ex) {
      Logger.getLogger(SubjectRegTableBEAN.class.getName()).log(Level.SEVERE, (String)null, ex);
    } finally {
      this.Close();
    } 
    return subjectInfo;
  }
  
  public void validate() {
    RequestContext requestContext = RequestContext.getCurrentInstance();
    requestContext.addCallbackParam("sid", Integer.valueOf(this.sid));
  }
}
