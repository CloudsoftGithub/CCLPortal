package COM.JambPracPortal.BEAN;

import COM.JambPracPortal.DAO.DAO;
import java.io.InputStream;
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
public class checkforMaterials extends DAO {
  private int mid;
  
  private String programm;
  
  private String doc;
  
  private String batch;
  
  private String title;
  
  private String description;
  
  private String date;
  
  private StreamedContent file;
  
  Statement stmt = null;
  
  PreparedStatement ps;
  
  ResultSet rs;
  
  public int getMid() {
    return this.mid;
  }
  
  public void setMid(int mid) {
    this.mid = mid;
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
  
  public void retriveProgrammFromUI() {
    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    this.programm = (String)ec.getRequestParameterMap().get("viewMaterialForm:program");
    System.out.println("TESTING PROGRAM:" + this.programm);
  }
  
  public void retriveDOCFromUI() {
    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    this.doc = (String)ec.getRequestParameterMap().get("viewMaterialForm:dateofCommencement");
    System.out.println("TESTING DOC:" + this.doc);
  }
  
  public void retriveMaterialNoFromUI() {
    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    this.title = (String)ec.getRequestParameterMap().get("viewMaterialForm:MaterialTitle");
    System.out.println("TESTING title:" + this.title);
  }
  
  public List<checkforMaterials> getMaterials() throws SQLException, Exception {
    List<checkforMaterials> messageInfo = new ArrayList<>();
    try {
      Connector();
      PreparedStatement ps = getCn().prepareStatement(" Select * from material where program=? AND doc=? AND batch=? AND title=? ");
      ps.setString(1, this.programm);
      ps.setString(2, this.doc);
      ps.setString(3, this.batch);
      ps.setString(4, this.title);
      RequestContext.getCurrentInstance().update("viewMaterialForm:viewMaterialPanel");
      this.rs = ps.executeQuery();
      while (this.rs.next()) {
        checkforMaterials tbl = new checkforMaterials();
        tbl.setMid(this.rs.getInt("mid"));
        tbl.setProgramm(this.rs.getString("program"));
        tbl.setBatch(this.rs.getString("batch"));
        tbl.setTitle(this.rs.getString("title"));
        tbl.setDescription(this.rs.getString("description"));
        tbl.setDate(this.rs.getString("date"));
        InputStream stream = this.rs.getBinaryStream("file");
        this.file = (StreamedContent)new DefaultStreamedContent(stream, "application/pdf");
        messageInfo.add(tbl);
      } 
     } catch (Exception e) {
         throw e;
     }finally{
        this.Close();
    }
    return messageInfo;
  }
}
