package tomczak.product.backlog.helper;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.New;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import tomczak.product.backlog.model.Product;
import tomczak.product.backlog.qualifiers.Current;

@SessionScoped
public class CurrentProductBean implements Serializable {

	private transient EntityHelper entityHelper;
	private Product currentProduct;

	@Inject
	public void init(@New EntityHelper entityHelper) {
		//TODO create real functionality for selecting product
		this.entityHelper = entityHelper;
		currentProduct = entityHelper.findById(Product.class, 1L);
	}
	
	@Produces
	@Current
	@Named
	public Product getCurrentProduct() {
		return currentProduct;
	}
}
