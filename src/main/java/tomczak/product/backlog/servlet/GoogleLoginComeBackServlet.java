package tomczak.product.backlog.servlet;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openid4java.association.AssociationException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.AuthSuccess;
import org.openid4java.message.MessageException;
import org.openid4java.message.ParameterList;
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.ax.FetchResponse;

import tomczak.product.backlog.session.UserSessionBean;

@WebServlet(GoogleLoginComeBackServlet.SERVLET_PATH)
public class GoogleLoginComeBackServlet extends HttpServlet{
	
	public static final String SERVLET_PATH = "/loginComeBack/google";
	
	@Inject private ConsumerManager manager;
	@Inject private UserSessionBean userBean;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ParameterList response = new ParameterList(	req.getParameterMap());

		// retrieve the previously stored discovery information
		DiscoveryInformation discovered = (DiscoveryInformation) req
				.getSession().getAttribute("openid-disc");

		// extract the receiving URL from the HTTP request
		StringBuffer receivingURL = req.getRequestURL();
		String queryString = req.getQueryString();
		if (queryString != null && queryString.length() > 0)
			receivingURL.append("?").append(req.getQueryString());

		// verify the response; ConsumerManager needs to be the same
		// (static) instance used to place the authentication request
		VerificationResult verification;
		try {
			verification = manager.verify(
					receivingURL.toString(), response, discovered);


		// examine the verification result and extract the verified
		// identifier
		Identifier verified = verification.getVerifiedId();
		if (verified != null) {
			AuthSuccess authSuccess = (AuthSuccess) verification
					.getAuthResponse();

			if (authSuccess.hasExtension(AxMessage.OPENID_NS_AX)) {
				FetchResponse fetchResp = (FetchResponse) authSuccess
						.getExtension(AxMessage.OPENID_NS_AX);

				List emails = fetchResp.getAttributeValues("email");
				List firstName = fetchResp.getAttributeValues("firstName");
				List lastName = fetchResp.getAttributeValues("lastName");
				String email = (String) emails.get(0);
				System.out.println("OpenIdlogin done with email: " + email);
				System.out.println("OpenIdlogin done with firstName: " + firstName.get(0));
				System.out.println("OpenIdlogin done with lastName: " + lastName.get(0));
				//TODO sequre lists
				
				userBean.setEmail(email);
				userBean.setFirstName(firstName.get(0).toString());
				userBean.setLastName(lastName.get(0).toString());
				
				StringBuffer requestURL = req.getRequestURL();
				int toIndex = requestURL.indexOf(req.getRequestURI());
				if (toIndex == -1) {
					//TODO report error end exit
				}
				
				requestURL.delete(toIndex, requestURL.length());
				String path = req.getContextPath() + "/faces/login.xhtml";
				requestURL.append(path);
				
				resp.sendRedirect(path);
				
			}
		}
		} catch (MessageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DiscoveryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AssociationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
