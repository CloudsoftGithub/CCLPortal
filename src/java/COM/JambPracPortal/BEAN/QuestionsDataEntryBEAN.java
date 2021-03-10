package COM.JambPracPortal.BEAN;

import COM.JambPracPortal.DAO.QuestionsDataEntryDAO;
import COM.JambPracPortal.MODEL.QuestionsDataEntry;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class QuestionsDataEntryBEAN {
  private QuestionsDataEntry questionsDEntry = new QuestionsDataEntry();
  
  public QuestionsDataEntry getQuestionsDEntry() {
    return this.questionsDEntry;
  }
  
  public void setQuestionsDEntry(QuestionsDataEntry questionsDEntry) {
    this.questionsDEntry = questionsDEntry;
  }
  
  public void questiondataentryMethod() throws Exception {
    try {
      QuestionsDataEntryDAO dao = new QuestionsDataEntryDAO();
      dao.questiondataentryMethod(this.questionsDEntry);
    } catch (Exception e) {
      throw e;
    } 
  }
}
