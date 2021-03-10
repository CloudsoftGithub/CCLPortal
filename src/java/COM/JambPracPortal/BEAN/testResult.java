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
public class testResult extends DAO implements Serializable {

    Connection con;

    PreparedStatement ps;

    Connection con2;

    PreparedStatement ps2;

    public String subject;

    public String subject1;

    public String subject2;

    public String questionNo;

    public String CorrectAnswer;

    public String username;

    public int result = 0;

    public int result2 = 0;

    public int subTotalNo2 = 0;

    public int subTotalNo = 0;

    public String userAnswers;

    public int getResult2() {
        return this.result2;
    }

    public void setResult2(int result2) {
        this.result2 = result2;
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

    public int getResult() {
        return this.result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getSubTotalNo2() {
        return this.subTotalNo2;
    }

    public void setSubTotalNo2(int subTotalNo2) {
        this.subTotalNo2 = subTotalNo2;
    }

    public int getSubTotalNo() {
        return this.subTotalNo;
    }

    public void setSubTotalNo(int subTotalNo) {
        this.subTotalNo = subTotalNo;
    }

    public String getUserAnswers() {
        return this.userAnswers;
    }

    public void setUserAnswers(String userAnswers) {
        this.userAnswers = userAnswers;
    }

    public void retriveCurrentUsernameFromUI() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        this.username = (String) ec.getRequestParameterMap().get("resultForm:myusername");
    }

    public void gettingsubject() throws ClassNotFoundException, SQLException, Exception {
        retriveCurrentUsernameFromUI();
        Class.forName("com.mysql.jdbc.Driver");
        this.Connector();// 

        this.ps = this.getCn().prepareStatement("select Subject1,Subject2 from selectedsubjects where username=?");
        this.ps.setString(1, this.username);
        ResultSet rss = this.ps.executeQuery();
        if (rss.next()) {
            this.subject1 = rss.getString(1);
            this.subject2 = rss.getString(2);
            RequestContext.getCurrentInstance().update("resultForm:BigresultPanel");
            //this.ps.close();
            //this.Close();
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "getting subjects erros", " pls, try again ");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }
    }

    public void myresultForSubj1() throws ClassNotFoundException, SQLException, Exception {
        String correctAnswerFlag = "yes";
        retriveCurrentUsernameFromUI();
        Class.forName("com.mysql.jdbc.Driver");
        this.Connector();// 

        this.ps = this.getCn().prepareStatement("select count(*) from userresponse where flag=? and username=? AND subject=?");
        this.ps.setString(1, correctAnswerFlag);
        this.ps.setString(2, this.username);
        this.ps.setString(3, this.subject1);
        ResultSet rss = this.ps.executeQuery();
        if (rss.next()) {
            result = rss.getInt(1);

            //RequestContext.getCurrentInstance().update("resultForm:BigresultPanel");
            System.out.println("my Result under: " + result);
            //this.ps.close();
            // this.Close();
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error user response", " pls, try again ");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }
    }

    public void myresultForSubj2() throws ClassNotFoundException, SQLException, Exception {
        String correctAnswerFlag = "yes";
        retriveCurrentUsernameFromUI();
        Class.forName("com.mysql.jdbc.Driver");
        this.Connector();

        this.ps = this.getCn().prepareStatement("select count(*) from userresponse where flag=? and username=? AND subject=?");
        this.ps.setString(1, correctAnswerFlag);
        this.ps.setString(2, this.username);
        this.ps.setString(3, this.subject2);
        ResultSet rss = this.ps.executeQuery();
        if (rss.next()) {
            this.result2 = rss.getInt(1);
            System.out.println("my Result2 under: " + result2);

            // RequestContext.getCurrentInstance().update("resultForm:BigresultPanel");
            //this.ps.close();
            // this.Close();
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error user response", " pls, try again ");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }
    }

    public void totalmarkssSubj1() throws ClassNotFoundException, SQLException, Exception {
        String correctAnswerFlag = "yes";
        retriveCurrentUsernameFromUI();

        Class.forName("com.mysql.jdbc.Driver");
        this.Connector();//

        this.ps = this.getCn().prepareStatement("select count(*) from questiondataentry where subject=?");
        this.ps.setString(1, this.subject1);
        ResultSet rss = this.ps.executeQuery();
        if (rss.next()) {
            this.subTotalNo = rss.getInt(1);
            RequestContext.getCurrentInstance().update("resultForm:BigresultPanel");
            //this.ps.close();
            //this.Close();
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error user response", " pls, try again ");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }
    }

    public void totalmarkssSubj2() throws ClassNotFoundException, SQLException, Exception {
        String correctAnswerFlag = "yes";
        retriveCurrentUsernameFromUI();
        Class.forName("com.mysql.jdbc.Driver");
        this.Connector();//

        this.ps = this.getCn().prepareStatement("select count(*) from questiondataentry where subject=?");
        this.ps.setString(1, this.subject2);
        ResultSet rss = this.ps.executeQuery();
        if (rss.next()) {
            this.subTotalNo2 = rss.getInt(1);
            RequestContext.getCurrentInstance().update("resultForm:BigresultPanel");
            this.ps.close();
            this.Close();
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error user response", " pls, try again ");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }
    }

    public void deleteResultAfterViewing() throws ClassNotFoundException, SQLException, Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.Connector();//

            this.ps = this.getCn().prepareStatement("DELETE FROM userresponse where username=?");
            this.ps.setString(1, this.username);
            this.ps.executeUpdate();
            this.ps.close();
            RequestContext.getCurrentInstance().update("TestDashBoardForm:TestDashBoardFormPanel");
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error user response", " pls, try again " + e);
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        } finally {
            this.Close();
        }
    }

    public void clear() {
        this.setResult(0);
        this.setResult2(0);
        this.setSubTotalNo(0);
        this.setSubTotalNo2(0);
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
    public void viewResult() throws ClassNotFoundException, SQLException, Exception {
        clear();
        gettingsubject();
        totalmarkssSubj1();
        totalmarkssSubj2();
        myresultForSubj1();
        myresultForSubj2();
    }
}//end of the class
