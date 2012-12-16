package tomczak.product.backlog.controller.item;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import tomczak.product.backlog.helper.item.ItemEventsBean;

@Named @RequestScoped
public class ItemController {
	
	@Inject private ItemEventsBean itemEventsBean;
	@Inject private FacesContext fcx;
	
	public String open(Long itemId) {
		boolean openSuccess = itemEventsBean.open(itemId);
		return "index";
	}
	
	public String close(Long itemId) {
		boolean closeSuccess = itemEventsBean.close(itemId);
		return "index";
	}
}
