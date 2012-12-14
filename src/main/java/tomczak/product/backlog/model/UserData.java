package tomczak.product.backlog.model;

import java.io.Serializable;
import javax.persistence.*;

import tomczak.product.backlog.model.User;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"user_id", "userDataName_id"}))
@NamedQueries({
	@NamedQuery(name=UserData.GET_BY_USER_ID_AND_NAME_ID, 
			query="SELECT ud FROM UserData ud WHERE ud.user.id = :userId AND ud.userDataName.id = :userDataNameId")
})
public class UserData implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String GET_BY_USER_ID_AND_NAME_ID = "UserData.getByUserIdAndNameId";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Long id = null;
	
	@ManyToOne(optional=false)
	private User user;
	
	@ManyToOne(optional=false)
	private UserDataName userDataName;

	private String stringData;
	
	public UserData() {
		super();
	}   
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public UserDataName getUserDataName() {
		return userDataName;
	}
	public void setUserDataName(UserDataName userDataName) {
		this.userDataName = userDataName;
	}
	public String getStringData() {
		return stringData;
	}
	public void setStringData(String stringData) {
		this.stringData = stringData;
	}
   
	
}
