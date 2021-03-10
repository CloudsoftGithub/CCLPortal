package COM.JambPracPortal.BEAN;

import COM.JambPracPortal.DAO.submit_assignDAO;
import COM.JambPracPortal.MODEL.submitAssignment;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class submitAssignmentBEAN {
  private submitAssignment submtAsgnment = new submitAssignment();
  
  public submitAssignment getSubmtAsgnment() {
    return this.submtAsgnment;
  }
  
  public void setSubmtAsgnment(submitAssignment submtAsgnment) {
    this.submtAsgnment = submtAsgnment;
  }
  
  public void submitAssgnmtMethod() throws Exception {
    try {
      submit_assignDAO dao = new submit_assignDAO();
      dao.submitAssignmentMthd(this.submtAsgnment);
    } catch (Exception e) {
      throw e;
    } 
  }
}
