package tomczak.product.backlog.helper.product;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;

import tomczak.product.backlog.qualifiers.CurrentProduct;

@SessionScoped
public class ProductSessionBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long currentProductId = null;

	@Produces
	@CurrentProduct
	public Long currentProductId() {
		return currentProductId;
	}

	public void setCurrentProductId(Long id) {
		currentProductId = id;
	}
	
	
}
