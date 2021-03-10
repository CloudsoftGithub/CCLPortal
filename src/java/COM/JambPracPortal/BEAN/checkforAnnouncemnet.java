package COM.JambPracPortal.BEAN;

import COM.JambPracPortal.DAO.DAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class checkforAnnouncemnet extends DAO {
  private int aid;
  
  private String announcement;
  
  private String publicizedDate;
  
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
  
  public String getPublicizedDate() {
    return this.publicizedDate;
  }
  
  public void setPublicizedDate(String publicizedDate) {
    this.publicizedDate = publicizedDate;
  }
  
  Statement stmt = null;
  
  PreparedStatement ps;
  
  ResultSet rs;
  
  public List<checkforAnnouncemnet> getPublicizedAnnouncement() throws SQLException, Exception {
    List<checkforAnnouncemnet> AnnoucementInfo = new ArrayList<>();
    try {
      this.Connector();
      PreparedStatement ps = getCn().prepareStatement("select * from announcements order by aid Desc ");
      this.rs = ps.executeQuery();
      while (this.rs.next()) {
        checkforAnnouncemnet tbl = new checkforAnnouncemnet();
        tbl.setAid(this.rs.getInt("aid"));
        tbl.setAnnouncement(this.rs.getString("announcement"));
        tbl.setPublicizedDate(this.rs.getString("publicizedDate"));
        AnnoucementInfo.add(tbl);
      } 
    } catch (Exception exception) {
    }finally{
        this.Close();
    }
    return AnnoucementInfo;
  }
}
