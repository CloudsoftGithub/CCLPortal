package COM.JambPracPortal.DAO;

import COM.JambPracPortal.MODEL.QuestionsDataEntry;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class QuestionsDataEntryDAO extends DAO {
  public int sid;
  
  public String subject;
  
  public int year;
  
  public int time;
  
  public int getSid() {
    return this.sid;
  }
  
  public void setSid(int sid) {
    this.sid = sid;
  }
  
  public String getSubject() {
    return this.subject;
  }
  
  public void setSubject(String subject) {
    this.subject = subject;
  }
  
  public int getYear() {
    return this.year;
  }
  
  public void setYear(int year) {
    this.year = year;
  }
  
  public int getTime() {
    return this.time;
  }
  
  public void setTime(int time) {
    this.time = time;
  }
  
  public void retriveCurrentSidFromUI() {
    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    String mytime = (String)ec.getRequestParameterMap().get("questiondataentryForm:Subjectid");
    this.sid = Integer.parseInt(mytime);
  }
  
  public void retriveCurrentSubjectFromUI() {
    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    this.subject = (String)ec.getRequestParameterMap().get("questiondataentryForm:Subject");
  }
  
  public void retriveCurrentYearFromUI() {
    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    String mytime = (String)ec.getRequestParameterMap().get("questiondataentryForm:Year");
    this.year = Integer.parseInt(mytime);
  }
  
  public void retriveCurrentTimeFromUI() {
    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    String mytime = (String)ec.getRequestParameterMap().get("questiondataentryForm:Time");
    this.time = Integer.parseInt(mytime);
  }
  
  public void questiondataentryMethod(QuestionsDataEntry qestionDentry) throws Exception {
    if (qestionDentry.getQuestionNo().equalsIgnoreCase("")) {
      FacesMessage messag = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data Entry Error", "sorry, questionNo is blank!");
      RequestContext.getCurrentInstance().showMessageInDialog(messag);
    } 
    if (qestionDentry.getQuestion().equalsIgnoreCase("")) {
      FacesMessage messag = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data Entry Error", "sorry, question is blank!");
      RequestContext.getCurrentInstance().showMessageInDialog(messag);
    } 
    retriveCurrentSidFromUI();
    Connector();
    PreparedStatement st1 = getCn().prepareStatement("select sid from questiondataentry where sid=? AND QuestionNo=?");
    st1.setInt(1, this.sid);
    st1.setString(2, qestionDentry.getQuestionNo());
    ResultSet rs = st1.executeQuery();
    if (rs.next()) {
      FacesMessage messag = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Question Data Entry Error", "sorry, the subject with sid = " + this.sid + " and questionNo = " + qestionDentry.getQuestionNo() + "  has already been entered!, pls enter the next questionNo");
      RequestContext.getCurrentInstance().showMessageInDialog(messag);
      Close();
      st1.close();
    } else {
      PreparedStatement st2 = getCn().prepareStatement("select sid from subjectreg where sid=?");
      st2.setInt(1, this.sid);
      ResultSet rs2 = st2.executeQuery();
      if (!rs2.next()) {
        FacesMessage messag = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Question Data Entry Error", "sorry, No subject with sid = " + this.sid + " is been registered!, pls crosscheck and try again");
        RequestContext.getCurrentInstance().showMessageInDialog(messag);
        Close();
        st2.close();
      } else {
        retriveCurrentSubjectFromUI();
        try {
          Connector();
          PreparedStatement st = getCn().prepareStatement("INSERT INTO questiondataentry values(?,?,?,?,?,?,?,?,?,?,?,?)");
          st.setInt(1, this.sid);
          st.setString(2, this.subject);
          st.setString(3, qestionDentry.getQuestionNo());
          st.setString(4, qestionDentry.getInstructions());
          st.setString(5, qestionDentry.getQuestion());
          st.setString(6, qestionDentry.getChoiceA());
          st.setString(7, qestionDentry.getChoiceB());
          st.setString(8, qestionDentry.getChoiceC());
          st.setString(9, qestionDentry.getChoiceD());
          st.setString(10, qestionDentry.getChoiceE());
          st.setString(11, qestionDentry.getChoiceF());
          st.setString(12, qestionDentry.getCorrectAnswer());
          st.executeUpdate();
          st.close();
          FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful Question Data Entry", "You succcessfully entered question = " + qestionDentry.getQuestionNo() + ", for  subject with sid = " + this.sid);
          RequestContext.getCurrentInstance().showMessageInDialog(message);
        } catch (Exception e) {
          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error in data entry pls, try again " + e, ""));
        } finally {
          Close();
        } 
      } 
    } 
  }
}
