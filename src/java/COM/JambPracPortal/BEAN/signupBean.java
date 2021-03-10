package COM.JambPracPortal.BEAN;

import COM.JambPracPortal.DAO.signupDAO;
import COM.JambPracPortal.MODEL.signup;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class signupBean {
  private signup signp = new signup();
  
  public signup getSignp() {
    return this.signp;
  }
  
  public void setSignp(signup signp) {
    this.signp = signp;
  }
  
  public void signupMethod() throws Exception {
    try {
      signupDAO dao = new signupDAO();
      dao.signupMethod(this.signp);
    } catch (Exception e) {
      throw e;
    } 
  }
}
