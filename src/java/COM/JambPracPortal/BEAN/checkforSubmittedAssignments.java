package COM.JambPracPortal.BEAN;

import COM.JambPracPortal.DAO.DAO;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@SessionScoped
public class checkforSubmittedAssignments extends DAO{
  private int sid;
  
  private String programm;
  
  private String doc;
  
  private String batch;
  
  private String title;
  
  private String description;
  
  private String date;
  
  private StreamedContent file;
  
  private String username;
  
  Statement stmt = null;
  
  PreparedStatement ps;
  
  ResultSet rs;
  
  Connection con;
  
  public int getSid() {
    return this.sid;
  }
  
  public void setSid(int sid) {
    this.sid = sid;
  }
  
  public String getProgramm() {
    return this.programm;
  }
  
  public void setProgramm(String programm) {
    this.programm = programm;
  }
  
  public String getDoc() {
    return this.doc;
  }
  
  public void setDoc(String doc) {
    this.doc = doc;
  }
  
  public String getBatch() {
    return this.batch;
  }
  
  public void setBatch(String batch) {
    this.batch = batch;
  }
  
  public String getTitle() {
    return this.title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public String getDescription() {
    return this.description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  public String getDate() {
    return this.date;
  }
  
  public void setDate(String date) {
    this.date = date;
  }
  
  public StreamedContent getFile() {
    return this.file;
  }
  
  public void setFile(StreamedContent file) {
    this.file = file;
  }
  
  public String getUsername() {
    return this.username;
  }
  
  public void setUsername(String username) {
    this.username = username;
  }
  
  public void retriveProgrammFromUI() {
    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    this.programm = (String)ec.getRequestParameterMap().get("viewAssignmentForm:program");
    System.out.println("TESTING PROGRAM:" + this.programm);
  }
  
  public void retriveDOCFromUI() {
    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    this.doc = (String)ec.getRequestParameterMap().get("viewAssignmentForm:dateofCommencement");
    System.out.println("TESTING DOC:" + this.doc);
  }
  
  public void retriveBatchFromUI() {
    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    this.batch = (String)ec.getRequestParameterMap().get("viewAssignmentForm:Batch");
    System.out.println("TESTING Batch:" + this.batch);
  }
  
  public List<checkforSubmittedAssignments> getAssignments() throws SQLException, Exception {
    List<checkforSubmittedAssignments> messageInfo = new ArrayList<>();
    try {
        this.Connector();
      Class.forName("com.mysql.jdbc.Driver");
       PreparedStatement ps = this.getCn().prepareStatement(" Select * from submit_assignment where program=? AND doc=? AND batch=? order by sid desc ");
      ps.setString(1, this.programm);
      ps.setString(2, this.doc);
      ps.setString(3, this.batch);
      RequestContext.getCurrentInstance().update("viewAssignmentForm:viewAssignmentPanel");
      this.rs = ps.executeQuery();
      while (this.rs.next()) {
        checkforSubmittedAssignments tbl = new checkforSubmittedAssignments();
        tbl.setSid(this.rs.getInt("sid"));
        tbl.setProgramm(this.rs.getString("program"));
        tbl.setBatch(this.rs.getString("batch"));
        tbl.setTitle(this.rs.getString("title"));
        tbl.setDate(this.rs.getString("date"));
        tbl.setUsername(this.rs.getString("username"));
        InputStream stream = this.rs.getBinaryStream("file");
        this.file = (StreamedContent)new DefaultStreamedContent(stream, "application/pdf/doc");
        messageInfo.add(tbl);
      } 
       
    } catch (Exception exception) {
    }finally{
        this.Close();
    }
    return messageInfo;
  }
}
