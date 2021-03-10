package COM.JambPracPortal.BEAN;

import COM.JambPracPortal.DAO.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;

@ManagedBean
@SessionScoped
public class AssignmentBEAN extends DAO {

    int counter = 0;

    public int sid;

    public String username;

    public String programm;

    public String doc;

    private String batch;

    public String title;

    public String description;

    private UploadedFile AssignmentFile;

    Statement stmt = null;

    PreparedStatement ps;

    ResultSet rs;

    Connection con;

    public int getSid() {
        return this.sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProgramm() {
        return this.programm;
    }

    public void setProgramm(String programm) {
        this.programm = programm;
    }

    public String getDoc() {
        return this.doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getBatch() {
        return this.batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UploadedFile getAssignmentFile() {
        return this.AssignmentFile;
    }

    public void setAssignmentFile(UploadedFile AssignmentFile) {
        this.AssignmentFile = AssignmentFile;
    }

    public void sendAssignment() throws SQLException, Exception {
         Class.forName("com.mysql.jdbc.Driver");
       
         this.Connector();//
 
        PreparedStatement st1 = this.getCn().prepareStatement("select title from Assignment where program=? AND doc=? AND batch=? AND title=?");
        st1.setString(1, this.programm);
        st1.setString(2, this.doc);
        st1.setString(3, this.batch);
        st1.setString(4, this.title);
        ResultSet rs1 = st1.executeQuery();
        if (rs1.next()) {
            FacesMessage messag = new FacesMessage(FacesMessage.SEVERITY_WARN, "Assigment Posting Error", "This 'Assignment TITLE' has been used. Please, select a different title.");
            RequestContext.getCurrentInstance().showMessageInDialog(messag);
            RequestContext.getCurrentInstance().update("uploadAssignmentForm:uploadAssignmentPanel");

        } else {
            try {
  
                UploadedFile uploadAssignment = getAssignmentFile();
                if (!uploadAssignment.getFileName().equalsIgnoreCase("")) {
                    PreparedStatement st = this.getCn().prepareStatement("INSERT INTO assignment values(null,?,?,?,?,?, Date(Now()),?)");
                    st.setString(1, this.programm);
                    st.setString(2, this.doc);
                    st.setString(3, this.batch);
                    st.setString(4, this.title);
                    st.setString(5, this.description);
                    st.setBinaryStream(6, uploadAssignment.getInputstream());
                    st.executeUpdate();
                    st.close();

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, uploadAssignment
                            .getFileName() + " successfully sent, " + "with title: " + this.title, "Thank you."));
                    clear();
                    this.counter++;
                    if (this.counter > 0) {
                        clear();
                        uploadAssignment.getInputstream().close();
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error in file upload. Please, supply a PDF file for the Assignment. ", "  thank you!"));
                }
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error in Sending Assignment. pls, try again ", "  thank you!" + e));
            } finally {

                this.Close();
            }
        }
    }

    public void clear() {
        this.programm = "";
        this.doc = "";
        this.batch = "";
        this.title = "";
        this.description = "";
        System.out.println("Printing the streamed file: " + this.AssignmentFile);
    }

   /*
    
     public void viewAssignment() throws SQLException, Exception {

        try {

            this.Connector();//

            PreparedStatement st1 = this.getCn().prepareStatement(" SELECT description from Assignment where program=? AND doc=? AND batch=? order by Date ");
            st1.setString(1, this.programm);
            st1.setString(2, this.doc);
            st1.setString(3, this.batch);
            ResultSet rs1 = st1.executeQuery();

            if (rs1.next()) {
                RequestContext.getCurrentInstance().update("viewAssginmentForm:viewAssignmentPanel");

            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Assignment View Error", "Invalid input.  Pls, check and try again. ");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
            }

        } catch (Exception e) {

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Assignment View Error", "Invalid input.  Pls, check and try again. " + e);
            RequestContext.getCurrentInstance().showMessageInDialog(message);

        } finally {
            this.Close();
        }

    }
    
    */
}
