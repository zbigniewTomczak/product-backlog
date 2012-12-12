package tomczak.product.backlog.controller;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import tomczak.product.backlog.helper.ItemConstraintBean;
import tomczak.product.backlog.helper.ItemEventsBean;

@Named @RequestScoped
public class ItemController {
	
	@Inject private ItemEventsBean itemEventsBean;
	@Inject private FacesContext fcx;
	
	public void open(Long itemId) {
		boolean openSuccess = itemEventsBean.open(itemId);
		count++;
		System.out.println(count);
	}
	
	public void close(Long itemId) {
		boolean closeSuccess = itemEventsBean.close(itemId);
		count++;
		System.out.println(count);
	}
	
	 private static int count;  
	  
	    public int getCount() {  
	        return count;  
	    }  
	  
	    public void setCount(int count) {  
	        this.count = count;  
	    }  
	      
	    public void increment() {  
	    	System.out.println("increment");
	        count++;  
	    }  
}
