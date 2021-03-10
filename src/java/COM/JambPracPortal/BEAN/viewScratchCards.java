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
public class viewScratchCards extends DAO {
  public String pinNo;
  
  public String serialNo;
  
  public int uid;
  
  public int getUid() {
    return this.uid;
  }
  
  public void setUid(int uid) {
    this.uid = uid;
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
  
  public List<viewScratchCards> getProducedCards() throws SQLException, Exception {
    List<viewScratchCards> cardsInfo = new ArrayList<>();
    try {
      Connector();
      PreparedStatement ps = getCn().prepareStatement("select * from scratchcard");
      this.rs = ps.executeQuery();
      while (this.rs.next()) {
        viewScratchCards tbl = new viewScratchCards();
        tbl.setUid(this.rs.getInt("uid"));
        tbl.setPinNo(this.rs.getString("PinNo"));
        tbl.setSerialNo(this.rs.getString("SerialNo"));
        cardsInfo.add(tbl);
      } 
    } catch (Exception ex) {
      Logger.getLogger(viewScratchCards.class.getName()).log(Level.SEVERE, (String)null, ex);
    } finally{
        this.Close();//
                
    }
    return cardsInfo;
  }
}
