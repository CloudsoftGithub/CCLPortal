package COM.JambPracPortal.BEAN;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
public class SelectBooleanView {
  private boolean UseOfEnglish;
  
  private boolean Mathematics;
  
  private boolean Physiscs;
  
  public boolean isUseOfEnglish() {
    return this.UseOfEnglish;
  }
  
  public void setUseOfEnglish(boolean UseOfEnglish) {
    this.UseOfEnglish = UseOfEnglish;
  }
  
  public boolean isMathematics() {
    return this.Mathematics;
  }
  
  public void setMathematics(boolean Mathematics) {
    this.Mathematics = Mathematics;
  }
  
  public boolean isPhysiscs() {
    return this.Physiscs;
  }
  
  public void setPhysiscs(boolean Physiscs) {
    this.Physiscs = Physiscs;
  }
  
  public void addMessage() {
    String summary = this.Physiscs ? "PHYSICS is selected" : "PHYSICS is deselected ";
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
  }
}
