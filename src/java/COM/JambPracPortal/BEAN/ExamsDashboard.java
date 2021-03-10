package COM.JambPracPortal.BEAN;

import COM.JambPracPortal.DAO.DAO;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;
import org.primefaces.model.StreamedContent;

@ManagedBean
@ViewScoped
public class ExamsDashboard extends DAO implements Serializable {

    PreparedStatement ps;

    PreparedStatement ps2;

    private String instructions;

    public int sid;

    public String subject;

    public String subject1;

    public String subject2;

    public int year;

    public int time;

    public String questionNo;

    public String CorrectAnswer;

    public String username;

    private String question;

    private String choiceA;

    private String choiceB;

    private String choiceC;

    private String choiceD;

    private String choiceE;

    private String choiceF;

    public String userAnswers;

    public int subjTotalNoOfQuest = 0;

    private int activeIndex;

    private boolean disable;

    public String selectectRadioValue;

    public StreamedContent myInstructions;

    public String getSelectectRadioValue() {
        return this.selectectRadioValue;
    }

    public StreamedContent getMyInstructions() {
        return this.myInstructions;
    }

    public void setMyInstructions(StreamedContent myInstructions) {
        this.myInstructions = myInstructions;
    }

    public boolean isDisable() {
        return this.disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public int getActiveIndex() {
        return this.activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }

    public int getSubjTotalNoOfQuest() {
        return this.subjTotalNoOfQuest;
    }

    public void setSubjTotalNoOfQuest(int subjTotalNoOfQuest) {
        this.subjTotalNoOfQuest = subjTotalNoOfQuest;
    }

    public String getUserAnswers() {
        return this.userAnswers;
    }

    public void setUserAnswers(String userAnswers) {
        this.userAnswers = userAnswers;
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

    public String getInstructions() {
        return this.instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject1() {
        return this.subject1;
    }

    public void setSubject1(String subject1) {
        this.subject1 = subject1;
    }

    public String getSubject2() {
        return this.subject2;
    }

    public void setSubject2(String subject2) {
        this.subject2 = subject2;
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

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getSid() {
        return this.sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
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
        this.subject = (String) ec.getRequestParameterMap().get("TestDashBoardForm:Subject");
    }

    public void retriveCurrentUsernameFromUI() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        this.username = (String) ec.getRequestParameterMap().get("TestDashBoardForm:myuser");
    }

    public void retriveUserAnswersFromUI() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        this.userAnswers = (String) ec.getRequestParameterMap().get("TestDashBoardForm:userAnswers");
    }

    public void subjectpullingDMethod() throws ClassNotFoundException, SQLException, Exception {
        retriveCurrentUsernameFromUI();
        Class.forName("com.mysql.jdbc.Driver");

        this.Connector();//

        this.ps = this.getCn().prepareStatement("SELECT subject1,subject2  FROM selectedsubjects  WHERE Username=?");
        this.ps.setString(1, this.username);
        ResultSet rss = this.ps.executeQuery();

        if (rss.next()) {
            this.subject1 = rss.getString(1);
            this.subject2 = rss.getString(2);

            RequestContext.getCurrentInstance().update("TestDashBoardForm:TestDashBoardFormPanel");
        } else {

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Subject retrieval Error", "the subject entered is invalid.  Pls, check and try again. ");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }
        this.subject = this.subject1;
    }

    public void timeYearpullingDMethod() throws ClassNotFoundException, SQLException, Exception {
        this.Connector();//

        this.ps = this.getCn().prepareStatement("SELECT time,year  FROM subjectreg  WHERE Subject=?");
        this.ps.setString(1, this.subject);
        ResultSet rss = this.ps.executeQuery();
        if (rss.next()) {
            this.time = rss.getInt(1);
            this.year = rss.getInt(2);
            RequestContext.getCurrentInstance().update("TestDashBoardForm:TestDashBoardFormPanel");
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Subject retrieval Error", "the subject entered is invalid.  Pls, check and try again. ");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }
    }

    public void sidPullingMethod() throws ClassNotFoundException, SQLException, Exception {
        this.Connector();
        try {

            this.ps = this.getCn().prepareStatement("SELECT sid FROM questiondataentry WHERE Subject=?");
            this.ps.setString(1, this.subject);
            ResultSet rss = this.ps.executeQuery();
            if (rss.next()) {
                this.sid = rss.getInt(1);
                RequestContext.getCurrentInstance().update("TestDashBoardForm:TestDashBoardFormPanel");
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "sid retrieval Error", "the sid entered is invalid.  Pls, sign in again");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            this.ps.close();
            this.Close();
        }

    }//end of the method

    int subTotalQuesNofromDB = 0;

    public void PullingTotalNoOfQuestionforSubjMethod() throws ClassNotFoundException, SQLException, Exception {

        try {
            Class.forName("com.mysql.jdbc.Driver");

            this.Connector();

            this.ps = this.getCn().prepareStatement("select count(*) from questiondataentry where Subject=?");
            this.ps.setString(1, this.subject);
            ResultSet rss = this.ps.executeQuery();
            if (rss.next()) {
                this.subTotalQuesNofromDB = rss.getInt(1);
            }
            this.ps.close();
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error QuestTotal", " pls, try again " + e);
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        } finally {
            this.Close();//
        }
    }

    public void questionPullingMethod() throws ClassNotFoundException, SQLException, Exception {
        this.Connector();//

        this.ps = this.getCn().prepareStatement("SELECT sid FROM questiondataentry WHERE Subject=?");
        this.ps.setString(1, this.subject);
        ResultSet rss2 = this.ps.executeQuery();
        if (rss2.next()) {
            this.sid = rss2.getInt(1);
            RequestContext.getCurrentInstance().update("TestDashBoardForm:TestDashBoardFormPanel");
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "sid retrieval Error", "the sid entered is invalid.  Pls, sign in again");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }

        try {
            this.ps = this.getCn().prepareStatement("SELECT count(*)QuestionNo FROM questiondataentry  WHERE subject=?");
            this.ps.setString(1, this.subject);
            ResultSet rs = this.ps.executeQuery();
            if (rs.next()) {
                this.subjTotalNoOfQuest = rs.getInt(1);
            }
        } catch (Exception e) {
            throw e;
        }

        try {
            retriveUserAnswersFromUI();
            this.ps = this.getCn().prepareStatement("SELECT Instructions,questionNo,Question,ChoiceA,ChoiceB,ChoiceC,ChoiceD,ChoiceE,CorretAnswer FROM questiondataentry  WHERE subject=? AND questionNo=0");
            this.ps.setString(1, this.subject);
            ResultSet rss = this.ps.executeQuery();
            if (rss.next()) {
                this.instructions = rss.getString(1);
                this.questionNo = rss.getString(2);
                this.question = rss.getString(3);
                this.choiceA = "(A)  " + rss.getString(4);
                this.choiceB = "(B)  " + rss.getString(5);
                this.choiceC = "(C)  " + rss.getString(6);
                this.choiceD = "(D)  " + rss.getString(7);
                this.choiceE = "(E)  " + rss.getString(8);
                this.CorrectAnswer = rss.getString(9);
                RequestContext.getCurrentInstance().update("TestDashBoardForm:TestDashBoardFormPanel");
            }
        } catch (Exception e) {
            throw e;
        } finally {

            this.Close();//
            this.ps.close();
        }

    }//end of the mthd

    int qustNoCounter = 0;

    public void nextButtonMethod() throws ClassNotFoundException, SQLException, Exception {
        this.Connector();//

        retriveUserAnswersFromUI();
        try {
            this.qustNoCounter++;
            this.ps = this.getCn().prepareStatement("SELECT Instructions,questionNo,Question,ChoiceA,ChoiceB,ChoiceC,ChoiceD,ChoiceE,CorretAnswer FROM questiondataentry  WHERE subject=? AND questionNo=?");
            this.ps.setString(1, this.subject);
            this.ps.setInt(2, this.qustNoCounter);
            ResultSet rss = this.ps.executeQuery();
            if (rss.next()) {
                this.instructions = rss.getString(1);
                this.questionNo = rss.getString(2);
                this.question = rss.getString(3);
                this.choiceA = "(A)  " + rss.getString(4);
                this.choiceB = "(B)  " + rss.getString(5);
                this.choiceC = "(C)  " + rss.getString(6);
                this.choiceD = "(D)  " + rss.getString(7);
                this.choiceE = "(E)  " + rss.getString(8);
                this.CorrectAnswer = rss.getString(9);
            } else if (!rss.next()) {
                if (this.subject.equalsIgnoreCase(this.subject1)) {
                    this.subject = this.subject2;
                    this.qustNoCounter = 1;
                    this.activeIndex = 1;
                } else {
                    this.subject = this.subject1;
                    this.qustNoCounter = 1;
                    this.activeIndex = 0;
                }
                this.ps = this.getCn().prepareStatement("SELECT Instructions,questionNo,Question,ChoiceA,ChoiceB,ChoiceC,ChoiceD,ChoiceE,CorretAnswer FROM questiondataentry  WHERE subject=? AND questionNo=?");
                this.ps.setString(1, this.subject);
                this.ps.setInt(2, this.qustNoCounter);
                ResultSet rs = this.ps.executeQuery();
                if (rs.next()) {
                    this.instructions = rs.getString(1);
                    this.questionNo = rs.getString(2);
                    this.question = rs.getString(3);
                    this.choiceA = "(A)  " + rs.getString(4);
                    this.choiceB = "(B)  " + rs.getString(5);
                    this.choiceC = "(C)  " + rs.getString(6);
                    this.choiceD = "(D)  " + rs.getString(7);
                    this.choiceE = "(E)  " + rs.getString(8);
                    this.CorrectAnswer = rs.getString(9);
                }
            }
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "instructions&Questions retrieval Error", "Instructions & Questions could not be retrieved.  Pls, check and try again. wwww ");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }
        try {
            this.ps = this.getCn().prepareStatement("SELECT count(*)QuestionNo FROM questiondataentry  WHERE subject=?");
            this.ps.setString(1, this.subject);
            ResultSet rs = this.ps.executeQuery();
            if (rs.next()) {
                this.subjTotalNoOfQuest = rs.getInt(1);
            }
        } catch (Exception exception) {

        } finally {
            this.ps.close();
            this.Close();
        }
    }

    public void savingQuestionMethod() throws SQLException, Exception {
        retriveUserAnswersFromUI();
        String flag = null;
        System.out.println(" Working?!!!!!!!!!!!!!!!!!!!! ");

        Class.forName("com.mysql.jdbc.Driver");
         this.Connector();
        
        RequestContext.getCurrentInstance().update("TestDashBoardForm:TestDashBoardFormPanel");

        try {
            ps2 = this.getCn().prepareStatement("INSERT INTO userresponse values(?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE UserAnswer=VALUES(UserAnswer),flag=VALUES(flag)");
            ps2.setString(1, username);
            ps2.setString(2, subject);
            ps2.setInt(3, sid);
            ps2.setString(4, questionNo);
            ps2.setString(5, CorrectAnswer);
            ps2.setString(6, userAnswers);
            ps2.setString(7, flag);

            this.ps2.executeUpdate();
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error user response", " pls, try again. " + e);
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }

        try {

            ps = this.getCn().prepareStatement("update userresponse SET flag=( CASE WHEN (CorrectAnswer=UserAnswer) THEN 'yes' ELSE 'no' END)");
            this.ps.executeUpdate();
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error response update", " pls, try again " + e);
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        } finally {
            this.ps.close();
            this.Close();
        }
    }//end of the method

    public void previousButtonMethod() throws ClassNotFoundException, SQLException, Exception {
        retriveUserAnswersFromUI();
        this.Connector();

        try {
            this.qustNoCounter--;
            this.ps = this.getCn().prepareStatement("SELECT Instructions,questionNo,Question,ChoiceA,ChoiceB,ChoiceC,ChoiceD,ChoiceE,CorretAnswer FROM questiondataentry  WHERE sid=? AND questionNo=?");
            this.ps.setInt(1, this.sid);
            this.ps.setInt(2, this.qustNoCounter);
            ResultSet rss = this.ps.executeQuery();
            if (rss.next()) {
                this.instructions = rss.getString(1);
                this.questionNo = rss.getString(2);
                this.question = rss.getString(3);
                this.choiceA = "(A)  " + rss.getString(4);
                this.choiceB = "(B)  " + rss.getString(5);
                this.choiceC = "(C)  " + rss.getString(6);
                this.choiceD = "(D)  " + rss.getString(7);
                this.choiceE = "(E)  " + rss.getString(8);
                this.CorrectAnswer = rss.getString(9);
                RequestContext.getCurrentInstance().update("TestDashBoardForm:TestDashBoardFormPanel");
            } else {
                this.subject = this.subject2;
            }
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "instructions retrieval Error", "Instructions could not be retrieved.  Pls, check and try again. ");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }
        try {
            this.ps = this.getCn().prepareStatement("SELECT count(*)QuestionNo FROM questiondataentry  WHERE subject=?");
            this.ps.setString(1, this.subject);
            ResultSet rs = this.ps.executeQuery();
            if (rs.next()) {
                this.subjTotalNoOfQuest = rs.getInt(1);
            }
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Previous Button Error", "Previous button NOT active.  Pls, check and try again. " + e.getMessage());
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        } finally {
            this.Close();
            this.ps.close();
        }
    }//end of themethod

    public void submitandViewResult() throws SQLException, Exception {
        int subTotalNo = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            this.Connector();

            this.ps = this.getCn().prepareStatement("select count(*) from questiondataentry where subject=?");
            this.ps.setString(1, this.subject1);
            ResultSet rss = this.ps.executeQuery();
            if (rss.next()) {
                subTotalNo = rss.getInt(1);
            }
            //this.ps.close();
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error QuestTotal", " pls, try again " + e);
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        } finally {
        }
        int subTotalNo2 = 0;

        try {
            Class.forName("com.mysql.jdbc.Driver");

            this.ps = this.getCn().prepareStatement("select count(*) from questiondataentry where subject=?");
            this.ps.setString(1, this.subject2);
            ResultSet rss = this.ps.executeQuery();
            if (rss.next()) {
                subTotalNo2 = rss.getInt(1);
                System.err.println("subTotalNo2:" + subTotalNo2);
            }
            //this.ps.close();
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error QuestTotal", " pls, try again " + e);
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        } finally {

        }

        String correctAnswerFlag = "yes";
        int result = 0;
        int result2 = 0;
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            this.Connector();//
            
            this.ps = this.getCn().prepareStatement("select count(*) from userresponse where flag=? and username=? and subject=?");
            this.ps.setString(1, correctAnswerFlag);
            this.ps.setString(2, this.username);
            this.ps.setString(3, this.subject1);
            ResultSet rss = this.ps.executeQuery();
            if (rss.next()) {
                result = rss.getInt(1);
            }
            // this.ps.close();
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error user response", " pls, try again " + e);
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        } finally {
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.Connector();

            this.ps = this.getCn().prepareStatement("select count(*) from userresponse where flag=? and username=? and subject=?");
            this.ps.setString(1, correctAnswerFlag);
            this.ps.setString(2, this.username);
            this.ps.setString(3, this.subject2);
            ResultSet rss = this.ps.executeQuery();
            if (rss.next()) {
                result2 = rss.getInt(1);
            }

            ExternalContext redcontext = FacesContext.getCurrentInstance().getExternalContext();
            redcontext.redirect("testResult.xhtml");
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error user response", " pls, try again " + e);
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        } finally {
            this.ps.close();
            this.Close();
        }
    }//end of the method

    public void retrievingandDisplayingAlreadySelectedAnswersButtons() throws Exception {
        this.Connector();//

        try {
            this.ps = this.getCn().prepareStatement("select UserAnswer FROM userresponse WHERE username=? AND subject=? AND questionno=?");
            this.ps.setString(1, this.username);
            this.ps.setString(2, this.subject);
            this.ps.setString(3, this.questionNo);
            ResultSet rss = this.ps.executeQuery();
            if (rss.next()) {
                String myUseranswer = rss.getString(1);
                if (myUseranswer.equalsIgnoreCase("A")) {
                    HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                    this.selectectRadioValue = request.getParameter("A");
                } else if (!myUseranswer.equalsIgnoreCase("B")) {
                    if (!myUseranswer.equalsIgnoreCase("C")) {
                        if (!myUseranswer.equalsIgnoreCase("D")) {
                            if (myUseranswer.equalsIgnoreCase("E"));
                        }
                    }
                }
                RequestContext.getCurrentInstance().update("TestDashBoardForm:TestDashBoardFormPanel");
            }
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "RetrievingAndDisplayingAlreadySelectedAns", "The Retrieval Failed.  Pls, check and try again. " + e);
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        } finally {
            this.ps.close();
            this.Close();
        }
    }//end of the method

    public void deselectRadioButtons() {
    }

    public void goToTab(int index) {
        this.activeIndex = index;
    }

    public void onTimeout() throws IOException, SQLException, Exception {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Time out", "time off! click on Ok to Submit");
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        submitandViewResult();
        ExternalContext redcontext = FacesContext.getCurrentInstance().getExternalContext();
        redcontext.redirect("testResult.xhtml");
    }

    /*
     public void Close() throws Exception {
        try {
            if (!this.con.isClosed()) {
                this.con.close();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.con.close();
        }
    }

    
    
     */
}//end of the class
