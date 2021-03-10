package COM.JambPracPortal.DAO;

import COM.JambPracPortal.MODEL.submitAssignment;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

public class submit_assignDAO extends DAO implements Serializable{

    private String username;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
 

    public void submitAssignmentMthd(submitAssignment subtAssgmt) throws Exception {
       
      
        Connector();
        PreparedStatement st1 = getCn().prepareStatement("select title from submit_assignment where program=? AND doc=? AND batch=? AND title=? AND username=? ");
        st1.setString(1, subtAssgmt.getProgramm());
        st1.setString(2, subtAssgmt.getDoc());
        st1.setString(3, subtAssgmt.getBatch());
        st1.setString(4, subtAssgmt.getTitle());
        st1.setString(5, subtAssgmt.getUsername());
        PreparedStatement st2 = getCn().prepareStatement("select title from assignment where program=? AND doc=? AND batch=? AND title=?");
        st2.setString(1, subtAssgmt.getProgramm());
        st2.setString(2, subtAssgmt.getDoc());
        st2.setString(3, subtAssgmt.getBatch());
        st2.setString(4, subtAssgmt.getTitle());
        ResultSet rs2 = st2.executeQuery();
        ResultSet rs1 = st1.executeQuery();
        if (rs1.next()) {
            FacesMessage messag = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Assigment Submission Error", "This Assignment TITLED: " + subtAssgmt.getTitle() + " has been submitted. Please, select a different title.");
            RequestContext.getCurrentInstance().showMessageInDialog(messag);
            Close();
        } else if (!rs2.next()) {
            FacesMessage messag = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Assigment Submission Error", subtAssgmt.getTitle() + " has NOT BEEN  GIVEN to the selected program. Pls, select correct program, title and doc.");
            RequestContext.getCurrentInstance().showMessageInDialog(messag);
            Close();
            subtAssgmt.setTitle("");
        } else {
            try {
                Connector();
                if (!subtAssgmt.getAssignmentFile().getFileName().equalsIgnoreCase("")) {
                    PreparedStatement st = getCn().prepareStatement("INSERT INTO submit_assignment values(null,?,?,?,?,?, Date(Now()),?)");
                    st.setString(1, subtAssgmt.getUsername());
                    st.setString(2, subtAssgmt.getProgramm());
                    st.setString(3, subtAssgmt.getDoc());
                    st.setString(4, subtAssgmt.getBatch());
                    st.setString(5, subtAssgmt.getTitle());
                    st.setBinaryStream(6, subtAssgmt.getAssignmentFile().getInputstream());
                    st.executeUpdate();
                    st.close();
                    Close();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, " You successfully submitted an assignemnt file:  "
                            + subtAssgmt.getAssignmentFile().getFileName() + " with title: " + subtAssgmt.getTitle(), ". Thank you."));
                    subtAssgmt.setProgramm("");
                    subtAssgmt.setDoc("");
                    subtAssgmt.setBatch("");
                    subtAssgmt.setTitle("");
                    subtAssgmt.setAssignmentFile(null);
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error in file upload.Pls, supply a file for the submitting the assignment ", "  thank you!"));
                }
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error in Submititing Assignment. pls, try again ", "  thank you!" + e));
            }
        }
    }
}
