package COM.JambPracPortal.DAO;

import COM.JambPracPortal.MODEL.subjectreg;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class subjectregDAO extends DAO {
  private int sid = 0;
  
  int counter;
  
  public int getSid() {
    return this.sid;
  }
  
  public void subjectregMethod(subjectreg subjreg) throws Exception {
    int myYear = subjreg.getYear();
    int myTime = subjreg.getTime();
    if (myYear < 1 || myTime < 1) {
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Subject Registration Error", "Pls, either Year or time cannot be zero (0)");
      RequestContext.getCurrentInstance().showMessageInDialog(message);
      this.Close();
    } 
    this.Connector();
    PreparedStatement st1 = getCn().prepareStatement("select subject from subjectreg where subject=? AND year=?");
    st1.setString(1, subjreg.getSubject());
    st1.setInt(2, subjreg.getYear());
    ResultSet rs = st1.executeQuery();
    if (rs.next()) {
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Subject Registration Error", "This subject has been registered for the same year, pls check your operations");
      RequestContext.getCurrentInstance().showMessageInDialog(message);
       st1.close();
    } else {
      try {
        this.Connector();
        PreparedStatement st = getCn().prepareStatement("INSERT INTO subjectreg values(null,?,?,?)");
        st.setString(1, subjreg.getSubject());
        st.setInt(2, subjreg.getYear());
        st.setInt(3, subjreg.getTime());
        st.executeUpdate();
        st.close();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful Subject registration", "You succcessfully registered (" + subjreg.getSubject() + ", " + subjreg.getYear() + "). Pls, make data entry to complete the regsitration. ");
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        RequestContext.getCurrentInstance().update("subjtRegForm:subjpanel");
      } catch (Exception e) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error in Subject registration pls, try again ", "thank you!" + e));
      } finally {
        this.Close();
      } 
    } 
  }
}
