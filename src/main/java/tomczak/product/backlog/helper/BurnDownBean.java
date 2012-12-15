package tomczak.product.backlog.helper;

import java.util.Date;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import tomczak.product.backlog.helper.item.ItemOpenessVector;
import tomczak.product.backlog.model.Item;
import tomczak.product.backlog.model.Product;
import tomczak.product.backlog.qualifiers.CurrentProduct;

@Stateless
public class BurnDownBean {
	@Inject EntityManager em;
	@Inject @CurrentProduct Long id;
	@Inject Date today;
	
	@Produces
	public ItemOpenessVector getItemOpenessVector() {
		ItemOpenessVector v;
		Product product = em.find(Product.class, id);
		if (product.getStartDate() == null) {
			v = new ItemOpenessVector(today, today);
		} else {
			v = new ItemOpenessVector(product.getStartDate(), today);
		}
		//em.refresh(product);
		
		for (Item item : product.getItems()) {
			v = v.addOpenings(item.getEvents());
		}
		return v;
	}
	
}
