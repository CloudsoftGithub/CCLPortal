package COM.JambPracPortal.BEAN;

import COM.JambPracPortal.DAO.userResponseDAO;
import COM.JambPracPortal.MODEL.userResponse;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class userResponseBEAN {
  private userResponse usrRep = new userResponse();
  
  public userResponse getUsrRep() {
    return this.usrRep;
  }
  
  public void setUsrRep(userResponse usrRep) {
    this.usrRep = usrRep;
  }
  
  public void userResponseMethod() throws Exception {
    try {
      userResponseDAO dao = new userResponseDAO();
      dao.userResponseMethod(this.usrRep);
    } catch (Exception e) {
      throw e;
    } 
  }
}
