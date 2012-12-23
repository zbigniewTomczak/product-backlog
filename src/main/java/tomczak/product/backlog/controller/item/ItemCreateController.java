package tomczak.product.backlog.controller.item;

import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.inject.Inject;
import javax.inject.Named;

import tomczak.product.backlog.helper.item.ItemConstraintBean;
import tomczak.product.backlog.helper.item.ItemCreationBean;
import tomczak.product.backlog.qualifiers.CurrentProduct;
import tomczak.product.backlog.util.JSFMessages;

@Named @RequestScoped
public class ItemCreateController {
	private String newItemName;
	private UIComponent newNameInput;
	
	@Inject @CurrentProduct private Long id;
	@Inject private Date today;
	@Inject private ItemCreationBean itemCreationBean;
	@Inject private ItemConstraintBean itemConstraintBean;
	@Inject private JSFMessages jsfMessages;
	
	public String create() {
		boolean itemExists = itemConstraintBean.alreadyExists(newItemName);
		if (itemExists) {
			jsfMessages.postErrorMessage("Item with this name already exists", newNameInput.getClientId());
            return "index";
		}
		boolean creationSuccess = itemCreationBean.createItem(newItemName, today, id);
		if (creationSuccess) {
			jsfMessages.postInfoMessage("Item " + newItemName + " created.");
			return "index";
		}
		newItemName = null;
		return "index";
	}
	
	public String getNewItemName() {
		return newItemName;
	}

	public void setNewItemName(String newItemName) {
		this.newItemName = newItemName;
	}

	public UIComponent getNewNameInput() {
		return newNameInput;
	}

	public void setNewNameInput(UIComponent newNameInput) {
		this.newNameInput = newNameInput;
	}
}
