package tomczak.product.backlog.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import tomczak.product.backlog.helper.ItemOpenessVector;


@Path("/burndown")
@RequestScoped
public class ProductItemsOpenessVectorRESTService {

   @Inject
   private ItemOpenessVector productItemsOpenessVector;
   
   
   @GET
   //@Path("/{id:[0-9][0-9]*}")
   @Produces(MediaType.APPLICATION_JSON)
   public ChartData getProductItemOpenessVector(/*@PathParam("id") long id*/) {
      return productItemsOpenessVector.getChartData();
   }
}
