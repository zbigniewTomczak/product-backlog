package tomczak.product.backlog.helper;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import tomczak.product.backlog.model.User;

@Stateless
public class CurrentUserDataBean {

	@Inject EntityManager em;
	
	@Produces
	@Named
	public User getCurrentUser(/*@Current */Long userId) {
		if (userId == null) {
			return null;
		}
		return em.find(User.class, userId);
	}
}
