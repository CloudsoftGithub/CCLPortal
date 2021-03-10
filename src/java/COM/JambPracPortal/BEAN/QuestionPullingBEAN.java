package COM.JambPracPortal.BEAN;

import COM.JambPracPortal.DAO.DAO;
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
public class QuestionPullingBEAN extends DAO{

    Connection con;

    PreparedStatement ps;

    public int sid;

    public String subject;

    public int year;

    public int time;

    private String questionNo;

    private String instructions;

    private String question;

    private String choiceA;

    private String choiceB;

    private String choiceC;

    private String choiceD;

    private String choiceE;

    private String choiceF;

    public String getQuestionNo() {
        return this.questionNo;
    }

    public void setQuestionNo(String questionNo) {
        this.questionNo = questionNo;
    }

    public String getInstructions() {
        return this.instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getChoiceA() {
        return this.choiceA;
    }

    public void setChoiceA(String choiceA) {
        this.choiceA = choiceA;
    }

    public String getChoiceB() {
        return this.choiceB;
    }

    public void setChoiceB(String choiceB) {
        this.choiceB = choiceB;
    }

    public String getChoiceC() {
        return this.choiceC;
    }

    public void setChoiceC(String choiceC) {
        this.choiceC = choiceC;
    }

    public String getChoiceD() {
        return this.choiceD;
    }

    public void setChoiceD(String choiceD) {
        this.choiceD = choiceD;
    }

    public String getChoiceE() {
        return this.choiceE;
    }

    public void setChoiceE(String choiceE) {
        this.choiceE = choiceE;
    }

    public String getChoiceF() {
        return this.choiceF;
    }

    public void setChoiceF(String choiceF) {
        this.choiceF = choiceF;
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
    }

    public void retriveCurrentSIDFromUI() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String mysid = (String) ec.getRequestParameterMap().get("questiondataentryForm:Subjectid");
        this.sid = Integer.parseInt(mysid);
    }

    public void subjectSearchDMethod() throws ClassNotFoundException, SQLException, Exception {
        this.Connector();//
        
        Class.forName("com.mysql.jdbc.Driver");
 
        this.ps = this.getCn().prepareStatement("SELECT subject,year,time FROM subjectreg WHERE sid=?");
        this.ps.setInt(1, this.sid);
        ResultSet rss = this.ps.executeQuery();
        if (rss.next()) {
            this.subject = rss.getString(1);
            this.year = rss.getInt(2);
            this.time = rss.getInt(3);
            RequestContext.getCurrentInstance().update("questiondataentryForm:QuestionDEntryPanel");
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Subject search error", "the sid entered is invalid.  Pls, check and try again. ");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }
        this.Close();
    }
}
