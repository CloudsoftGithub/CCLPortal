package COM.JambPracPortal.BEAN;

import COM.JambPracPortal.DAO.DAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;

@ManagedBean
@SessionScoped
public class MaterialBEAN extends DAO {
  public int mid;
  
  public String programm;
  
  public String doc;
  
  private String batch;
  
  public String title;
  
  public String description;
  
  private UploadedFile materialFile;
  
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
  
  public UploadedFile getMaterialFile() {
    return this.materialFile;
  }
  
  public void setMaterialFile(UploadedFile materialFile) {
    this.materialFile = materialFile;
  }
  
  public void sendMaterial() throws SQLException, Exception {
    Connector();
    PreparedStatement st1 = getCn().prepareStatement("select title from material where program=? AND doc=? AND batch=? AND title=?");
    st1.setString(1, this.programm);
    st1.setString(2, this.doc);
    st1.setString(3, this.batch);
    st1.setString(4, this.title);
    ResultSet rs1 = st1.executeQuery();
    if (rs1.next()) {
      FacesMessage messag = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Material Posting Error", "This 'Material TITLE' has been used. Please, select a different title.");
      RequestContext.getCurrentInstance().showMessageInDialog(messag);
 
    } else {
        
      try {
        Connector();
        if (!this.materialFile.getFileName().equalsIgnoreCase("")) {
          PreparedStatement st = getCn().prepareStatement("INSERT INTO material values(null,?,?,?,?,?, Date(Now()),?)");
          st.setString(1, this.programm);
          st.setString(2, this.doc);
          st.setString(3, this.batch);
          st.setString(4, this.title);
          st.setString(5, this.description);
          st.setBinaryStream(6, this.materialFile.getInputstream());
          RequestContext.getCurrentInstance().update("uploadMaterialForm:uploadMaterialPanel");
          st.executeUpdate();
          st.close();
          this.Close();
          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, this.materialFile
                
                .getFileName() + " successfully sent, " + "with title: " + this.title, "Thank you."));
          clear();
        } else {
          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error in file upload.Pls, supply a PDF file for the Material ", "  thank you!"));
        } 
      } catch (Exception e) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error in Sending Material. pls, try again ", "  thank you!" + e));
      } finally{
          this.Close();
      }
    } 
  }
  
  public void clear() {
    this.programm = "";
    this.doc = "";
    this.batch = "";
    this.title = "";
    this.description = "";
    this.materialFile = null;
  }
}
