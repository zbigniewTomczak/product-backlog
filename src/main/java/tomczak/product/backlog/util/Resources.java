package tomczak.product.backlog.util;

import java.util.Date;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;

@RequestScoped
public class Resources {
   
   @SuppressWarnings("unused")
   @Produces
   private Date today= new Date();
   
   @Produces
   public Logger produceLog(InjectionPoint injectionPoint) {
      return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
   }
   
   @Produces
   @RequestScoped
   public FacesContext produceFacesContext() {
       return FacesContext.getCurrentInstance();
   }
}
