package tomczak.product.backlog.test;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import javax.ejb.EJBException;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.New;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import tomczak.product.backlog.EmailExistsException;
import tomczak.product.backlog.EmailMalformedException;
import tomczak.product.backlog.helper.EntityHelper;
import tomczak.product.backlog.helper.user.CurrentUserBean;
import tomczak.product.backlog.helper.user.CurrentUserDataBean;
import tomczak.product.backlog.helper.user.UserDataBean;
import tomczak.product.backlog.helper.user.UserManagerBean;
import tomczak.product.backlog.helper.user.UserSessionBean;
import tomczak.product.backlog.model.User;
import tomczak.product.backlog.model.UserDataName;
import tomczak.product.backlog.qualifiers.CurrentUser;
import tomczak.product.backlog.util.EntityManagerProducer;

@RunWith(Arquillian.class)
public class IncrementalTest {
	private static final String CORRECT_EMAIL = "myEmail@marec.com";
	private static final String INCORRECT_EMAIL = "muciomucio";
	private static final String CORRECT_NAME = "Marec";
	private static final String CORRECT_SURNAME = "Smith";

	@Deployment
	public static Archive<?> createTestArchive() {
		WebArchive wa = ShrinkWrap
				.create(WebArchive.class, "test.war")
				.addAsResource("META-INF/test-persistence.xml",
						"META-INF/persistence.xml")
				.addAsResource("import.sql", "import.sql")
				.addAsWebInfResource("test-ds.xml")
				.addAsWebInfResource("jboss-web.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addClass(EntityManagerProducer.class)
				.addClass(EntityHelper.class)
				.addClass(UserManagerBean.class)
				.addClass(UserDataBean.class)
				.addClass(UserSessionBean.class)
				.addClass(CurrentUserBean.class)
				.addClass(CurrentUserDataBean.class)
				.addClass(EmailExistsException.class)
				.addClass(EmailMalformedException.class)
				.addPackage(User.class.getPackage())
				//.addPackage(UsernamePasswordHashUtil.class.getPackage())
				;
		System.out.println(wa.toString(true));
		return wa;
	}

	@Inject EntityHelper entityHelper;
	
	@Inject UserManagerBean userManagerBean; 
	@Inject UserDataBean userDataBean;
	@Inject UserSessionBean userSessionBean;
	@Inject CurrentUserBean currentUserBean;
	@Inject CurrentUserDataBean currentUserDataBean;
	
	@Test
	public void envirinmentCheck() {
		UserDataName firstName = entityHelper.findById(UserDataName.class, UserDataName.FIRST_NAME_ID);
		Assert.assertNotNull(firstName);
		UserDataName lastName = entityHelper.findById(UserDataName.class, UserDataName.LAST_NAME_ID);
		Assert.assertNotNull(lastName);
	}
	
	
	@Test @InSequence(1) 
	public void UserManagerBean_createUser_emailProvided_returnsOne() {
		Long id = userManagerBean.createUser(CORRECT_EMAIL, null, null);
		Assert.assertEquals(1L, id.longValue());
	}

	@Test @InSequence(2) 
	public void UserManagerBean_getUserId_emailProvided_returnsOne() {
		Long id = userManagerBean.getUserId(CORRECT_EMAIL);
		Assert.assertEquals(1L, id.longValue());
	}
	
	@Test @InSequence(3) 
	public void UserManagerBean_createUser_existingEmailProvided_throwsEmailExistsException() {
		try {
			userManagerBean.createUser(CORRECT_EMAIL, null, null);
		} catch (EmailExistsException e) {
			return;
		}
		
		fail();
	}

	@Test @InSequence(4)
	public void UserDataBean_setGetStringUserDataId_firstUserNameSurname_Persists() {
		userDataBean.setStringUserDataId(1L, UserDataName.FIRST_NAME_ID, CORRECT_NAME);
		userDataBean.setStringUserDataId(1L, UserDataName.LAST_NAME_ID, CORRECT_SURNAME);
		
		String name = userDataBean.getStringUserData(1L, UserDataName.FIRST_NAME_ID);
		String surname = userDataBean.getStringUserData(1L, UserDataName.LAST_NAME_ID);
		Assert.assertEquals(CORRECT_NAME, name);
		Assert.assertEquals(CORRECT_SURNAME, surname);		
	}

	@Test @InSequence(5) 
	public void UserManagerBean_createUser_emailNameSurname_insertCorrectData() {
		final String PREFIX = "dual";
		Long id = userManagerBean.createUser(PREFIX + CORRECT_EMAIL,CORRECT_NAME,CORRECT_SURNAME);
		String name = userDataBean.getStringUserData(id, UserDataName.FIRST_NAME_ID);
		String surname = userDataBean.getStringUserData(id, UserDataName.LAST_NAME_ID);
		Assert.assertEquals(CORRECT_NAME, name);
		Assert.assertEquals(CORRECT_SURNAME, surname);
	}
	
	@Ignore
	@Test @InSequence(6) 
	public void UserManagerBean_createUser_malformedEmail_throwsEmailMailformedException() {
		try {
			userManagerBean.createUser(INCORRECT_EMAIL, null, null);
		} catch (EmailMalformedException e) {
			return;
		}
		
		fail();
	}
	
	@Test @InSequence(7) 
	public void UserManagerBean_createUser_nullEmail_throwsNUllPointerException() {
		try {
			userManagerBean.createUser(null, null, null);
		} catch (EJBException e) {
			if (e.getCausedByException() instanceof NullPointerException)
				return;
		}
		
		fail();
	}	
	
	@Test @InSequence(8) 
	public void UserSessionBean_setCurrentUserId_firstUser_isLogged() {
		userSessionBean.setCurrentUserId(1L);
		assertTrue(userSessionBean.isLogged());
	}
	
	@Test @InSequence(9)
	public void UserSessionBean_logout_isLogout() {
		userSessionBean.logout();
		assertFalse(userSessionBean.isLogged());
	}
	
	@Inject @CurrentUser Instance<Long> currentUserId;
	@Test @InSequence(10)
	public void UserSessionBean_correctUserId_properlyInjected() {
		assertEquals(null, currentUserId.get());
		userSessionBean.setCurrentUserId(1L);
		assertEquals(1L, currentUserId.get().longValue());
	}
	
	@Inject @Named("currentUser") Instance<User> currentUser;
	@Test @InSequence(11) 
	public void CurrentUserBean_currentUser_properlyInjected() {
		userSessionBean.setCurrentUserId(1L);
		assertEquals(1L, currentUser.get().getId().longValue());
	}
	
	@Inject @Named("currentUserFirstName") Instance<String> currentUserFirstName;
	@Test @InSequence(12) 
	public void CurrentUserDataBean_currentUserFirstName_properlyInjected() {
		userSessionBean.setCurrentUserId(1L);
		assertEquals(CORRECT_NAME, currentUserFirstName.get());
	}
	
	@Inject @Named("currentUserLastName") Instance<String> currentUserLastName;
	@Test @InSequence(13) 
	public void CurrentUserDataBean_currentUserLastName_properlyInjected() {
		userSessionBean.setCurrentUserId(1L);
		assertEquals(CORRECT_SURNAME, currentUserLastName.get());
	}
	
	@Test @InSequence(60)
	public void tt() {
		assertNotNull(userManagerBean.createUser("kokos2", "koko", null, null));
	}
	@Test @InSequence(60)
	public void tt1() {
		assertTrue(userManagerBean.login("kokos2", "koko".toCharArray()));
	}
}