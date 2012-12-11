package tomczak.product.backlog.helper;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import tomczak.product.backlog.model.Item;
import tomczak.product.backlog.model.Product;
import tomczak.product.backlog.qualifiers.Current;

@Stateless
public class ItemConstraintBean {

	@Inject EntityManager em;
	@Inject @Current Product product;
	
	public boolean alreadyExists(String name) {
		List<Long> list = em.createNamedQuery(Item.COUNT_BY_NAME_AND_PRODUCT_ID, Long.class)
			.setParameter("productId", product.getId())
			.setParameter("name", name).getResultList();
		long count = list.size() > 0 ? list.get(0) : 0;
		return count > 0L ? true : false;
	}
}
