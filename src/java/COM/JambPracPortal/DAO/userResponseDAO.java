package COM.JambPracPortal.DAO;

import COM.JambPracPortal.MODEL.userResponse;
import java.sql.PreparedStatement;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

public class userResponseDAO extends DAO {
  public void userResponseMethod(userResponse usrResp) throws Exception {
    try {
      String myflag = "answered";
      PreparedStatement st3 = getCn().prepareStatement("INSERT INTO signup values(?,?,?,?,?,?,?)");
      st3.setString(1, usrResp.getUsername());
      st3.setString(2, usrResp.getSubject());
      st3.setInt(3, usrResp.getSid());
      st3.setString(4, usrResp.getQuestionNo());
      st3.setString(5, usrResp.getCorrectAnswer());
      st3.setString(6, usrResp.getUserAnswers());
      st3.setString(7, usrResp.getFlag());
      st3.executeUpdate();
      st3.close();
      FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful Options Selection", "You succcessfully entered your response = ");
      RequestContext.getCurrentInstance().showMessageInDialog(message);
    } catch (Exception e) {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error in data entry pls, try again " + e, ""));
    } finally {
      Close();
    } 
  }
}
