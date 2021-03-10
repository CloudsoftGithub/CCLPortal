package COM.JambPracPortal.BEAN;

import COM.JambPracPortal.DAO.UserDAO;
import COM.JambPracPortal.MODEL.signup;
import java.io.IOException;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class LoginBean {
  private signup signin = new signup();
  
  public signup getSignin() {
    return this.signin;
  }
  
  public void setSignin(signup signin) {
    this.signin = signin;
  }
  
  public void loginProject() throws SQLException, IOException, Exception {
    try {
      UserDAO dao = new UserDAO();
      dao.loginMthd(this.signin);
    } catch (Exception e) {
      throw e;
    } 
  }
}
