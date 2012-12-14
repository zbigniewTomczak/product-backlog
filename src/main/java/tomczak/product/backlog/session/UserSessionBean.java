package tomczak.product.backlog.session;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@SessionScoped @Named
public class UserSessionBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String email;
	private String firstName;
	private String lastName;
	
	public String logout() {
		email = null;
		firstName = null;
		lastName = null;
		return "login";
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
}
