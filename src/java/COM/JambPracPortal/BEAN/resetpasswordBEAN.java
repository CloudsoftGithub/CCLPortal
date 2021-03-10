package COM.JambPracPortal.BEAN;

import COM.JambPracPortal.DAO.resetpasswordDAO;
import COM.JambPracPortal.MODEL.resetpassword;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class resetpasswordBEAN {
  private resetpassword resetpswd = new resetpassword();
  
  public resetpassword getResetpswd() {
    return this.resetpswd;
  }
  
  public void setResetpswd(resetpassword resetpswd) {
    this.resetpswd = resetpswd;
  }
  
  public void passwordResetConfirnmationMthd() throws Exception {
    try {
      resetpasswordDAO dao = new resetpasswordDAO();
      dao.PasswordResetDetailsConfirmation(this.resetpswd);
    } catch (Exception e) {
      throw e;
    } 
  }
  
  public void verifyTokenMthd() throws Exception {
    try {
      resetpasswordDAO dao = new resetpasswordDAO();
      dao.verifyToken(this.resetpswd);
    } catch (Exception e) {
      throw e;
    } 
  }
  
  public void resetpasswordMethod() throws Exception {
    try {
      resetpasswordDAO dao = new resetpasswordDAO();
      dao.resetPasswordMthd(this.resetpswd);
    } catch (Exception e) {
      throw e;
    } 
  }
}
