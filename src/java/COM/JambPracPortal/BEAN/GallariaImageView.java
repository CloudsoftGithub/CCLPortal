package COM.JambPracPortal.BEAN;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

@ManagedBean
public class GallariaImageView {
  private List<String> images;
  
  @PostConstruct
  public void init() {
    this.images = new ArrayList<>();
    for (int i = 1; i <= 12; i++)
      this.images.add("gallaria" + i + ".gif"); 
  }
  
  public List<String> getImages() {
    return this.images;
  }
}
