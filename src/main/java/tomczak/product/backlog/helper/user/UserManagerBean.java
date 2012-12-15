package tomczak.product.backlog.helper.user;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import tomczak.product.backlog.model.User;
import tomczak.product.backlog.model.UserDataName;

@Stateless
public class UserManagerBean {
	@Inject EntityManager em;
	@Inject UserDataBean userDataBean;
	
	public Long getUserId (String email) {
		if (email == null) {
			throw new NullPointerException();
		}
		
		List<User> list = em.createNamedQuery(User.GET_BY_EMAIL, User.class)
				.setParameter("email", email).getResultList();
		if (list.size() > 0) {
			return list.get(0).getId();
		}
		
		return null;
	}
	
	public Long createUser(String email, String firstName, String lastName) {
		if (email == null) {
			throw new NullPointerException();
		}
		
		List<User> list = em.createNamedQuery(User.GET_BY_EMAIL, User.class)
				.setParameter("email", email).getResultList();
		if (list.size() > 0) {
			return list.get(0).getId();
		}
		
		User user = new User();
		user.setEmail(email);
		em.persist(user);
		em.flush();
		
		if (firstName!= null) {
			userDataBean.getStringUserDataId(user.getId(), UserDataName.FIRST_NAME_ID, firstName);
		}
		
		if (lastName != null) {
			userDataBean.getStringUserDataId(user.getId(), UserDataName.LAST_NAME_ID, lastName);
		}
		
		return user.getId();
	}
}
