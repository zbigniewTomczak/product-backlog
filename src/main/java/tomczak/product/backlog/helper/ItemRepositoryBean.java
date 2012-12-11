package tomczak.product.backlog.helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import tomczak.product.backlog.model.Item;
import tomczak.product.backlog.model.Product;
import tomczak.product.backlog.model.Status;
import tomczak.product.backlog.qualifiers.Current;

@Stateless
public class ItemRepositoryBean {
	@Inject EntityManager em;
	@Inject @Current Product product;
	
	List<Item> openItemsList = new ArrayList<Item>();
	List<Item> closedItemsList = new ArrayList<Item>();
	
	@SuppressWarnings("unused")
	@Inject
	public void init() {
		openItemsList = new ArrayList<Item>();
		closedItemsList = new ArrayList<Item>();
		List<Item> list = em.createNamedQuery(Item.GET_BY_PRODUCT_ID, Item.class)
				.setParameter("productId", product.getId())
				.getResultList();
		Iterator<Item> iter = list.iterator();
		System.out.println("List.size()" + list.size());
		while(iter.hasNext()) {
			Item item = iter.next();
			em.refresh(item);
			Status status = getCurrentStatusForItemId(item.getId());
			if (Status.OPEN_STATUS_ID.equals(status.getId())) {
				openItemsList.add(item);
			} else if (Status.CLOSE_STATUS_ID.equals(status.getId())) {
				closedItemsList.add(item);
			}
		}
		//TODO order items by date descending
	}
	
	
	public List<Item> getOpenItemsForCurrentProduct() {
		return openItemsList;
	}

	public List<Item> getClosedItemsForCurrentProduct() {
		return closedItemsList;
	}
	
	public Status getCurrentStatusForItemId(Long itemId) {
		Item item = em.find(Item.class, itemId);
		if (item.getEvents().size() == 0) {
			//TODO log error
			System.out.println("ERROR: events size 0 for Item(" + item.getId() +")");
			return null;
		}
		
		return item.getEvents().get(item.getEvents().size() - 1).getStatus();
	}
	
}
