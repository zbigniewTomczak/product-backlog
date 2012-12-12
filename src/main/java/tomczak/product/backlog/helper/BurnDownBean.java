package tomczak.product.backlog.helper;

import java.util.Date;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import tomczak.product.backlog.model.Item;
import tomczak.product.backlog.model.Product;
import tomczak.product.backlog.qualifiers.Current;

@Stateless
public class BurnDownBean {
	@Inject EntityManager em;
	@Inject @Current Product product;
	@Inject Date today;
	
	@Produces
	public ItemOpenessVector getItemOpenessVector() {
		ItemOpenessVector v;
		product = em.find(Product.class, product.getId());
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
