package COM.JambPracPortal.BEAN;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class SubjectSelectionCheckboxView {
  private List<String> subjects;
  
  private Object[] selFilters;
  
  Connection con;
  
  PreparedStatement ps;
  
  Connection con2;
  
  PreparedStatement ps2;
  
  public String username;
  
  public String subject;
  
  public int sid;
  
  @PostConstruct
  public void init() {
    this.subjects = new ArrayList<>();
    this.subjects.add("ENGLISH LANGUAGE 2004");
    this.subjects.add("USE OF ENGLISH 2005");
    this.subjects.add("UTME ECONOMICS 2012");
    this.subjects.add("COMMERCE 2012");
    this.subjects.add("UTME BIOLOGY 2005");
    this.subjects.add("UTME MATHEMATICS 2013");
    this.subjects.add("GOVERNMENT 2012");
    this.subjects.add("ACCOUNTS 2013");
    this.subjects.add("UTME PHYSICS");
    this.subjects.add("UTME CHEMISTRY");
    this.subjects.add("UTME LITERATURE IN ENGLISH");
    this.subjects.add("UTME CRS");
    this.subjects.add("UTME CRK");
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
  
  public String getUsername() {
    return this.username;
  }
  
  public void setUsername(String username) {
    this.username = username;
  }
  
  public Connection getCon() {
    return this.con;
  }
  
  public void setCon(Connection con) {
    this.con = con;
  }
  
  public PreparedStatement getPs() {
    return this.ps;
  }
  
  public void setPs(PreparedStatement ps) {
    this.ps = ps;
  }
  
  public List<String> getSubjects() {
    return this.subjects;
  }
  
  public void setSubjects(List<String> subjects) {
    this.subjects = subjects;
  }
  
  public void retriveCurrentUsernameFromUI() {
    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    this.username = (String)ec.getRequestParameterMap().get("SubjectSelectionForm:myusername");
  }
}
