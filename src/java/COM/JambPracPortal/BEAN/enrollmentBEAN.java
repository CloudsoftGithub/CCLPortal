package COM.JambPracPortal.BEAN;

import COM.JambPracPortal.DAO.enrollmentDAO;
import COM.JambPracPortal.MODEL.enrollment;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class enrollmentBEAN {
  private enrollment enroll = new enrollment();
  
  public enrollment getEnroll() {
    return this.enroll;
  }
  
  public void setEnroll(enrollment enroll) {
    this.enroll = enroll;
  }
  
  public void enrollmentMthd() throws Exception {
    try {
      enrollmentDAO dao = new enrollmentDAO();
      dao.enrolmentMthd(this.enroll);
    } catch (Exception e) {
      throw e;
    } 
  }
  
  public void cclNoRetrievalMthd() throws Exception {
    try {
      enrollmentDAO dao = new enrollmentDAO();
      dao.retrievingCClNoMethd(this.enroll);
    } catch (Exception e) {
      throw e;
    } 
  }
}
