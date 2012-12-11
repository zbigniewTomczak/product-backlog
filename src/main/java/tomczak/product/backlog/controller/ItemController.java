package tomczak.product.backlog.controller;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import tomczak.product.backlog.helper.EntityHelper;
import tomczak.product.backlog.helper.ItemConstraintBean;
import tomczak.product.backlog.helper.ItemEventsBean;
import tomczak.product.backlog.model.Item;

@Named @RequestScoped
public class ItemController {
	private String newItemName;
	private UIComponent newNameInput;
	
	@Inject private ItemEventsBean itemEventsBean;
	@Inject private ItemConstraintBean itemConstraintBean;
	@Inject private FacesContext fcx;
	
	public String create() {
		boolean itemExists = itemConstraintBean.alreadyExists(newItemName);
		if (itemExists) {
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Item with this name already exists", "Item with this name already exists");
            fcx.addMessage(newNameInput.getClientId(), m);
            return "?faces-redirect=true";
		}
		boolean creationSuccess = itemEventsBean.create(newItemName);
		newItemName = null;
		return "?faces-redirect=true";
	}
	
	public void open(Long itemId) {
		boolean openSuccess = itemEventsBean.open(itemId);
		count++;
		System.out.println(count);
	}
	
	public void close(Long itemId) {
		boolean closeSuccess = itemEventsBean.close(itemId);
		count++;
		System.out.println(count);
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

	
	 private static int count;  
	  
	    public int getCount() {  
	        return count;  
	    }  
	  
	    public void setCount(int count) {  
	        this.count = count;  
	    }  
	      
	    public void increment() {  
	    	System.out.println("increment");
	        count++;  
	    }  
}
