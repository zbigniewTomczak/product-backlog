package tomczak.product.backlog.model;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;


@Entity
@NamedQueries({
	@NamedQuery(name=User.GET_BY_EMAIL, query="SELECT u FROM User u WHERE u.email = :email")
})
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String GET_BY_EMAIL = "User.getByEmail";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Long id = null;

	@Column(unique = true, nullable = false)
	private String email;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
   
}
