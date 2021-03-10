package COM.JambPracPortal.BEAN;

import COM.JambPracPortal.MODEL.subjectreg;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class viewsubjID extends subjectreg {

    public int sid;

    public String subject;

    public int year;

    public int time;

    Connection con;

    PreparedStatement ps;

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

    public int getSid() {
        return this.sid;
    }

    public void retriveCurrentSubjectFromUI() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        this.subject = (String) ec.getRequestParameterMap().get("subjtRegForm:Subject");
    }

    public void retriveCurrentYearFromUI() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String myyear = (String) ec.getRequestParameterMap().get("subjtRegForm:Year");
        this.year = Integer.parseInt(myyear);
    }

    public void retriveCurrentTimeFromUI() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String mytime = (String) ec.getRequestParameterMap().get("subjtRegForm:Time");
        this.time = Integer.parseInt(mytime);
    }

    public void subjectViewIDMethod() throws ClassNotFoundException, SQLException {
        retriveCurrentYearFromUI();
        retriveCurrentTimeFromUI();
        retriveCurrentSubjectFromUI();
        Class.forName("com.mysql.jdbc.Driver");

 //        con = DriverManager.getConnection("jdbc:mysql://node53961-cclportal2020.w1-us.cloudjiffy.net/jambpracportal", "root", "VYCmxf59153");
 
       con = (com.mysql.jdbc.Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/jambpracportal?user=root&password=ash123");

        this.ps = this.con.prepareStatement("select sid from subjectreg where subject=? AND year=?");
        this.ps.setString(1, this.subject);
        this.ps.setInt(2, this.year);
        ResultSet rss = this.ps.executeQuery();
        if (rss.next()) {
            this.sid = rss.getInt(1);
            RequestContext.getCurrentInstance().update("subjtRegForm:subjpanel");
        }

        this.con.close();//
    }
}
