package tomczak.product.backlog.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
	@NamedQuery(name=ProductUserRole.GET_BY_USER_ID_AND_ROLE_ID,
			query="SELECT p.product FROM ProductUserRole p WHERE p.user.id = :userId  AND p.role.id = :roleId"),
	@NamedQuery(name=ProductUserRole.GET_BY_PRODUCT_NAME_USER_ID_AND_ROLE_ID,
			query="SELECT p.product.id FROM ProductUserRole p WHERE p.user.id = :userId  AND p.product.name = :name AND p.role.id = :roleId")
})
public class ProductUserRole implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String GET_BY_USER_ID_AND_ROLE_ID = "ProductUserRole.getByUserIdAndRoleId";
	public static final String GET_BY_PRODUCT_NAME_USER_ID_AND_ROLE_ID = "ProductUserRole.getByProductNameUserIdAndRoleId";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Long id = null;

	@ManyToOne(optional = false)
	private Product product;
	
	@ManyToOne(optional = false)
	private User user;
	
	@ManyToOne(optional = false)
	private Role role;

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}   
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}   
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
   
	
}
