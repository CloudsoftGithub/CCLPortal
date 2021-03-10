package COM.JambPracPortal.BEAN;

import COM.JambPracPortal.DAO.DAO;
import java.io.IOException;
import java.io.InputStream;
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
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

@ManagedBean
@SessionScoped
public class scannedDocUpload extends DAO {

    private static final long serialVersionUID = 1L;

    private UploadedFile file;

    private UploadedFile questionFile;

    private UploadedFile choiceAFile;

    private UploadedFile choiceBFile;

    private UploadedFile choiceCFile;

    private UploadedFile choiceDFile;

    private UploadedFile choiceEFile;

    private static int file_Size = 15360;

    public int sid;

    public String questionNo;

    Connection con;

    PreparedStatement ps;

    public StreamedContent myInstructions;

    public StreamedContent myQuestion;

    public StreamedContent myChoiceA;

    public StreamedContent myChoiceB;

    public StreamedContent myChoiceC;

    public StreamedContent myChoiceD;

    public StreamedContent myChoiceE;

    public int getSid() {
        return this.sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getQuestionNo() {
        return this.questionNo;
    }

    public void setQuestionNo(String questionNo) {
        this.questionNo = questionNo;
    }

    public StreamedContent getMyInstructions() {
        return this.myInstructions;
    }

    public void setMyInstructions(StreamedContent myInstructions) {
        this.myInstructions = myInstructions;
    }

    public StreamedContent getMyQuestion() {
        return this.myQuestion;
    }

    public void setMyQuestion(StreamedContent myQuestion) {
        this.myQuestion = myQuestion;
    }

    public StreamedContent getMyChoiceA() {
        return this.myChoiceA;
    }

    public void setMyChoiceA(StreamedContent myChoiceA) {
        this.myChoiceA = myChoiceA;
    }

    public UploadedFile getFile() {
        return this.file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getQuestionFile() {
        return this.questionFile;
    }

    public void setQuestionFile(UploadedFile questionFile) {
        this.questionFile = questionFile;
    }

    public UploadedFile getChoiceAFile() {
        return this.choiceAFile;
    }

    public void setChoiceAFile(UploadedFile choiceAFile) {
        this.choiceAFile = choiceAFile;
    }

    public UploadedFile getChoiceBFile() {
        return this.choiceBFile;
    }

    public void setChoiceBFile(UploadedFile choiceBFile) {
        this.choiceBFile = choiceBFile;
    }

    public StreamedContent getMyChoiceB() {
        return this.myChoiceB;
    }

    public void setMyChoiceB(StreamedContent myChoiceB) {
        this.myChoiceB = myChoiceB;
    }

    public UploadedFile getChoiceCFile() {
        return this.choiceCFile;
    }

    public void setChoiceCFile(UploadedFile choiceCFile) {
        this.choiceCFile = choiceCFile;
    }

    public StreamedContent getMyChoiceC() {
        return this.myChoiceC;
    }

    public void setMyChoiceC(StreamedContent myChoiceC) {
        this.myChoiceC = myChoiceC;
    }

    public UploadedFile getChoiceDFile() {
        return this.choiceDFile;
    }

    public void setChoiceDFile(UploadedFile choiceDFile) {
        this.choiceDFile = choiceDFile;
    }

    public UploadedFile getChoiceEFile() {
        return this.choiceEFile;
    }

    public void setChoiceEFile(UploadedFile choiceEFile) {
        this.choiceEFile = choiceEFile;
    }

    public StreamedContent getMyChoiceD() {
        return this.myChoiceD;
    }

    public void setMyChoiceD(StreamedContent myChoiceD) {
        this.myChoiceD = myChoiceD;
    }

    public StreamedContent getMyChoiceE() {
        return this.myChoiceE;
    }

    public void setMyChoiceE(StreamedContent myChoiceE) {
        this.myChoiceE = myChoiceE;
    }

    public void retriveCurrentSIDFromUI() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String mysid = (String) ec.getRequestParameterMap().get("questiondataentryForm:Subjectid");
        this.sid = Integer.parseInt(mysid);
    }

    public void retriveCurrentQuestionNoFromUI() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        this.questionNo = (String) ec.getRequestParameterMap().get("questiondataentryForm:questionNo");
    }

    public void uploadInstructionsScnDoc() throws IOException, ClassNotFoundException, SQLException, Exception {
        this.Connector();//
        
        retriveCurrentSIDFromUI();
        retriveCurrentQuestionNoFromUI();
        Class.forName("com.mysql.jdbc.Driver");
        this.ps = this.getCn().prepareStatement("select Instructions from upload_instructions where sid =? AND QuestionNo=?");
        this.ps.setInt(1, this.sid);
        this.ps.setString(2, this.questionNo);
        ResultSet rss = this.ps.executeQuery();
        if (rss.next()) {
            InputStream inputstream2 = rss.getBinaryStream(1);
            this.myInstructions = (StreamedContent) new DefaultStreamedContent(inputstream2, "image/png");
            FacesMessage messag = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Instruction upload confirmation", " Instructions for: SID = " + this.sid + "  and QuestionNo = " + this.questionNo + "  already exist.");
            RequestContext.getCurrentInstance().showMessageInDialog(messag);
        } else if (this.file.getSize() != 0L && this.file.getSize() <= file_Size) {
            try {
                retriveCurrentSIDFromUI();
                retriveCurrentQuestionNoFromUI();
                InputStream fin2 = this.file.getInputstream();
                Class.forName("com.mysql.jdbc.Driver");
                this.ps = this.getCn().prepareStatement("INSERT INTO upload_instructions values(?,?,?)");
                this.ps.setInt(1, this.sid);
                this.ps.setString(2, this.questionNo);
                this.ps.setBinaryStream(3, fin2, this.file.getSize());
                this.ps.executeUpdate();
                this.ps.close();
                 if (this.file.getSize() != 0L && this.file.getSize() <= file_Size) {
                    try {
                        retriveCurrentSIDFromUI();
                        retriveCurrentQuestionNoFromUI();
                        System.out.println(this.questionFile.getFileName());
                        Class.forName("com.mysql.jdbc.Driver");
                        this.ps = this.getCn().prepareStatement("select Instructions  from upload_instructions  where sid =? AND QuestionNo =?");
                        this.ps.setInt(1, this.sid);
                        this.ps.setString(2, this.questionNo);
                        ResultSet rs = this.ps.executeQuery();
                        if (rs.next()) {
                            InputStream inputstream = rs.getBinaryStream(1);
                            this.myInstructions = (StreamedContent) new DefaultStreamedContent(inputstream, "image/png");
                        }
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Instruction upload confirmation", " You successfully uploaded Instructions for: SID= " + this.sid + "  Question No = " + this.questionNo);
                        RequestContext.getCurrentInstance().showMessageInDialog(message);
                    } catch (Exception e) {
                        System.out.println("Exception-File Upload." + e.getMessage());
                        System.out.println("Inserting image NOTE successfully done!");
                    } finally {
                        this.ps.close();
                        this.Close();
                    }
                }
            } catch (Exception e) {
                FacesMessage msg = new FacesMessage("Your scanned doc is NOT uploaded successfully", " pls, fill the form correctly and submit " + e);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_WARN, "ivalid photo selected! ", "  The maximum file size allowed is: 15kb "));
        }
    }

    public void uploadQuestion() throws IOException, ClassNotFoundException, SQLException, Exception {
        
        this.Connector();//
        
        retriveCurrentSIDFromUI();
        retriveCurrentQuestionNoFromUI();
        Class.forName("com.mysql.jdbc.Driver");
        this.ps = this.getCn().prepareStatement("select Question from upload_question where sid =? AND QuestionNo=?");
        this.ps.setInt(1, this.sid);
        this.ps.setString(2, this.questionNo);
        ResultSet rss = this.ps.executeQuery();
        if (rss.next()) {
            InputStream inputstream2 = rss.getBinaryStream(1);
            this.myQuestion = (StreamedContent) new DefaultStreamedContent(inputstream2, "image/png");
            FacesMessage messag = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Question upload confirmation", " Question for: SID = " + this.sid + "  and QuestionNo = " + this.questionNo + "  already exist.");
            RequestContext.getCurrentInstance().showMessageInDialog(messag);
        } else if (this.questionFile.getSize() != 0L && this.questionFile.getSize() <= file_Size) {
            try {
                retriveCurrentSIDFromUI();
                retriveCurrentQuestionNoFromUI();
                InputStream fin2 = this.questionFile.getInputstream();
                Class.forName("com.mysql.jdbc.Driver");
                this.ps = this.getCn().prepareStatement("INSERT INTO upload_question values(?,?,?)");
                this.ps.setInt(1, this.sid);
                this.ps.setString(2, this.questionNo);
                this.ps.setBinaryStream(3, fin2, this.questionFile.getSize());
                this.ps.executeUpdate();
                this.ps.close();
                 if (this.questionFile.getSize() != 0L && this.questionFile.getSize() <= file_Size) {
                    try {
                        retriveCurrentSIDFromUI();
                        retriveCurrentQuestionNoFromUI();
                        System.out.println(this.questionFile.getFileName());
                        Class.forName("com.mysql.jdbc.Driver");
                        this.ps = this.getCn().prepareStatement("select Question from upload_question where sid =? AND QuestionNo =?");
                        this.ps.setInt(1, this.sid);
                        this.ps.setString(2, this.questionNo);
                        ResultSet rs = this.ps.executeQuery();
                        if (rs.next()) {
                            InputStream inputstream = rs.getBinaryStream(1);
                            this.myQuestion = (StreamedContent) new DefaultStreamedContent(inputstream, "image/png");
                        }
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Question upload confirmation", " You successfully uploaded Question for: SID= " + this.sid + "  Question No = " + this.questionNo);
                        RequestContext.getCurrentInstance().showMessageInDialog(message);
                    } catch (Exception e) {
                        System.out.println("Exception-File Upload." + e.getMessage());
                        System.out.println("Inserting image NOTE successfully done!");
                    } finally {
                        this.ps.close();
                        this.Close();
                    }
                }
            } catch (Exception e) {
                FacesMessage msg = new FacesMessage("Your scanned doc is NOT uploaded successfully", " pls, fill the form correctly and submit " + e);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_WARN, "ivalid photo selected! ", "  The maximum file size allowed is: 15kb "));
        }
    }

    public void uploadChoiceA() throws IOException, ClassNotFoundException, SQLException, Exception {
        
        this.Connector();//
        
        retriveCurrentSIDFromUI();
        retriveCurrentQuestionNoFromUI();
        Class.forName("com.mysql.jdbc.Driver");
        this.ps = this.getCn().prepareStatement("select ChoiceA from upload_choicea where sid =? AND QuestionNo=?");
        this.ps.setInt(1, this.sid);
        this.ps.setString(2, this.questionNo);
        ResultSet rss = this.ps.executeQuery();
        if (rss.next()) {
            InputStream inputstream2 = rss.getBinaryStream(1);
            this.myChoiceA = (StreamedContent) new DefaultStreamedContent(inputstream2, "image/png");
            FacesMessage messag = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ChoiceA upload confirmation", " ChoiceA for: SID = " + this.sid + "  and QuestionNo = " + this.questionNo + "  already exist.");
            RequestContext.getCurrentInstance().showMessageInDialog(messag);
        } else if (this.choiceAFile.getSize() != 0L && this.choiceAFile.getSize() <= file_Size) {
            try {
                retriveCurrentSIDFromUI();
                retriveCurrentQuestionNoFromUI();
                InputStream fin2 = this.choiceAFile.getInputstream();
                Class.forName("com.mysql.jdbc.Driver");
                this.ps = this.getCn().prepareStatement("INSERT INTO upload_choicea values(?,?,?)");
                this.ps.setInt(1, this.sid);
                this.ps.setString(2, this.questionNo);
                this.ps.setBinaryStream(3, fin2, this.choiceAFile.getSize());
                this.ps.executeUpdate();
                this.ps.close();
                 if (this.choiceAFile.getSize() != 0L && this.choiceAFile.getSize() <= file_Size) {
                    try {
                        retriveCurrentSIDFromUI();
                        retriveCurrentQuestionNoFromUI();
                        System.out.println(this.choiceAFile.getFileName());
                        Class.forName("com.mysql.jdbc.Driver");
                        this.ps = this.getCn().prepareStatement("select ChoiceA from upload_choicea where sid =? AND QuestionNo =?");
                        this.ps.setInt(1, this.sid);
                        this.ps.setString(2, this.questionNo);
                        ResultSet rs = this.ps.executeQuery();
                        if (rs.next()) {
                            InputStream inputstream = rs.getBinaryStream(1);
                            this.myChoiceA = (StreamedContent) new DefaultStreamedContent(inputstream, "image/png");
                        }
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "ChoiceA upload confirmation", " You successfully uploaded ChoiceA for: SID= " + this.sid + "  Question No = " + this.questionNo);
                        RequestContext.getCurrentInstance().showMessageInDialog(message);
                    } catch (Exception e) {
                        System.out.println("Exception-File Upload." + e.getMessage());
                        System.out.println("Inserting image NOTE successfully done!");
                    } finally {
                        this.ps.close();
                        this.Close();
                    }
                }
            } catch (Exception e) {
                FacesMessage msg = new FacesMessage("ChoiceA doc is NOT uploaded successfully", " pls, fill the form correctly and submit " + e);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_WARN, "ivalid photo selected! ", "  The maximum file size allowed is: 15kb "));
        }
    }

    public void uploadChoiceB() throws IOException, ClassNotFoundException, SQLException, Exception {
        
        this.Connector();//
        
        retriveCurrentSIDFromUI();
        retriveCurrentQuestionNoFromUI();
        Class.forName("com.mysql.jdbc.Driver");
        this.ps = this.getCn().prepareStatement("select ChoiceB from upload_choiceb where sid =? AND QuestionNo=?");
        this.ps.setInt(1, this.sid);
        this.ps.setString(2, this.questionNo);
        ResultSet rss = this.ps.executeQuery();
        if (rss.next()) {
            InputStream inputstream2 = rss.getBinaryStream(1);
            this.myChoiceB = (StreamedContent) new DefaultStreamedContent(inputstream2, "image/png");
            FacesMessage messag = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ChoiceB upload confirmation", " ChoiceB for: SID = " + this.sid + "  and QuestionNo = " + this.questionNo + "  already exist.");
            RequestContext.getCurrentInstance().showMessageInDialog(messag);
        } else if (this.choiceBFile.getSize() != 0L && this.choiceBFile.getSize() <= file_Size) {
            try {
                retriveCurrentSIDFromUI();
                retriveCurrentQuestionNoFromUI();
                InputStream fin2 = this.choiceBFile.getInputstream();
                Class.forName("com.mysql.jdbc.Driver");
                this.ps = this.getCn().prepareStatement("INSERT INTO upload_choiceb values(?,?,?)");
                this.ps.setInt(1, this.sid);
                this.ps.setString(2, this.questionNo);
                this.ps.setBinaryStream(3, fin2, this.choiceBFile.getSize());
                this.ps.executeUpdate();
                this.ps.close();
                 if (this.choiceBFile.getSize() != 0L && this.choiceBFile.getSize() <= file_Size) {
                    try {
                        retriveCurrentSIDFromUI();
                        retriveCurrentQuestionNoFromUI();
                        System.out.println(this.choiceBFile.getFileName());
                        Class.forName("com.mysql.jdbc.Driver");
                        this.ps = this.con.prepareStatement("select ChoiceB from upload_choiceb where sid =? AND QuestionNo =?");
                        this.ps.setInt(1, this.sid);
                        this.ps.setString(2, this.questionNo);
                        ResultSet rs = this.ps.executeQuery();
                        if (rs.next()) {
                            InputStream inputstream = rs.getBinaryStream(1);
                            this.myChoiceB = (StreamedContent) new DefaultStreamedContent(inputstream, "image/png");
                        }
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "ChoiceB upload confirmation", " You successfully uploaded ChoiceB for: SID= " + this.sid + "  Question No = " + this.questionNo);
                        RequestContext.getCurrentInstance().showMessageInDialog(message);
                    } catch (Exception e) {
                        System.out.println("Exception-File Upload." + e.getMessage());
                        System.out.println("Inserting image NOTE successfully done!");
                    } finally {
                        this.ps.close();
                        this.Close();
                    }
                }
            } catch (Exception e) {
                FacesMessage msg = new FacesMessage("ChoiceB doc is NOT uploaded successfully", " pls, fill the form correctly and submit " + e);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_WARN, "ivalid photo selected! ", "  The maximum file size allowed is: 15kb "));
        }
    }

    public void uploadChoiceC() throws IOException, ClassNotFoundException, SQLException, Exception {
        
        this.Connector();//
        
        retriveCurrentSIDFromUI();
        retriveCurrentQuestionNoFromUI();
        Class.forName("com.mysql.jdbc.Driver");
        this.ps = this.getCn().prepareStatement("select ChoiceC from upload_choicec where sid =? AND QuestionNo=?");
        this.ps.setInt(1, this.sid);
        this.ps.setString(2, this.questionNo);
        ResultSet rss = this.ps.executeQuery();
        if (rss.next()) {
            InputStream inputstream2 = rss.getBinaryStream(1);
            this.myChoiceC = (StreamedContent) new DefaultStreamedContent(inputstream2, "image/png");
            FacesMessage messag = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ChoiceC upload confirmation", " ChoiceC for: SID = " + this.sid + "  and QuestionNo = " + this.questionNo + "  already exist.");
            RequestContext.getCurrentInstance().showMessageInDialog(messag);
        } else if (this.choiceCFile.getSize() != 0L && this.choiceCFile.getSize() <= file_Size) {
            try {
                retriveCurrentSIDFromUI();
                retriveCurrentQuestionNoFromUI();
                InputStream fin2 = this.choiceCFile.getInputstream();
                Class.forName("com.mysql.jdbc.Driver");
                this.ps = this.getCn().prepareStatement("INSERT INTO upload_choicec values(?,?,?)");
                this.ps.setInt(1, this.sid);
                this.ps.setString(2, this.questionNo);
                this.ps.setBinaryStream(3, fin2, this.choiceCFile.getSize());
                this.ps.executeUpdate();
                this.ps.close();
               
                if (this.choiceCFile.getSize() != 0L && this.choiceCFile.getSize() <= file_Size) {
                    try {
                        retriveCurrentSIDFromUI();
                        retriveCurrentQuestionNoFromUI();
                        System.out.println(this.choiceCFile.getFileName());
                        Class.forName("com.mysql.jdbc.Driver");
                        this.ps = this.getCn().prepareStatement("select ChoiceC from upload_choicec where sid =? AND QuestionNo =?");
                        this.ps.setInt(1, this.sid);
                        this.ps.setString(2, this.questionNo);
                        ResultSet rs = this.ps.executeQuery();
                        if (rs.next()) {
                            InputStream inputstream = rs.getBinaryStream(1);
                            this.myChoiceC = (StreamedContent) new DefaultStreamedContent(inputstream, "image/png");
                        }
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "ChoiceC upload confirmation", " You successfully uploaded ChoiceC for: SID= " + this.sid + "  Question No = " + this.questionNo);
                        RequestContext.getCurrentInstance().showMessageInDialog(message);
                    } catch (Exception e) {
                        System.out.println("Exception-File Upload." + e.getMessage());
                        System.out.println("Inserting image NOTE successfully done!");
                    } finally {
                        this.ps.close();
                        this.Close();
                    }
                }
            } catch (Exception e) {
                FacesMessage msg = new FacesMessage("ChoiceC doc is NOT uploaded successfully", " pls, fill the form correctly and submit " + e);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_WARN, "ivalid photo selected! ", "  The maximum file size allowed is: 15kb "));
        }
    }

    public void uploadChoiceD() throws IOException, ClassNotFoundException, SQLException, Exception {
        
        this.Connector();//
        
        retriveCurrentSIDFromUI();
        retriveCurrentQuestionNoFromUI();
        Class.forName("com.mysql.jdbc.Driver");
        this.ps = this.getCn().prepareStatement("select ChoiceD from upload_choiced where sid =? AND QuestionNo=?");
        this.ps.setInt(1, this.sid);
        this.ps.setString(2, this.questionNo);
        ResultSet rss = this.ps.executeQuery();
        if (rss.next()) {
            InputStream inputstream2 = rss.getBinaryStream(1);
            this.myChoiceD = (StreamedContent) new DefaultStreamedContent(inputstream2, "image/png");
            FacesMessage messag = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ChoiceD upload confirmation", " ChoiceD for: SID = " + this.sid + "  and QuestionNo = " + this.questionNo + "  already exist.");
            RequestContext.getCurrentInstance().showMessageInDialog(messag);
        } else if (this.choiceDFile.getSize() != 0L && this.choiceDFile.getSize() <= file_Size) {
            try {
                retriveCurrentSIDFromUI();
                retriveCurrentQuestionNoFromUI();
                InputStream fin2 = this.choiceDFile.getInputstream();
                Class.forName("com.mysql.jdbc.Driver");
                this.ps = this.getCn().prepareStatement("INSERT INTO upload_choiced values(?,?,?)");
                this.ps.setInt(1, this.sid);
                this.ps.setString(2, this.questionNo);
                this.ps.setBinaryStream(3, fin2, this.choiceDFile.getSize());
                this.ps.executeUpdate();
                this.Close();
                 
                if (this.choiceDFile.getSize() != 0L && this.choiceDFile.getSize() <= file_Size) {
                    try {
                        retriveCurrentSIDFromUI();
                        retriveCurrentQuestionNoFromUI();
                        System.out.println(this.choiceDFile.getFileName());
                        Class.forName("com.mysql.jdbc.Driver");
                        this.ps = this.getCn().prepareStatement("select ChoiceD from upload_choiced where sid =? AND QuestionNo =?");
                        this.ps.setInt(1, this.sid);
                        this.ps.setString(2, this.questionNo);
                        ResultSet rs = this.ps.executeQuery();
                        if (rs.next()) {
                            InputStream inputstream = rs.getBinaryStream(1);
                            this.myChoiceD = (StreamedContent) new DefaultStreamedContent(inputstream, "image/png");
                        }
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "ChoiceD upload confirmation", " You successfully uploaded ChoiceD for: SID= " + this.sid + "  Question No = " + this.questionNo);
                        RequestContext.getCurrentInstance().showMessageInDialog(message);
                    } catch (Exception e) {
                        System.out.println("Exception-File Upload." + e.getMessage());
                        System.out.println("Inserting image NOTE successfully done!");
                    } finally {
                        this.ps.close();
                        this.Close();
                    }
                }
            } catch (Exception e) {
                FacesMessage msg = new FacesMessage("ChoiceD doc is NOT uploaded successfully", " pls, fill the form correctly and submit " + e);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_WARN, "ivalid photo selected! ", "  The maximum file size allowed is: 15kb "));
        }
    }

    public void uploadChoiceE() throws IOException, ClassNotFoundException, SQLException, Exception {
        
        this.Connector();//
        
        retriveCurrentSIDFromUI();
        retriveCurrentQuestionNoFromUI();
        Class.forName("com.mysql.jdbc.Driver");
        this.ps = this.getCn().prepareStatement("select ChoiceE from upload_choicee where sid =? AND QuestionNo=?");
        this.ps.setInt(1, this.sid);
        this.ps.setString(2, this.questionNo);
        ResultSet rss = this.ps.executeQuery();
        if (rss.next()) {
            InputStream inputstream2 = rss.getBinaryStream(1);
            this.myChoiceE = (StreamedContent) new DefaultStreamedContent(inputstream2, "image/png");
            FacesMessage messag = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ChoiceE upload confirmation", " ChoiceE for: SID = " + this.sid + "  and QuestionNo = " + this.questionNo + "  already exist.");
            RequestContext.getCurrentInstance().showMessageInDialog(messag);
        } else if (this.choiceEFile.getSize() != 0L && this.choiceEFile.getSize() <= file_Size) {
            try {
                retriveCurrentSIDFromUI();
                retriveCurrentQuestionNoFromUI();
                InputStream fin2 = this.choiceEFile.getInputstream();
                Class.forName("com.mysql.jdbc.Driver");
                this.ps = this.getCn().prepareStatement("INSERT INTO upload_choicee values(?,?,?)");
                this.ps.setInt(1, this.sid);
                this.ps.setString(2, this.questionNo);
                this.ps.setBinaryStream(3, fin2, this.choiceEFile.getSize());
                this.ps.executeUpdate();
                this.ps.close();
                
                if (this.choiceEFile.getSize() != 0L && this.choiceEFile.getSize() <= file_Size) {
                    try {
                        retriveCurrentSIDFromUI();
                        retriveCurrentQuestionNoFromUI();
                        System.out.println(this.choiceEFile.getFileName());
                        Class.forName("com.mysql.jdbc.Driver");
                        this.ps = this.con.prepareStatement("select ChoiceE from upload_choicee where sid =? AND QuestionNo =?");
                        this.ps.setInt(1, this.sid);
                        this.ps.setString(2, this.questionNo);
                        ResultSet rs = this.ps.executeQuery();
                        if (rs.next()) {
                            InputStream inputstream = rs.getBinaryStream(1);
                            this.myChoiceD = (StreamedContent) new DefaultStreamedContent(inputstream, "image/png");
                        }
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "ChoiceE upload confirmation", " You successfully uploaded ChoiceE for: SID= " + this.sid + "  Question No = " + this.questionNo);
                        RequestContext.getCurrentInstance().showMessageInDialog(message);
                    } catch (Exception e) {
                        System.out.println("Exception-File Upload." + e.getMessage());
                        System.out.println("Inserting image NOTE successfully done!");
                    } finally {
                        this.ps.close();
                        this.con.close();
                    }
                }
            } catch (Exception e) {
                FacesMessage msg = new FacesMessage("ChoiceE doc is NOT uploaded successfully", " pls, fill the form correctly and submit " + e);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_WARN, "ivalid photo selected! ", "  The maximum file size allowed is: 15kb "));
        }
    }

    public void clear() {
        setSid(0);
        setQuestionNo(null);
        setMyInstructions(null);
    }
}
