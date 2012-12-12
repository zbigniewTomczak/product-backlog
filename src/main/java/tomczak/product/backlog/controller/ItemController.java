package tomczak.product.backlog.controller;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import tomczak.product.backlog.helper.ItemEventsBean;

@Named @RequestScoped
public class ItemController {
	
	@Inject private ItemEventsBean itemEventsBean;
	@Inject private FacesContext fcx;
	
	public void open(Long itemId) {
		boolean openSuccess = itemEventsBean.open(itemId);
	}
	
	public void close(Long itemId) {
		boolean closeSuccess = itemEventsBean.close(itemId);
	}
}
