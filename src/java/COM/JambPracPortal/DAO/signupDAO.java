package COM.JambPracPortal.DAO;

import COM.JambPracPortal.MODEL.signup;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

public class signupDAO extends DAO {

    public void signupMethod(signup sigp) throws Exception {
        this.Connector();
        PreparedStatement st1 = getCn().prepareStatement("select username from signup where username=?");
        st1.setString(1, sigp.getUsername());
        ResultSet rs = st1.executeQuery();
        PreparedStatement st2 = getCn().prepareStatement("select serialNo,PinNo from signup where serialNo=? AND PinNo=?");
        st2.setString(1, sigp.getSerialNo());
        st2.setString(2, sigp.getPinNo());
        ResultSet rs2 = st2.executeQuery();
        if (rs2.next()) {
            FacesMessage messag = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Signup Error", "invalid scratch card detail, this card has been used");
            RequestContext.getCurrentInstance().showMessageInDialog(messag);
            st1.close();
        } else if (rs.next()) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Signup Error", "This username has been signed up, pls use a different username");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            this.Close();
            st1.close();
        } else {
            String myflag = "used";
            PreparedStatement st3 = getCn().prepareStatement("select serialNo,PinNo from scratchcard where serialNo=? AND PinNo=?");
            st3.setString(1, sigp.getSerialNo());
            st3.setString(2, sigp.getPinNo());
            ResultSet rs3 = st3.executeQuery();
            if (rs3.next()) {
                try {
                    Connector();
                    PreparedStatement st = getCn().prepareStatement("INSERT INTO signup values(null,?,?,?,?,?,?, Date(Now()))");
                    st.setString(1, sigp.getUsername());
                    st.setString(2, sigp.getPassword());
                    st.setString(3, sigp.getEmail());
                    st.setString(4, sigp.getSerialNo());
                    st.setString(5, sigp.getPinNo());
                    st.setString(6, myflag);
                    st.executeUpdate();
                    st.close();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "You succcessfully signup thank you! ", "you will be redirected to combination page"));
                    ExternalContext redcontext = FacesContext.getCurrentInstance().getExternalContext();
                    redcontext.redirect("successfulSignup.xhtml");
                } catch (Exception e) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error in signup pls, try again ", "thank you!" + e));
                } finally {
                    Close();
                }
            } else {
                FacesMessage messag = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Signup Error", "invalid scratch card detail, pls obtain your card from our vendors or  call: +234 9038605224 for help");
                RequestContext.getCurrentInstance().showMessageInDialog(messag);
                st1.close();
                Close();
            }
        }
    }
}
