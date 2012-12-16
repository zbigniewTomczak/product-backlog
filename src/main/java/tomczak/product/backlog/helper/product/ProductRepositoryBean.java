package tomczak.product.backlog.helper.product;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import tomczak.product.backlog.model.Product;
import tomczak.product.backlog.model.ProductUserRole;
import tomczak.product.backlog.model.Role;

@Stateless
public class ProductRepositoryBean {

	@Inject EntityManager em;
	
	public List<Product> getUserProductOnwershipList(Long id) {
		if (id == null) {
			return Collections.emptyList();
					
		}
		
		return em.createNamedQuery(ProductUserRole.GET_BY_USER_ID_AND_ROLE_ID, Product.class)
			.setParameter("userId", id)
			.setParameter("roleId", Role.PRODUCT_MANAGER_ROLE_ID)
			.getResultList();
	}

}
