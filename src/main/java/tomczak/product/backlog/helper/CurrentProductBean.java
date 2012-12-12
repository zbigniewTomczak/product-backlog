package tomczak.product.backlog.helper;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import tomczak.product.backlog.model.Product;
import tomczak.product.backlog.qualifiers.Current;

@SessionScoped
public class CurrentProductBean implements Serializable {

	private static final long serialVersionUID = 1L;
	//private transient EntityManager em;
	//TODO change to simple product id
	private Product currentProduct;

	@Inject
	public void init(EntityManager em) {
		//TODO create real functionality for selecting product
		//this.em = em;
		currentProduct = em.find(Product.class, 1L);
		System.out.println("Init******************");
	}
	
//	public void refreshProduct(@Observes(notifyObserver=Reception.IF_EXISTS, during=TransactionPhase.AFTER_COMPLETION) Item item ) {
//		currentProduct = em.merge(currentProduct);
//		em.refresh(currentProduct);
//	}
	
	@Produces
	@Current
	@Named
	@RequestScoped
	public Product getCurrentProduct() {
		return currentProduct;
	}
}
