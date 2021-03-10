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

@ManagedBean
@SessionScoped
public class announcementBEAN extends DAO {
  public int aid;
  
  public String announcement;
  
  public String DatePublisized;
  
  public String retrieveaccouncement;
  
  Statement stmt = null;
  
  PreparedStatement ps;
  
  ResultSet rs;
  
  public int getAid() {
    return this.aid;
  }
  
  public void setAid(int aid) {
    this.aid = aid;
  }
  
  public String getAnnouncement() {
    return this.announcement;
  }
  
  public void setAnnouncement(String announcement) {
    this.announcement = announcement;
  }
  
  public String getDatePublisized() {
    return this.DatePublisized;
  }
  
  public void setDatePublisized(String DatePublisized) {
    this.DatePublisized = DatePublisized;
  }
  
  public String getRetrieveaccouncement() {
    return this.retrieveaccouncement;
  }
  
  public void setRetrieveaccouncement(String retrieveaccouncement) {
    this.retrieveaccouncement = retrieveaccouncement;
  }
  
  public void publicizeAnnouncement() throws SQLException, Exception {
   this.Connector();//
   
    PreparedStatement st1 = getCn().prepareStatement("select announcement from announcements where announcement=?");
    st1.setString(1, this.announcement);
    ResultSet rs1 = st1.executeQuery();
    if (rs1.next()) {
      FacesMessage messag = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Announcement Error", "This announcement has been publicized. Please, check your entries.");
      RequestContext.getCurrentInstance().showMessageInDialog(messag);
      //Close();
    } else {
        
      try {
        Connector();//
        PreparedStatement st = getCn().prepareStatement("INSERT INTO announcements values(null,?, Date(Now()))");
        st.setString(1, this.announcement);
        st.executeUpdate();
        st.close();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "You succcessfully Publicized an announcement ", "to the public"));
      } catch (Exception e) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error in announcement pls, try again ", "  thank you!" + e));
      } finally{
          this.Close();
      }
    } 
  }
  
  public void viewAnnouncement() throws SQLException, Exception {
      this.Connector();
    this.retrieveaccouncement = "";
    //Connector();
    PreparedStatement st1 = getCn().prepareStatement("select announcement from announcements where aid=?");
    st1.setInt(1, this.aid);
    ResultSet rs1 = st1.executeQuery();
    if (rs1.next()) {
      this.retrieveaccouncement = rs1.getString(1);
      RequestContext.getCurrentInstance().update("AnnouncementForm:AnnouncementPanel");
    } else {
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "announcement View Error", "the announcement ID entered is invalid.  Pls, check and try again. ");
      RequestContext.getCurrentInstance().showMessageInDialog(message);
    } 
  }
  
  public void editAnnouncement() throws Exception {
    try {
      this.Connector();//
      PreparedStatement preparedStatement = getCn().prepareStatement("UPDATE announcements SET announcement=? where aid=?");
      preparedStatement.setString(1, this.retrieveaccouncement);
      preparedStatement.setInt(2, this.aid);
      preparedStatement.executeUpdate();
      RequestContext.getCurrentInstance().update("AnnouncementForm:AnnouncementPanel");
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "announcement editing Info", "you successfully edited the announcement, thank you!");
      RequestContext.getCurrentInstance().showMessageInDialog(message);
    } catch (Exception e) {
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "announcement editing Error", "pls, read this error meassage :" + e);
      RequestContext.getCurrentInstance().showMessageInDialog(message);
    } 
    this.retrieveaccouncement = "";
    
    PreparedStatement st1 = getCn().prepareStatement("select announcement from announcements where aid=?");
    st1.setInt(1, this.aid);
    ResultSet rs1 = st1.executeQuery();
    if (rs1.next()) {
      this.retrieveaccouncement = rs1.getString(1);
      RequestContext.getCurrentInstance().update("AnnouncementForm:AnnouncementPanel");
    } 
    this.Close();
  }
  
}
