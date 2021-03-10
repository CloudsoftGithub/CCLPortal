package COM.JambPracPortal.BEAN;

import COM.JambPracPortal.DAO.DAO;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

@ManagedBean
@SessionScoped
public class ckeckTest extends DAO {

    private static final long serialVersionUID = 1L;

    private UploadedFile file;

    Connection con;

    PreparedStatement ps;

    public StreamedContent myImage;

    public UploadedFile getFile() {
        return this.file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public StreamedContent getMyImage() {
        return this.myImage;
    }

    public void setMyImage(StreamedContent myImage) {
        this.myImage = myImage;
    }

    public void chekings() throws Exception {
        try {
            this.Connector();//

            System.out.println("im tesing this block of codes");
            System.out.println(this.file.getFileName());
            Class.forName("com.mysql.jdbc.Driver");
            this.ps = this.getCn().prepareStatement("select Instructions from questiondataentry where sid=4 and questionno=1");
            ResultSet rs = this.ps.executeQuery();
            if (rs.next()) {
                InputStream inputstream = rs.getBinaryStream(1);
                this.myImage = (StreamedContent) new DefaultStreamedContent(inputstream, "image/png");
            }
            System.out.println("are u here!");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "photo upload confirmation", " You ccc successfully uploaded your photograph. Pls, fill the form and submit");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        } catch (Exception e) {
            System.out.println("Exception-File Upload." + e.getMessage());
        } finally {
            this.Close();
        }
    }
}
