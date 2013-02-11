package tomczak.product.backlog.controller.item;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import tomczak.product.backlog.helper.item.ItemEventsBean;

@Named @RequestScoped
public class ItemController {
	
	@Inject private ItemEventsBean itemEventsBean;
	
	//TODO add interceptor to check if has modification rights
	public String open(Long itemId) {
		itemEventsBean.open(itemId);
		return "index";
	}
	
	public String close(Long itemId) {
		itemEventsBean.close(itemId);
		return "index";
	}
}
