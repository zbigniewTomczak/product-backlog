package tomczak.product.backlog.helper.item;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import tomczak.product.backlog.model.Item;
import tomczak.product.backlog.qualifiers.CurrentProduct;

@Stateless
public class ItemConstraintBean {

	@Inject EntityManager em;
	@Inject @CurrentProduct Long id;
	
	public boolean alreadyExists(String name) {
		List<Long> list = em.createNamedQuery(Item.COUNT_BY_NAME_AND_PRODUCT_ID, Long.class)
			.setParameter("productId", id)
			.setParameter("name", name).getResultList();
		long count = list.size() > 0 ? list.get(0) : 0;
		return count > 0L ? true : false;
	}
	
	public boolean hasEditRights(Long itemId) {
		//TODO implement
		return false;
	}
}
