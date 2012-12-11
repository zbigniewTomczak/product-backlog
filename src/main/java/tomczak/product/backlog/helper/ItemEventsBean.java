package tomczak.product.backlog.helper;

import java.util.Date;

import javax.ejb.Stateless;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import tomczak.product.backlog.model.Event;
import tomczak.product.backlog.model.Item;
import tomczak.product.backlog.model.Product;
import tomczak.product.backlog.model.Status;
import tomczak.product.backlog.qualifiers.Current;

@Stateless
public class ItemEventsBean {

	@Inject @Current 
	private Product currentProduct;
	
	@Inject private Date today;
	@Inject private EntityHelper entityHelper;

	@Inject @Any javax.enterprise.event.Event<Item> itemEvent;
	
	public boolean create(String newItemName) {
		Item item = new Item();
		item.setName(newItemName);
		item.setProduct(currentProduct);
		entityHelper.persist(item);
		Event event = new Event();
		event.setDate(today);
		event.setItem(item);
		event.setStatus(entityHelper.findById(Status.class, Status.INITIAL_STATUS_ID));
		entityHelper.persist(event);
		System.out.println("Created:" + event.getId() + "(" + event.getId()+")" + event.getItem().getId());
		if (item.getId() != null && event.getId() != null) {
			itemEvent.fire(item);
			return true;
		}
		
		return false;
	}

	public boolean open(Long currentItemId) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean close(Long currentItemId) {
		// TODO Auto-generated method stub
		return false;
	}
}
