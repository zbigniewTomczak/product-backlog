package tomczak.product.backlog.test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import javax.ejb.EJBException;
import javax.enterprise.inject.Instance;
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
import org.junit.Test;
import org.junit.runner.RunWith;

import tomczak.product.backlog.DuplicateProductException;
import tomczak.product.backlog.EmailExistsException;
import tomczak.product.backlog.EmailMalformedException;
import tomczak.product.backlog.helper.EntityHelper;
import tomczak.product.backlog.helper.product.CurrentProductBean;
import tomczak.product.backlog.helper.product.ProductConstraintBean;
import tomczak.product.backlog.helper.product.ProductCreateBean;
import tomczak.product.backlog.helper.product.ProductRepositoryBean;
import tomczak.product.backlog.helper.product.ProductSessionBean;
import tomczak.product.backlog.helper.user.UserDataBean;
import tomczak.product.backlog.helper.user.UserManagerBean;
import tomczak.product.backlog.model.Product;
import tomczak.product.backlog.model.Role;
import tomczak.product.backlog.model.User;
import tomczak.product.backlog.qualifiers.CurrentProduct;
import tomczak.product.backlog.util.EntityManagerProducer;

@RunWith(Arquillian.class)
public class ProductIncrementalTest {
	private static final String CORRECT_EMAIL = "myEmail@marec.com";
	private static final String INCORRECT_EMAIL = "muciomucio";
	private static final String CORRECT_PRODUCT_NAME = "System optimization";
	private static final String CORRECT_SURNAME = "Smith";

	@Deployment
	public static Archive<?> createTestArchive() {
		WebArchive wa = ShrinkWrap
				.create(WebArchive.class, "test.war")
				.addAsResource("META-INF/test-persistence.xml",
						"META-INF/persistence.xml")
				.addAsResource("import.sql", "import.sql")
				.addAsWebInfResource("test-ds.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addClass(EntityManagerProducer.class)
				.addClass(EntityHelper.class)
				.addClass(UserManagerBean.class)
				.addClass(UserDataBean.class)
				.addClass(EmailExistsException.class)
				.addClass(EmailMalformedException.class)
				.addClass(ProductCreateBean.class)
				.addClass(ProductConstraintBean.class)
				.addClass(ProductRepositoryBean.class)
				.addClass(ProductSessionBean.class)
				.addClass(CurrentProductBean.class)
				.addClass(DuplicateProductException.class)
				.addClass(CurrentProduct.class)
				.addPackage(User.class.getPackage());
		System.out.println(wa.toString(true));
		return wa;
	}

	@Inject EntityHelper entityHelper;
	
	@Inject UserManagerBean userManagerBean; 
	
	@Inject ProductCreateBean productCreateBean;
	@Inject ProductRepositoryBean productRepositoryBean;
	@Inject ProductConstraintBean productConstraintBean;
	@Inject ProductSessionBean productSessionBean;
	@Inject CurrentProductBean currentProductBean;
	
	@Test
	public void envirinmentCheck() {
		Role role = entityHelper.findById(Role.class, Role.PRODUCT_MANAGER_ROLE_ID);
		Assert.assertNotNull(role);
		role = entityHelper.findById(Role.class, Role.CONTRIBUTOR_ROLE_ID);
		Assert.assertNotNull(role);	
		role = entityHelper.findById(Role.class, Role.READONLY_ROLE_ID);
		Assert.assertNotNull(role);
	}

	@Test @InSequence(1) 
	public void ProductCreateBean_createProduct_wrongUserId_throwsException() {
		try {
			productCreateBean.create(CORRECT_PRODUCT_NAME, 7L);
		} catch (Exception e) {
			return;
		}
		fail();
	}

	@Test @InSequence(2) 
	public void UserManagerBean_createUser_emailProvided_returnsOne() {
		Long id = userManagerBean.createUser(CORRECT_EMAIL, null, null);
		Assert.assertEquals(1L, id.longValue());
	}

	@Test @InSequence(3) 
	public void ProductCreateBean_createProduct_correctName_createsProduct() {
		Product product = productCreateBean.create(CORRECT_PRODUCT_NAME, 1L);
		assertEquals(CORRECT_PRODUCT_NAME, product.getName());
	}
	
	@Test @InSequence(4) 
	public void ProductCreateBean_createProduct_theSameName_throwsDuplicateProductException() {
		try {
			productCreateBean.create(CORRECT_PRODUCT_NAME, 1L);
		} catch (DuplicateProductException e) {
			return;
		}
		fail();
	}
	
	@Test @InSequence(5) 
	public void ProductConstraintBean_alreadyExists_existingName_returnsTrue() {
		assertTrue(productConstraintBean.alreadyExists(CORRECT_PRODUCT_NAME, 1L));
	}
	
	@Test @InSequence(6) 
	public void ProductRepositoryBean_getUserProductOnwershipList_retursOneProduct() {
		List<Product> list = productRepositoryBean.getUserProductOnwershipList(1L);
		assertEquals(1, list.size());
	}
	
	@Inject @CurrentProduct Instance<Long> currentProductId;
	@Test @InSequence(7)
	public void ProductSessionBean_getSetCurrentProductId() {
		List<Product> list = productRepositoryBean.getUserProductOnwershipList(1L);
		Long productId = list.get(0).getId();
		productSessionBean.setCurrentProductId(productId);
		assertEquals(productId, currentProductId.get());
	}
	
	@Inject @Named("currentProduct") Instance<Product> currentProduct;
	@Test @InSequence(8)
	public void CurrentProductBean_getCurrentProduct_producesProduct() {
		List<Product> list = productRepositoryBean.getUserProductOnwershipList(1L);
		Long productId = list.get(0).getId();
		productSessionBean.setCurrentProductId(productId);		
		assertNotNull(currentProduct.get());
	}
}