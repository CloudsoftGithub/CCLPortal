package COM.JambPracPortal.BEAN;

import COM.JambPracPortal.DAO.DAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class QuestionsDataEntryTableBEAN extends DAO {

    private int sid;

    private String subject;

    private int year;

    private int time;

    private int mysid;

    private String questionNo;

    private String instructions;

    private String question;

    private String choiceA;

    private String choiceB;

    private String choiceC;

    private String choiceD;

    private String choiceE;

    private String choiceF;

    private String correctAnswer;

    public int getMysid() {
        return this.mysid;
    }

    public void setMysid(int mysid) {
        this.mysid = mysid;
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

    public String getChoiceE() {
        return this.choiceE;
    }

    public void setChoiceE(String choiceE) {
        this.choiceE = choiceE;
    }

    public String getChoiceD() {
        return this.choiceD;
    }

    public void setChoiceD(String choiceD) {
        this.choiceD = choiceD;
    }

    public String getChoiceF() {
        return this.choiceF;
    }

    public void setChoiceF(String choiceF) {
        this.choiceF = choiceF;
    }

    public String getCorrectAnswer() {
        return this.correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    Statement stmt = null;

    PreparedStatement ps;

    ResultSet rs;

    public List<QuestionsDataEntryTableBEAN> getquestiondataentryInfo() throws SQLException {
        List<QuestionsDataEntryTableBEAN> questiondataentryInfo = new ArrayList<>();
        try {
            this.Connector();
            PreparedStatement ps = getCn().prepareStatement("select * from questiondataentry");

            while (this.rs.next()) {
                QuestionsDataEntryTableBEAN tbl = new QuestionsDataEntryTableBEAN();
                tbl.setSid(this.rs.getInt("sid"));
                tbl.setQuestionNo(this.rs.getString("questionNo"));
                tbl.setInstructions(this.rs.getString("instructions"));
                tbl.setQuestion(this.rs.getString("question"));
                tbl.setChoiceA(this.rs.getString("choiceA"));
                tbl.setChoiceB(this.rs.getString("choiceB"));
                tbl.setChoiceC(this.rs.getString("choiceC"));
                tbl.setChoiceD(this.rs.getString("choiceD"));
                tbl.setChoiceE(this.rs.getString("choiceE"));
                tbl.setChoiceF(this.rs.getString("choiceF"));
                tbl.setCorrectAnswer(this.rs.getString("corretAnswer"));
                questiondataentryInfo.add(tbl);
            }
        } catch (Exception ex) {
            Logger.getLogger(QuestionsDataEntryTableBEAN.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        clear();
        return questiondataentryInfo;
    }

    public void clear() {
        this.instructions = null;
        this.choiceA = null;
        this.choiceB = null;
        this.choiceC = null;
        this.choiceD = null;
        this.choiceE = null;
        this.choiceF = null;
        this.correctAnswer = null;
    }
}
