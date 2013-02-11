package tomczak.product.backlog.helper.product;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import tomczak.product.backlog.model.ProductUserRole;
import tomczak.product.backlog.model.Role;
import tomczak.product.backlog.qualifiers.CurrentProduct;

@Stateless
public class ProductConstraintBean {

	@Inject EntityManager em;
	
	public boolean alreadyExists(String name, Long userId) {
		List<Long> list = em.createNamedQuery(ProductUserRole.GET_BY_PRODUCT_NAME_USER_ID_AND_ROLE_ID, Long.class)
			.setParameter("userId", userId)
			.setParameter("name", name)
			.setParameter("roleId", Role.PRODUCT_MANAGER_ROLE_ID).getResultList();
		long count = list.size() > 0 ? list.get(0) : 0;
		return count > 0L ? true : false;
	}
}
