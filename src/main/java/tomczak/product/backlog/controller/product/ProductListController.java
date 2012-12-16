package tomczak.product.backlog.controller.product;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import tomczak.product.backlog.helper.product.ProductRepositoryBean;
import tomczak.product.backlog.model.Product;
import tomczak.product.backlog.qualifiers.CurrentUser;

@RequestScoped
public class ProductListController {
	
	@Inject @CurrentUser Long id;
	@Inject 
	private ProductRepositoryBean productRepositoryBean;
	private List<Product> currentUserProductOnwershipList;

	@SuppressWarnings("unused")
	@Inject
	private void init() {
		currentUserProductOnwershipList = productRepositoryBean.getUserProductOnwershipList(id);
	}
	
	@Produces
	@Named
	public List<Product> getCurrentUserProductOnwershipList() {
		return currentUserProductOnwershipList;
	}
}
