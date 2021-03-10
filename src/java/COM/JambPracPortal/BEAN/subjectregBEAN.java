package COM.JambPracPortal.BEAN;

import COM.JambPracPortal.DAO.subjectregDAO;
import COM.JambPracPortal.MODEL.subjectreg;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class subjectregBEAN {
  private int sid;
  
  int counter = 0;
  
  public int getSid() {
    return this.sid;
  }
  
  private subjectreg subjreg = new subjectreg();
  
  public subjectreg getSubjreg() {
    return this.subjreg;
  }
  
  public void setSubjreg(subjectreg subjreg) {
    this.subjreg = subjreg;
  }
  
  public void subjectregMethod() throws Exception {
    try {
      subjectregDAO dao = new subjectregDAO();
      dao.subjectregMethod(this.subjreg);
    } catch (Exception e) {
      throw e;
    } 
  }
}
