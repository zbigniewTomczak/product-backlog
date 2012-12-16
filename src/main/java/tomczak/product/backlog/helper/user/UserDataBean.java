package tomczak.product.backlog.helper.user;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import tomczak.product.backlog.model.User;
import tomczak.product.backlog.model.UserData;
import tomczak.product.backlog.model.UserDataName;

@Stateless
public class UserDataBean {
	@Inject EntityManager em;
	
	public Long setStringUserDataId(Long userId, Long userDataNameId, String data) {
		UserData userData = getUserDataByUserIdAndDataNameId(userId, userDataNameId);
		if (userData != null) {
			userData.setStringData(data);
			return userData.getId();
		}
		
		userData = new UserData();
		userData.setStringData(data);
		User user = em.find(User.class, userId);
		userData.setUser(user);
		UserDataName userDataName = em.find(UserDataName.class, userDataNameId);
		userData.setUserDataName(userDataName);
		
		em.persist(userData);
		
		return userData.getId();
	}
	
	public String getStringUserData (Long userId, Long userDataNameId) {
		UserData userData = getUserDataByUserIdAndDataNameId(userId, userDataNameId);
		return userData.getStringData() != null ? userData.getStringData() : null;
	}
	
	public UserData getUserDataByUserIdAndDataNameId(Long userId, Long userDataNameId) {
		List<UserData> list = em.createNamedQuery(UserData.GET_BY_USER_ID_AND_NAME_ID,UserData.class)
				.setParameter("userId", userId)
				.setParameter("userDataNameId", userDataNameId)
				.getResultList();
			if (list.size() > 0) {
				return list.get(0);
			}
			
			return null;
	}
}
