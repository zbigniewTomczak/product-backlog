package tomczak.product.backlog.controller.product;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import tomczak.product.backlog.helper.EntityHelper;
import tomczak.product.backlog.helper.product.ProductSessionBean;
import tomczak.product.backlog.model.Product;

@RequestScoped @Named
public class ProductChooseController {
	@Inject ProductSessionBean productSessionBean;
	@Inject EntityHelper eh;
	public String choose(Long id) {
		Product product = eh.findById(Product.class, id);
		if (product != null) {
			productSessionBean.setCurrentProductId(id);
		}
		
		return "index";
	}
}
