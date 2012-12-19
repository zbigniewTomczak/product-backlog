package tomczak.product.backlog.util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

public class JSFMessages {
	@Inject FacesContext fc;
	
	public void postInfoMessage(String message) {
		postInfoMessage(message, null);
	}

	public void postInfoMessage(String message, String clientId) {
		postMessage(message, clientId, FacesMessage.SEVERITY_INFO);
	}

	public void postErrorMessage(String message) {
		postErrorMessage(message, null);
	}

	public void postErrorMessage(String message, String clientId) {
		postMessage(message, clientId, FacesMessage.SEVERITY_ERROR);
	}

	private void postMessage(String message, String clientId, Severity severity) {
		FacesMessage m = new FacesMessage(severity, message, message);
        fc.addMessage(clientId, m);
        fc.getExternalContext().getFlash().setKeepMessages(true);		
	}
}
