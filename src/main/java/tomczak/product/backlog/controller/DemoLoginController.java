package tomczak.product.backlog.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import tomczak.product.backlog.helper.user.UserSessionBean;

@Named @RequestScoped
public class DemoLoginController {
	@Inject private UserSessionBean userBean;
	public String login (Long userId) {
		userBean.setCurrentUserId(userId);
		return "myProducts";
	}
}
