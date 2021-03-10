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

@ManagedBean
@SessionScoped
public class subjtInfoTable extends DAO {
  private int sid;
  
  private String subject;
  
  private int year;
  
  private int time;
  
  public String intrestedIn;
  
  public String name;
  
  public String email;
  
  public String phone;
  
  public String comment;
  
  public String commentDate;
  
  public int uid;
  
  public int getUid() {
    return this.uid;
  }
  
  public void setUid(int uid) {
    this.uid = uid;
  }
  
  public String getIntrestedIn() {
    return this.intrestedIn;
  }
  
  public void setIntrestedIn(String intrestedIn) {
    this.intrestedIn = intrestedIn;
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
  
  public String getCommentDate() {
    return this.commentDate;
  }
  
  public void setCommentDate(String commentDate) {
    this.commentDate = commentDate;
  }
  
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
  
  public List<subjtInfoTable> getRegisteredSubjects() throws SQLException, Exception {
    List<subjtInfoTable> subjectInfo = new ArrayList<>();
    try {
      Connector();
      PreparedStatement ps = getCn().prepareStatement("select * from clientcomments");
      this.rs = ps.executeQuery();
      while (this.rs.next()) {
        subjtInfoTable tbl = new subjtInfoTable();
        tbl.setUid(this.rs.getInt("UID"));
        tbl.setIntrestedIn(this.rs.getString("interestedIn"));
        tbl.setName(this.rs.getString("name"));
        tbl.setEmail(this.rs.getString("email"));
        tbl.setPhone(this.rs.getString("phone"));
        tbl.setComment(this.rs.getString("comments"));
        tbl.setCommentDate(this.rs.getString("commentDate"));
        subjectInfo.add(tbl);
      } 
    } catch (Exception ex) {
      Logger.getLogger(SubjectRegTableBEAN.class.getName()).log(Level.SEVERE, (String)null, ex);
    } finally {
      this.Close();
    } 
    return subjectInfo;
  }
}
