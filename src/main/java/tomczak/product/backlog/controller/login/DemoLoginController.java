package tomczak.product.backlog.controller.login;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;

import tomczak.product.backlog.controller.product.ProductListController;
import tomczak.product.backlog.helper.product.ProductSessionBean;
import tomczak.product.backlog.helper.user.UserSessionBean;
import tomczak.product.backlog.model.Product;

@Named @RequestScoped
public class DemoLoginController {
	@Inject private UserSessionBean userBean;
	@Inject private Instance <ProductListController> productListController;
	@Inject private ProductSessionBean productSessionBean;
	public String login (Long userId) {
		userBean.setCurrentUserId(userId);
		List<Product> list = productListController.get().getCurrentUserProductOnwershipList();
		if (list.size() > 0) {
			setProductAsDefault(list.get(0).getId());
			return "index";
		}
		return "products";
	}
	
	private void setProductAsDefault(Long id) {
		productSessionBean.setCurrentProductId(id);
	}
}
