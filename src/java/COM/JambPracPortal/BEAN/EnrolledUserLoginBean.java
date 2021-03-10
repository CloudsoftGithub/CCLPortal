package COM.JambPracPortal.BEAN;

import COM.JambPracPortal.DAO.EnrolledUserLoginDAO;
import COM.JambPracPortal.MODEL.login;
import java.io.IOException;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped 
public class EnrolledUserLoginBean{
  private login certapp = new login();
  
  public login getCertapp() {
    return this.certapp;
  }
  
  public void setCertapp(login certapp) {
    this.certapp = certapp;
  }
  
  private login login = new login();
  
  public login getLogin() {
    return this.login;
  }
  
  public void setLogin(login login1) {
    this.login = login1;
  }
  
  public void loginMethd() throws SQLException, IOException, Exception {
    try {
      EnrolledUserLoginDAO dao = new EnrolledUserLoginDAO();
      dao.loginMethod(this.login);
    } catch (Exception e) {
      throw e;
    } 
  }
  
  public void clearVariblesMethod() {
    try {
      EnrolledUserLoginDAO dao = new EnrolledUserLoginDAO();
      dao.clearVariables(this.login);
    } catch (Exception e) {
      throw e;
    } 
  }
}
