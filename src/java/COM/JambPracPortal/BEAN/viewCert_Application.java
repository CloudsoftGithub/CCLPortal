package COM.JambPracPortal.BEAN;

import COM.JambPracPortal.DAO.DAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class viewCert_Application extends DAO {
  PreparedStatement ps;
  
  ResultSet rs;
  
  private String fullname;
  
  private String email;
  
  private String phone;
  
  private String program;
  
  private String cclNo;
  
  private String username;
  
  private String dateSigneUp;
  
  private String dateApplied;
  
  private String duration;
  
  public PreparedStatement getPs() {
    return this.ps;
  }
  
  public void setPs(PreparedStatement ps) {
    this.ps = ps;
  }
  
  public ResultSet getRs() {
    return this.rs;
  }
  
  public void setRs(ResultSet rs) {
    this.rs = rs;
  }
  
  public String getFullname() {
    return this.fullname;
  }
  
  public void setFullname(String fullname) {
    this.fullname = fullname;
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
  
  public String getProgram() {
    return this.program;
  }
  
  public void setProgram(String program) {
    this.program = program;
  }
  
  public String getCclNo() {
    return this.cclNo;
  }
  
  public void setCclNo(String cclNo) {
    this.cclNo = cclNo;
  }
  
  public String getUsername() {
    return this.username;
  }
  
  public void setUsername(String username) {
    this.username = username;
  }
  
  public String getDateSigneUp() {
    return this.dateSigneUp;
  }
  
  public void setDateSigneUp(String dateSigneUp) {
    this.dateSigneUp = dateSigneUp;
  }
  
  public String getDateApplied() {
    return this.dateApplied;
  }
  
  public void setDateApplied(String dateApplied) {
    this.dateApplied = dateApplied;
  }
  
  public String getDuration() {
    return this.duration;
  }
  
  public void setDuration(String duration) {
    this.duration = duration;
  }
  
  public List<viewCert_Application> getCert_ApplicatonviewInfo() throws Exception {
    Connector();
    List<viewCert_Application> stockviewInfo = new ArrayList<>();
    try {
      PreparedStatement ps = getCn().prepareStatement("SELECT fullname,emailid,phone,program,c.username,cclno,SignedupDate,dateapplied,DATEDIFF(dateapplied,SignedupDate) FROM enrollment e inner join cert_application c ON e.username = c.username AND status=''");
      this.rs = ps.executeQuery();
      while (this.rs.next()) {
        viewCert_Application tbl = new viewCert_Application();
        tbl.setFullname(this.rs.getString("fullname"));
        tbl.setEmail(this.rs.getString("emailID"));
        tbl.setPhone(this.rs.getString("phone"));
        tbl.setProgram(this.rs.getString("program"));
        tbl.setUsername(this.rs.getString("username"));
        tbl.setCclNo(this.rs.getString("cclNo"));
        tbl.setDateSigneUp(this.rs.getString("SignedupDate"));
        tbl.setDateApplied(this.rs.getString("dateApplied"));
        tbl.setDuration(this.rs.getString(9));
        stockviewInfo.add(tbl);
      } 
      ps.close();
     } catch (Exception e) {
      throw e;
    } finally {
    this.Close();}
    return stockviewInfo;
  }
  
  public List<viewCert_Application> getCert_ApprovalviewInfo() throws Exception {
    Connector();
    List<viewCert_Application> stockviewInfo = new ArrayList<>();
    try {
      PreparedStatement ps = getCn().prepareStatement("SELECT fullname,emailid,phone,program,c.username,cclno,SignedupDate,dateapplied,DATEDIFF(dateapplied,SignedupDate) FROM enrollment e inner join cert_application c ON e.username = c.username AND status='approved'");
      this.rs = ps.executeQuery();
      while (this.rs.next()) {
        viewCert_Application tbl = new viewCert_Application();
        tbl.setFullname(this.rs.getString("fullname"));
        tbl.setEmail(this.rs.getString("emailID"));
        tbl.setPhone(this.rs.getString("phone"));
        tbl.setProgram(this.rs.getString("program"));
        tbl.setUsername(this.rs.getString("username"));
        tbl.setCclNo(this.rs.getString("cclNo"));
        tbl.setDateSigneUp(this.rs.getString("SignedupDate"));
        tbl.setDateApplied(this.rs.getString("dateApplied"));
        tbl.setDuration(this.rs.getString(9));
        stockviewInfo.add(tbl);
      } 
      ps.close();
      Close();
    } catch (Exception e) {
      throw e;
    } finally {
      try {
        this.rs.close();
        this.ps.close();
        Close();
      } catch (Exception e) {
        e.printStackTrace();
        System.err.println("Error while viewing cert-application: " + e.getMessage());
      } 
    } 
    return stockviewInfo;
  }
}
