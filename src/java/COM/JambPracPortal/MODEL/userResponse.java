package COM.JambPracPortal.MODEL;

public class userResponse {
  private String subject;
  
  private int sid;
  
  private String questionNo;
  
  private String username;
  
  private String CorrectAnswer;
  
  private String userAnswers;
  
  private String Flag;
  
  public String getSubject() {
    return this.subject;
  }
  
  public void setSubject(String subject) {
    this.subject = subject;
  }
  
  public int getSid() {
    return this.sid;
  }
  
  public void setSid(int sid) {
    this.sid = sid;
  }
  
  public String getQuestionNo() {
    return this.questionNo;
  }
  
  public void setQuestionNo(String questionNo) {
    this.questionNo = questionNo;
  }
  
  public String getUsername() {
    return this.username;
  }
  
  public void setUsername(String username) {
    this.username = username;
  }
  
  public String getCorrectAnswer() {
    return this.CorrectAnswer;
  }
  
  public void setCorrectAnswer(String CorrectAnswer) {
    this.CorrectAnswer = CorrectAnswer;
  }
  
  public String getUserAnswers() {
    return this.userAnswers;
  }
  
  public void setUserAnswers(String userAnswers) {
    this.userAnswers = userAnswers;
  }
  
  public String getFlag() {
    return this.Flag;
  }
  
  public void setFlag(String Flag) {
    this.Flag = Flag;
  }
}
