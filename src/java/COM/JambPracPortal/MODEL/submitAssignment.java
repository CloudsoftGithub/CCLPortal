package COM.JambPracPortal.MODEL;

import org.primefaces.model.UploadedFile;

public class submitAssignment {
  public int sid;
  
  public String programm;
  
  public String doc;
  
  private String batch;
  
  public String title;
  
  private UploadedFile AssignmentFile;
  
  private String username;
  
  public int getSid() {
    return this.sid;
  }
  
  public void setSid(int sid) {
    this.sid = sid;
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
  
  public UploadedFile getAssignmentFile() {
    return this.AssignmentFile;
  }
  
  public void setAssignmentFile(UploadedFile AssignmentFile) {
    this.AssignmentFile = AssignmentFile;
  }
  
  public String getUsername() {
    return this.username;
  }
  
  public void setUsername(String username) {
    this.username = username;
  }
}
