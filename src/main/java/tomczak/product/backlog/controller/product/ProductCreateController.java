package tomczak.product.backlog.controller.product;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.inject.Inject;
import javax.inject.Named;

import tomczak.product.backlog.helper.product.ProductConstraintBean;
import tomczak.product.backlog.helper.product.ProductCreateBean;
import tomczak.product.backlog.helper.product.ProductSessionBean;
import tomczak.product.backlog.model.Product;
import tomczak.product.backlog.qualifiers.CurrentUser;
import tomczak.product.backlog.util.JSFMessages;

@Named @RequestScoped
public class ProductCreateController {
	private String newProductName;
	private UIComponent newNameInput;
	
	@Inject private ProductCreateBean productCreateBean;
	@Inject private ProductConstraintBean productConstraintBean;
	@Inject private ProductSessionBean productSessionBean;
	@Inject private JSFMessages jsfMessages;
	@Inject @CurrentUser Long userId;
	
	public String create() {
		boolean exists = productConstraintBean.alreadyExists(newProductName);
		if (exists) {
            jsfMessages.postErrorMessage("Product with this name already exists", newNameInput.getClientId());
            return "products";
		}
		Product product = productCreateBean.create(newProductName, userId);
		if (product != null) {
			productSessionBean.setCurrentProductId(product.getId());
		}
		newProductName = null;
		return "index";
	}
	
	public String getNewProductName() {
		return newProductName;
	}

	public void setNewProductName(String newProductName) {
		this.newProductName = newProductName;
	}

	public UIComponent getNewNameInput() {
		return newNameInput;
	}

	public void setNewNameInput(UIComponent newNameInput) {
		this.newNameInput = newNameInput;
	}
}
