package COM.JambPracPortal.BEAN;

import COM.JambPracPortal.DAO.DAO;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class QuestionsDataEntryImageModeBEAN extends DAO {
  public int sid;
  
  public String subject;
  
  public int year;
  
  public int time;
  
  public String questionNo;
  
  public String CorrectAnswer;
  
  InputStream inputstreamInstruction;
  
  byte[] imagedataInstructions;
  
  InputStream inputstreamQuestion;
  
  byte[] imagedataQuestion;
  
  InputStream inputstreamChoiceA;
  
  byte[] imagedataChoiceA;
  
  InputStream inputstreamChoiceB;
  
  byte[] imagedataChoiceB;
  
  InputStream inputstreamChoiceC;
  
  byte[] imagedataIChoiceC;
  
  InputStream inputstreamChoiceD;
  
  byte[] imagedataChoiceD;
  
  InputStream inputstreamChoiceE;
  
  byte[] imagedataChoiceE;
  
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
  
  public String getQuestionNo() {
    return this.questionNo;
  }
  
  public void setQuestionNo(String questionNo) {
    this.questionNo = questionNo;
  }
  
  public String getCorrectAnswer() {
    return this.CorrectAnswer;
  }
  
  public void setCorrectAnswer(String CorrectAnswer) {
    this.CorrectAnswer = CorrectAnswer;
  }
  
  public void retriveCurrentSIDFromUI() {
    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    String mysid = (String)ec.getRequestParameterMap().get("questiondataentryForm:Subjectid");
    this.sid = Integer.parseInt(mysid);
  }
  
  public void retriveCurrentQuestionNoFromUI() {
    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    this.questionNo = (String)ec.getRequestParameterMap().get("questiondataentryForm:questionNo");
  }
  
  public void retriveCurrentCorrectAnswerFromUI() {
    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    this.CorrectAnswer = (String)ec.getRequestParameterMap().get("questiondataentryForm:correctAnswer");
  }
  
  public void retriveCurrentSubjectFromUI() {
    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    this.subject = (String)ec.getRequestParameterMap().get("questiondataentryForm:Subject");
  }
  
  public void submitquestiondataentry() throws IOException, ClassNotFoundException, SQLException, Exception {
    int counter = 0;
    retriveCurrentQuestionNoFromUI();
    retriveCurrentSIDFromUI();
    Connector();
    PreparedStatement sts = getCn().prepareStatement("select Question from questiondataentry where sid= ? AND QuestionNo=?");
    sts.setInt(1, this.sid);
    sts.setString(2, this.questionNo);
    ResultSet rs = sts.executeQuery();
    if (rs.next()) {
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Form Submission Error", "this Question with sid =  " + this.sid + " and QuestionNo = " + this.questionNo + " has been registered!");
      RequestContext.getCurrentInstance().showMessageInDialog(message);
    } else {
      retriveCurrentQuestionNoFromUI();
      retriveCurrentSIDFromUI();
      Connector();
      PreparedStatement ps = getCn().prepareStatement("select Instructions from upload_instructions where sid=? AND QuestionNo=?");
      ps.setInt(1, this.sid);
      ps.setString(2, this.questionNo);
      ResultSet rst = ps.executeQuery();
      if (rst.next()) {
        this.inputstreamInstruction = rst.getBinaryStream(1);
        this.imagedataInstructions = rst.getBytes("Instructions");
      } 
      PreparedStatement pss = getCn().prepareStatement("select Subject from subjectreg where sid=?");
      pss.setInt(1, this.sid);
      ResultSet rsts = pss.executeQuery();
      if (rsts.next())
        this.subject = rsts.getString(1); 
      PreparedStatement ps2 = getCn().prepareStatement("select Question from upload_question where sid=? AND QuestionNo=?");
      ps2.setInt(1, this.sid);
      ps2.setString(2, this.questionNo);
      ResultSet rst2 = ps2.executeQuery();
      if (rst2.next()) {
        this.inputstreamQuestion = rst2.getBinaryStream(1);
        this.imagedataQuestion = rst2.getBytes("Question");
      } 
      PreparedStatement ps3 = getCn().prepareStatement("select ChoiceA from upload_choicea where sid=? AND QuestionNo=?");
      ps3.setInt(1, this.sid);
      ps3.setString(2, this.questionNo);
      ResultSet rst3 = ps3.executeQuery();
      if (rst3.next()) {
        this.inputstreamChoiceA = rst3.getBinaryStream(1);
        this.imagedataChoiceA = rst3.getBytes("ChoiceA");
      } 
      PreparedStatement ps4 = getCn().prepareStatement("select ChoiceB from upload_choiceb where sid=? AND QuestionNo=?");
      ps4.setInt(1, this.sid);
      ps4.setString(2, this.questionNo);
      ResultSet rst4 = ps4.executeQuery();
      if (rst4.next()) {
        this.inputstreamChoiceB = rst4.getBinaryStream(1);
        this.imagedataChoiceB = rst4.getBytes("ChoiceB");
      } 
      PreparedStatement ps5 = getCn().prepareStatement("select ChoiceC from upload_choicec where sid=? AND QuestionNo=?");
      ps5.setInt(1, this.sid);
      ps5.setString(2, this.questionNo);
      ResultSet rst5 = ps5.executeQuery();
      if (rst5.next()) {
        this.inputstreamChoiceC = rst5.getBinaryStream(1);
        this.imagedataIChoiceC = rst5.getBytes("ChoiceC");
      } 
      PreparedStatement ps6 = getCn().prepareStatement("select ChoiceD from upload_choiced where sid=? AND QuestionNo=?");
      ps6.setInt(1, this.sid);
      ps6.setString(2, this.questionNo);
      ResultSet rst6 = ps6.executeQuery();
      if (rst6.next()) {
        this.inputstreamChoiceD = rst6.getBinaryStream(1);
        this.imagedataChoiceD = rst6.getBytes("ChoiceD");
      } 
      PreparedStatement ps7 = getCn().prepareStatement("select ChoiceE from upload_choicee where sid=? AND QuestionNo=?");
      ps7.setInt(1, this.sid);
      ps7.setString(2, this.questionNo);
      ResultSet rst7 = ps7.executeQuery();
      if (rst7.next()) {
        this.inputstreamChoiceE = rst7.getBinaryStream(1);
        this.imagedataChoiceE = rst7.getBytes("ChoiceE");
      } 
      try {
        String ChoiceE = "null";
        retriveCurrentQuestionNoFromUI();
        retriveCurrentSIDFromUI();
        Connector();
        Connector();
        PreparedStatement st = getCn().prepareStatement("INSERT INTO questiondataentry values(?,?,?,?,?,?,?,?,?,?,?,?)");
        st.setInt(1, this.sid);
        st.setString(2, this.subject);
        st.setString(3, this.questionNo);
        st.setBytes(4, this.imagedataInstructions);
        st.setBytes(5, this.imagedataQuestion);
        st.setBytes(6, this.imagedataChoiceA);
        st.setBytes(7, this.imagedataChoiceB);
        st.setBytes(8, this.imagedataIChoiceC);
        st.setBytes(9, this.imagedataChoiceD);
        st.setBytes(10, this.imagedataChoiceE);
        st.setString(11, ChoiceE);
        st.setString(12, this.CorrectAnswer);
        st.executeUpdate();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Form Submission Confirmation", "You successfully submitted your form for sid = " + this.sid + " and QuestionNo = " + this.questionNo + " ,  thank you! ");
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        try {
          String InsertQueryString = "DELETE FROM upload_instructions where sid=? AND QuestionNo=?";
          PreparedStatement pstatement = getCn().prepareStatement(InsertQueryString);
          pstatement.setInt(1, this.sid);
          pstatement.setString(2, this.questionNo);
          pstatement.executeUpdate();
          String InsertQueryString2 = "DELETE FROM upload_question where sid=? AND QuestionNo=?";
          PreparedStatement pstatement2 = getCn().prepareStatement(InsertQueryString2);
          pstatement2.setInt(1, this.sid);
          pstatement2.setString(2, this.questionNo);
          pstatement2.executeUpdate();
          String InsertQueryString3 = "DELETE FROM upload_choicea where sid=? AND QuestionNo=?";
          PreparedStatement pstatement3 = getCn().prepareStatement(InsertQueryString3);
          pstatement3.setInt(1, this.sid);
          pstatement3.setString(2, this.questionNo);
          pstatement3.executeUpdate();
          String InsertQueryString4 = "DELETE FROM upload_choiceb where sid=? AND QuestionNo=?";
          PreparedStatement pstatement4 = getCn().prepareStatement(InsertQueryString4);
          pstatement4.setInt(1, this.sid);
          pstatement4.setString(2, this.questionNo);
          pstatement4.executeUpdate();
          String InsertQueryString5 = "DELETE FROM upload_choicec where sid=? AND QuestionNo=?";
          PreparedStatement pstatement5 = getCn().prepareStatement(InsertQueryString5);
          pstatement5.setInt(1, this.sid);
          pstatement5.setString(2, this.questionNo);
          pstatement5.executeUpdate();
          String InsertQueryString6 = "DELETE FROM upload_choiced where sid=? AND QuestionNo=?";
          PreparedStatement pstatement6 = getCn().prepareStatement(InsertQueryString6);
          pstatement6.setInt(1, this.sid);
          pstatement6.setString(2, this.questionNo);
          pstatement6.executeUpdate();
          String InsertQueryString7 = "DELETE FROM upload_choicee where sid=? AND QuestionNo=?";
          PreparedStatement pstatement7 = getCn().prepareStatement(InsertQueryString7);
          pstatement7.setInt(1, this.sid);
          pstatement7.setString(2, this.questionNo);
          pstatement7.executeUpdate();
        } finally {
          Close();
        } 
      } catch (Exception e) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error in Question Data Entry! please try again " + e, ""));
      } finally {
        Close();
      } 
    } 
  }
}
