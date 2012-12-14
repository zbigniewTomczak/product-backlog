package tomczak.product.backlog.model;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;


@Entity
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	public final static Long PRODUCT_MANAGER_ROLE_ID = 1L;
	public final static Long CONTRIBUTOR_ROLE_ID = 2L;
	public final static Long READONLY_ROLE_ID = 3L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Long id = null;
	
	@Column(unique = true, nullable = true)
	private String name;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
   
}
