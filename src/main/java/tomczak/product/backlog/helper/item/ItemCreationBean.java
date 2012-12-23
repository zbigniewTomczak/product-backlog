package tomczak.product.backlog.helper.item;

import java.util.Date;
import java.util.List;

import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import tomczak.product.backlog.model.Item;
import tomczak.product.backlog.model.ItemEvent;
import tomczak.product.backlog.model.Product;
import tomczak.product.backlog.model.Status;

@ApplicationScoped @Singleton
public class ItemCreationBean {
	@Inject EntityManager em;
	@Inject @Any javax.enterprise.event.Event<Item> itemChangeEvent;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean createItem(String newItemName,Date today, Long productId) {
		Status initialStatus = em.find(Status.class, Status.INITIAL_STATUS_ID);
		Product currentProduct = em.find(Product.class, productId);
		Integer number = 1;
		List<Integer> resultList = em.createNamedQuery(Item.GET_NEXT_ITEM_NUMBER_FOR_PRODUCT_ID, Integer.class)
				.setParameter("productId", productId)
				.getResultList();
		if (resultList.size() > 0) {
			number = resultList.get(0);
		}
		
		if (number == null) {
			number = 1;
		}
		
		Item item = new Item();
		item.setName(newItemName);
		item.setProduct(currentProduct);
		item.setStatus(initialStatus);
		item.setNumber(number);
		em.persist(item);
		ItemEvent event = new ItemEvent();
		event.setDate(today);
		event.setItem(item);
		event.setStatus(initialStatus);
		em.persist(event);
		if(currentProduct.getStartDate() == null) {
			currentProduct.setStartDate(today);
			em.persist(currentProduct);
		}
		System.out.println("Created:" + event.getId() + "(" + event.getId()+")" + event.getItem().getId());
		if (item.getId() != null && event.getId() != null) {
			itemChangeEvent.fire(item);
			return true;
		}
		
		return false;
		
	}
}
