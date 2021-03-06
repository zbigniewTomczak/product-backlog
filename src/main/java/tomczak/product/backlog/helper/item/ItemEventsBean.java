package tomczak.product.backlog.helper.item;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import tomczak.product.backlog.helper.EntityHelper;
import tomczak.product.backlog.model.Item;
import tomczak.product.backlog.model.ItemEvent;
import tomczak.product.backlog.model.Status;
import tomczak.product.backlog.qualifiers.CurrentProduct;

@Stateless
public class ItemEventsBean {

	@Inject @CurrentProduct Long id;
	
	@Inject private Date today;
	@Inject private EntityHelper entityHelper;
	@Inject private EntityManager em;

	@Inject @Any javax.enterprise.event.Event<Item> itemChangeEvent;
	
	public boolean open(Long currentItemId) {
		return newStatus(currentItemId, Status.OPEN_STATUS_ID);
	}

	public boolean close(Long currentItemId) {
		return newStatus(currentItemId, Status.CLOSE_STATUS_ID);
	}
	
	private boolean newStatus(Long itemId, Long newStatusId) {
		Item item = entityHelper.findById(Item.class, itemId);
		if (item == null) {
			return false;
		}
		
		Status status = entityHelper.findById(Status.class, newStatusId);
		if (status == null) {
			return false;
		}
		
		item.setStatus(status);
		List<ItemEvent> list = em.createNamedQuery(ItemEvent.GET_BY_DATE_AND_ITEM_ID, ItemEvent.class)
								.setParameter("date", today)
								.setParameter("itemId", item.getId())
								.getResultList();
		ItemEvent itemEvent;
		if (list.size() > 0) {
			itemEvent = list.get(0);
		} else {
			itemEvent = new ItemEvent();
			itemEvent.setDate(today);
			itemEvent.setItem(item);
		}
		itemEvent.setStatus(status);
		em.persist(itemEvent);
		
		itemChangeEvent.fire(item);
		return true;
	}
}
