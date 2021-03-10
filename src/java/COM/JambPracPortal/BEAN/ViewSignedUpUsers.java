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

@ManagedBean
@SessionScoped
public class ViewSignedUpUsers extends DAO {
  public int counter;
  
  public String username;
  
  public String password;
  
  public String emailID;
  
  public String pinNo;
  
  public String serialNo;
  
  public String flag;
  
  public String SignedupDate;
  
  public int getCounter() {
    return this.counter;
  }
  
  public void setCounter(int counter) {
    this.counter = counter;
  }
  
  public String getUsername() {
    return this.username;
  }
  
  public void setUsername(String username) {
    this.username = username;
  }
  
  public String getPassword() {
    return this.password;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  public String getEmailID() {
    return this.emailID;
  }
  
  public void setEmailID(String emailID) {
    this.emailID = emailID;
  }
  
  public String getFlag() {
    return this.flag;
  }
  
  public void setFlag(String flag) {
    this.flag = flag;
  }
  
  public String getSignedupDate() {
    return this.SignedupDate;
  }
  
  public void setSignedupDate(String SignedupDate) {
    this.SignedupDate = SignedupDate;
  }
  
  public String getPinNo() {
    return this.pinNo;
  }
  
  public void setPinNo(String pinNo) {
    this.pinNo = pinNo;
  }
  
  public String getSerialNo() {
    return this.serialNo;
  }
  
  public void setSerialNo(String serialNo) {
    this.serialNo = serialNo;
  }
  
  Statement stmt = null;
  
  PreparedStatement ps;
  
  ResultSet rs;
  
  public List<ViewSignedUpUsers> getSignedUpUsersInfo() throws SQLException {
    List<ViewSignedUpUsers> usersInfo = new ArrayList<>();
    try {
      this.Connector();
      PreparedStatement ps = getCn().prepareStatement("select * from signup");
      this.rs = ps.executeQuery();
      while (this.rs.next()) {
        ViewSignedUpUsers tbl = new ViewSignedUpUsers();
        tbl.setCounter(this.rs.getInt("counter"));
        tbl.setUsername(this.rs.getString("username"));
        tbl.setPassword(this.rs.getString("password"));
        tbl.setEmailID(this.rs.getString("emailID"));
        tbl.setSerialNo(this.rs.getString("serialNo"));
        tbl.setPinNo(this.rs.getString("PinNo"));
        tbl.setFlag(this.rs.getString("flag"));
        tbl.setSignedupDate(this.rs.getString("SignedupDate"));
        usersInfo.add(tbl);
      } 
    } catch (Exception ex) {
      Logger.getLogger(viewScratchCards.class.getName()).log(Level.SEVERE, (String)null, ex);
    } 
    return usersInfo;
  }
}
