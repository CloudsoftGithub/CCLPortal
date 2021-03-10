package COM.JambPracPortal.BEAN;

import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class user {
  private int number;
  
  private String msg;
  
  public String name;
  
  public String country;
  
  public String getCountry() {
    return this.country;
  }
  
  public void setCountry(String country) {
    this.country = country;
  }
  
  public int getNumber() {
    return this.number;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getMsg() {
    return this.msg;
  }
  
  public void check() {
    if (this.name.equalsIgnoreCase("asheer")) {
      this.msg = "very good, pls get couraged";
      this.number = 1;
    } else {
      this.msg = "try again later";
      this.number = 100;
    } 
    RequestContext.getCurrentInstance().update("forma:panela");
  }
  
  public String outcome() {
    FacesContext fc = FacesContext.getCurrentInstance();
    this.country = getCountryParam(fc);
    return "result";
  }
  
  public String getCountryParam(FacesContext fc) {
    Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
    return params.get("country");
  }
}
