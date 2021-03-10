package COM.JambPracPortal.BEAN;

import COM.JambPracPortal.DAO.DAO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
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
public class questiondataentrySearchBean extends DAO implements Serializable {

    Connection con;

    PreparedStatement ps;

    public int sid;

    public String subject;

    public int year;

    public int time;

    public String Instructions;

    public String Question;

    public String ChoiceA;

    public String ChoiceB;

    public String ChoiceC;

    public String ChoiceD;

    public String ChoiceE;

    public String QuestionNo;

    public String CorrectAnswer;

    public String myInstructions;

    public String myQuestion;

    public String myChoiceA;

    public String myChoiceB;

    public String myChoiceC;

    public String myChoiceD;

    public String myChoiceE;

    public String myQuestionNo;

    public String myCorrectAnswer;

    public String getMyInstructions() {
        return this.myInstructions;
    }

    public void setMyInstructions(String myInstructions) {
        this.myInstructions = myInstructions;
    }

    public String getMyQuestion() {
        return this.myQuestion;
    }

    public void setMyQuestion(String myQuestion) {
        this.myQuestion = myQuestion;
    }

    public String getMyChoiceA() {
        return this.myChoiceA;
    }

    public void setMyChoiceA(String myChoiceA) {
        this.myChoiceA = myChoiceA;
    }

    public String getMyChoiceB() {
        return this.myChoiceB;
    }

    public void setMyChoiceB(String myChoiceB) {
        this.myChoiceB = myChoiceB;
    }

    public String getMyChoiceC() {
        return this.myChoiceC;
    }

    public void setMyChoiceC(String myChoiceC) {
        this.myChoiceC = myChoiceC;
    }

    public String getMyChoiceD() {
        return this.myChoiceD;
    }

    public void setMyChoiceD(String myChoiceD) {
        this.myChoiceD = myChoiceD;
    }

    public String getMyChoiceE() {
        return this.myChoiceE;
    }

    public void setMyChoiceE(String myChoiceE) {
        this.myChoiceE = myChoiceE;
    }

    public String getMyQuestionNo() {
        return this.myQuestionNo;
    }

    public void setMyQuestionNo(String myQuestionNo) {
        this.myQuestionNo = myQuestionNo;
    }

    public String getMyCorrectAnswer() {
        return this.myCorrectAnswer;
    }

    public void setMyCorrectAnswer(String myCorrectAnswer) {
        this.myCorrectAnswer = myCorrectAnswer;
    }

    public String getCorrectAnswer() {
        return this.CorrectAnswer;
    }

    public void setCorrectAnswer(String CorrectAnswer) {
        this.CorrectAnswer = CorrectAnswer;
    }

    public String getInstructions() {
        return this.Instructions;
    }

    public void setInstructions(String Instructions) {
        this.Instructions = Instructions;
    }

    public String getQuestion() {
        return this.Question;
    }

    public void setQuestion(String Question) {
        this.Question = Question;
    }

    public String getChoiceA() {
        return this.ChoiceA;
    }

    public void setChoiceA(String ChoiceA) {
        this.ChoiceA = ChoiceA;
    }

    public String getChoiceB() {
        return this.ChoiceB;
    }

    public void setChoiceB(String ChoiceB) {
        this.ChoiceB = ChoiceB;
    }

    public String getChoiceC() {
        return this.ChoiceC;
    }

    public void setChoiceC(String ChoiceC) {
        this.ChoiceC = ChoiceC;
    }

    public String getChoiceD() {
        return this.ChoiceD;
    }

    public void setChoiceD(String ChoiceD) {
        this.ChoiceD = ChoiceD;
    }

    public String getChoiceE() {
        return this.ChoiceE;
    }

    public void setChoiceE(String ChoiceE) {
        this.ChoiceE = ChoiceE;
    }

    public String getQuestionNo() {
        return this.QuestionNo;
    }

    public void setQuestionNo(String QuestionNo) {
        this.QuestionNo = QuestionNo;
    }

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

    public void retriveCurrentSubjectFromUI() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        this.subject = (String) ec.getRequestParameterMap().get("questiondataentryForm:Subject");
        System.out.println("Subject Printing Test1: " + subject);
    }

    public void retriveCurrentSIDFromUI() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String mysid = (String) ec.getRequestParameterMap().get("questiondataentryForm:Subjectid");
        this.sid = Integer.parseInt(mysid);
    }

    public void subjectSearchDMethod() throws ClassNotFoundException, SQLException, Exception {
        Class.forName("com.mysql.jdbc.Driver");
        this.Connector();//

        retriveCurrentSubjectFromUI();
        retriveCurrentSIDFromUI();

        try {
            this.ps = this.getCn().prepareStatement("SELECT subject,year,time FROM subjectreg WHERE sid=?");
            this.ps.setInt(1, this.sid);
            ResultSet rss = this.ps.executeQuery();
            System.out.println("Subject Printing Test2: " + subject);

            if (rss.next()) {
                this.subject = rss.getString(1);
                this.year = rss.getInt(2);
                this.time = rss.getInt(3);
                RequestContext.getCurrentInstance().update("questiondataentryForm:QuestionDEntryPanel");
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Subject search error", "the sid entered is invalid.  Pls, check and try again. ");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            //this.Close();
        }

    }//end of the method

    public void subjectIfoDMethod() throws ClassNotFoundException, SQLException, Exception {
        Class.forName("com.mysql.jdbc.Driver");
        this.Connector();//
        try {

            this.ps = this.getCn().prepareStatement("SELECT subject,Instructions,Question, ChoiceA, ChoiceB, ChoiceC, ChoiceD, ChoiceE,CorretAnswer  FROM questiondataentry WHERE sid=? AND QuestionNo=? ");
            this.ps.setInt(1, this.sid);
            this.ps.setString(2, this.QuestionNo);
            ResultSet rss = this.ps.executeQuery();
            if (rss.next()) {
                this.subject = rss.getString(1);
                this.Instructions = rss.getString(2);
                this.Question = rss.getString(3);
                this.ChoiceA = rss.getString(4);
                this.ChoiceB = rss.getString(5);
                this.ChoiceC = rss.getString(6);
                this.ChoiceD = rss.getString(7);
                this.ChoiceE = rss.getString(8);
                this.CorrectAnswer = rss.getString(9);
                RequestContext.getCurrentInstance().update("EditquestiondataentryForm:editQuestionDEntryPanel");
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Subject search error", "the sid entered is invalid.  Pls, check and try again. ");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
            }
        } catch (Exception e) {
            throw e;

        } finally {
            this.Close();
        }

    }//end of the mthd

    public void editsubjectIfoDMethod() throws ClassNotFoundException, SQLException, Exception {
       
        Class.forName("com.mysql.jdbc.Driver");
        this.Connector();//
        
//        /retriveCurrentSubjectFromUI();
       // retriveCurrentSIDFromUI();
          
        try {

            this.ps = this.getCn().prepareStatement("UPDATE questiondataentry SET Instructions=?, Question=?, ChoiceA= ?, ChoiceB=?, ChoiceC=?, ChoiceD=?, ChoiceE=?, CorretAnswer=?  WHERE sid=? AND QuestionNo=? ");
            this.ps.setString(1, this.myInstructions);
            this.ps.setString(2, this.myQuestion);
            this.ps.setString(3, this.myChoiceA);
            this.ps.setString(4, this.myChoiceB);
            this.ps.setString(5, this.myChoiceC);
            this.ps.setString(6, this.myChoiceD);
            this.ps.setString(7, this.myChoiceE);
            this.ps.setString(8, this.myCorrectAnswer);
            this.ps.setInt(9, this.sid);
            this.ps.setString(10, this.QuestionNo);
            this.ps.executeUpdate();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Subject editing Message", "you successfully edited a question's info, thank you!");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Subject editing Error", "pls, read this error meassage :" + e);
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        } finally {
            this.Close();
        }
    }
}//end of the class
