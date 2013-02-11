package tomczak.product.backlog.helper.product;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import tomczak.product.backlog.DuplicateProductException;
import tomczak.product.backlog.model.Product;
import tomczak.product.backlog.model.ProductUserRole;
import tomczak.product.backlog.model.Role;
import tomczak.product.backlog.model.User;

@Stateless
public class ProductCreateBean {

	@Inject private EntityManager em;
	@Inject private ProductConstraintBean productConstraintBean;
	
	public Product create(String name, Long userId) throws DuplicateProductException{
		User user = em.find(User.class, userId);
		if (user == null) {
			throw new RuntimeException();
		}
		
		if (productConstraintBean.alreadyExists(name, userId)) {
			throw new DuplicateProductException();
		}
		
		Product product = new Product();
		product.setName(name);
		em.persist(product);
		
		ProductUserRole productUserRole = new ProductUserRole();
		productUserRole.setProduct(product);
		productUserRole.setRole(em.find(Role.class, Role.PRODUCT_MANAGER_ROLE_ID));
		productUserRole.setUser(user);
		em.persist(productUserRole);
		return product;
		
	}
}
