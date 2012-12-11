package tomczak.product.backlog.controller;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;
import javax.inject.Named;

import tomczak.product.backlog.helper.ItemRepositoryBean;
import tomczak.product.backlog.model.Item;

@Named @RequestScoped
public class ItemListController {
	List<Item> openItemsList;
	List<Item> closedItemsList;
	
	@Inject ItemRepositoryBean itemRepositoryBean;
	
	@SuppressWarnings("unused")
	@Inject
	private void init() {
		openItemsList = itemRepositoryBean.getOpenItemsForCurrentProduct();
		closedItemsList = itemRepositoryBean.getClosedItemsForCurrentProduct();
	}
	
	public void update(@Observes(notifyObserver=Reception.IF_EXISTS) Item item) {
		itemRepositoryBean.init();
		init();
	}

	public List<Item> getOpenItemsList() {
		return openItemsList;
	}

	public void setOpenItemsList(List<Item> openItemsList) {
		this.openItemsList = openItemsList;
	}

	public List<Item> getClosedItemsList() {
		return closedItemsList;
	}

	public void setClosedItemsList(List<Item> closedItemsList) {
		this.closedItemsList = closedItemsList;
	}
	
	
}
