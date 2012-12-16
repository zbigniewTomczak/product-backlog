package tomczak.product.backlog.helper.product;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import tomczak.product.backlog.helper.EntityHelper;
import tomczak.product.backlog.model.Product;
import tomczak.product.backlog.qualifiers.CurrentProduct;

@RequestScoped
public class CurrentProductBean {

	@Inject EntityHelper eh;
	private Product product;

	@Produces
	@Named
	public Product getCurrentProduct(@CurrentProduct Long id) {
		if (id != null) {
			if(product == null) {
				product = eh.findById(Product.class, id);
			} 
			return product;
		}
		
		return null;
	}
	
}
