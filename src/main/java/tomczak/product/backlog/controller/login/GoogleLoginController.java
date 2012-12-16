package tomczak.product.backlog.controller.login;

import java.io.IOException;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.MessageException;
import org.openid4java.message.ax.FetchRequest;

import tomczak.product.backlog.servlet.GoogleLoginComeBackServlet;

@Named @RequestScoped
public class GoogleLoginController implements LoginController {
	final static String GOOGLE_ENDPOINT = "https://www.google.com/accounts/o8/id";
	@Inject private FacesContext fc;
	@Inject private ConsumerManager manager;
	
	@Override
	public void login() {
		Object request = fc.getExternalContext().getRequest();
		if ( request instanceof HttpServletRequest) {
			HttpServletRequest sevletRequest = (HttpServletRequest) request;
			System.out.println(sevletRequest.getServerName());
			System.out.println(sevletRequest.getServerPort());
			StringBuffer requestURL = sevletRequest.getRequestURL();
			int toIndex = requestURL.indexOf(sevletRequest.getRequestURI());
			if (toIndex == -1) {
				//TODO report error end exit
			}
			
			requestURL.delete(toIndex, requestURL.length());
			String path = fc.getExternalContext().getRequestContextPath() + GoogleLoginComeBackServlet.SERVLET_PATH;
			requestURL.append(path);
			System.out.println(requestURL);
			List discoveries = null;
			try {
				discoveries = manager.discover(GOOGLE_ENDPOINT);
			} catch (DiscoveryException e2) {
				//TODO report error end exit
				e2.printStackTrace();
			}

			// attempt to associate with the OpenID provider
			// and retrieve one service endpoint for authentication
			DiscoveryInformation discovered = manager.associate(discoveries);

			// store the discovery information in the user's session
			ExternalContext externalContext = fc.getExternalContext();
			Object session = externalContext.getSession(true);
			if (!(session instanceof HttpSession)) {
				//TODO report error and exit
			}
			HttpSession httpSession = (HttpSession) session;
			httpSession.setAttribute("openid-disc", discovered);

			// obtain a AuthRequest message to be sent to the OpenID provider
			AuthRequest authReq = null;
			try {
				authReq = manager.authenticate(discovered, requestURL.toString());
			} catch (MessageException e1) {
				//TODO report error and exit
				e1.printStackTrace();
			} catch (ConsumerException e1) {
				//TODO report error and exit
				e1.printStackTrace();
			}

			FetchRequest fetch = FetchRequest.createFetchRequest();
			try {
				fetch.addAttribute("email",
						"http://axschema.org/contact/email", true);
				fetch.addAttribute("firstName",
						"http://axschema.org/namePerson/first", true);
				fetch.addAttribute("lastName",
						"http://axschema.org/namePerson/last", true);
				// attach the extension to the authentication request
				authReq.addExtension(fetch);
			} catch (MessageException e1) {
				//TODO report error and exit
				e1.printStackTrace();
			}


			try {
				fc.getExternalContext().redirect(authReq.getDestinationUrl(true));
			} catch (IOException e) {
				//TODO report error
				e.printStackTrace();
			}
			
		} else {
			//TODO report error end exit
		}
	}
}
