package tomczak.product.backlog.helper.user;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import tomczak.product.backlog.EmailExistsException;
import tomczak.product.backlog.model.User;
import tomczak.product.backlog.model.UserDataName;

@Stateless
public class UserManagerBean {
	@Inject EntityManager em;
	@Inject UserDataBean userDataBean;
	
	public Long getUserId (String email) {
		if (email == null) {
			throw new NullPointerException();
		}
		
		List<User> list = em.createNamedQuery(User.GET_BY_EMAIL, User.class)
				.setParameter("email", email).getResultList();
		if (list.size() > 0) {
			return list.get(0).getId();
		}
		
		return null;
	}
	
	public Long createUser(String email, String firstName, String lastName) throws EmailExistsException{
		if (email == null) {
			throw new NullPointerException();
		}
		
		List<User> list = em.createNamedQuery(User.GET_BY_EMAIL, User.class)
				.setParameter("email", email).getResultList();
		if (list.size() > 0) {
			throw new EmailExistsException();
		}
		
		User user = new User();
		user.setEmail(email);
		em.persist(user);
		em.flush();
		
		if (firstName!= null) {
			userDataBean.setStringUserDataId(user.getId(), UserDataName.FIRST_NAME_ID, firstName);
		}
		
		if (lastName != null) {
			userDataBean.setStringUserDataId(user.getId(), UserDataName.LAST_NAME_ID, lastName);
		}
		
		return user.getId();
	}
	
	public Long createUser(String email, String password, String firstName, String lastName) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-512");
		} catch (Exception e) {
			e.printStackTrace();
		}
		byte[] passBytes = password.getBytes();
		byte[] hash = md.digest(passBytes);
		//try {
			//String passHash = new org.jboss.sasl.util.UsernamePasswordHashUtil().generateHashedHexURP(email, "ProductBacklogRealm",password.toCharArray());
			//byte[] passHash2 = new org.jboss.sasl.util.UsernamePasswordHashUtil().generateHashedURP(email, "ProductBacklogRealm",password.toCharArray());
			//String passwordHash = org.jboss.sasl.util.HexConverter.convertToHexString(hash);
			String passwordHash2 = tob64(hash);
			String passwordHash3 = encodeBase16(hash);
			//System.out.println("kokokoko: "+ passwordHash);
			User u = new User();
			u.setEmail(email);
			u.setPassword(passwordHash2);
			System.out.println("###"+passwordHash2);
			em.persist(u);
			return u.getId();
		//} catch (NoSuchAlgorithmException e) {
		//	e.printStackTrace();
		//}
		//return null;
	}

	public boolean login(final String login, final char[] password) {
		final User u = em.find(User.class, getUserId(login));
		System.out.println("***"+u.getPassword());
		try {
			LoginContext lc = new LoginContext("dupa zdzisia", new CallbackHandler() {
				
				@Override
				public void handle(Callback[] callbacks) throws IOException,
						UnsupportedCallbackException {
					for (Callback callback : callbacks) {
						handle (callback);
					}
					
				}

				private void handle(Callback callback) throws UnsupportedCallbackException {
					if (callback instanceof NameCallback) {
						((NameCallback) callback).setName(login);
					} else
					if (callback instanceof PasswordCallback) {
						MessageDigest md = null;
						try {
							md = MessageDigest.getInstance("MD5");
						} catch (Exception e) {
							e.printStackTrace();
						}
						byte[] passBytes = new String(password).getBytes();
						byte[] hash = md.digest(passBytes);
						String passwordHash3 = encodeBase16(hash);
						System.out.println("&&&"+passwordHash3);
						System.out.println(u.getPassword().equals(passwordHash3));
						((PasswordCallback) callback).setPassword(password);
					} else {
						 throw new UnsupportedCallbackException(callback, "Unrecognized Callback");
					}
					
				}
			});
			lc.login();
			return true;
		} catch (LoginException e) {
			e.printStackTrace();
			return false;
		}
		
		
		
	}
	
	 private static final char[] base64Table =
			   "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz./".toCharArray();
			   public static final String BASE64_ENCODING = "BASE64";
			   public static final String BASE16_ENCODING = "HEX";

			   // These functions assume that the byte array has MSB at 0, LSB at end.
			   // Reverse the byte array (not the String) if this is not the case.
			   // All base64 strings are in natural order, least significant digit last.
			   public static String tob64(byte[] buffer)
			   {
			      boolean notleading = false;
			      int len = buffer.length, pos = len % 3, c;
			      byte b0 = 0, b1 = 0, b2 = 0;
			      StringBuffer sb = new StringBuffer();

			      switch(pos)
			      {
			         case 1:
			            b2 = buffer[0];
			            break;
			         case 2:
			            b1 = buffer[0];
			            b2 = buffer[1];
			            break;
			      }
			      do
			      {
			         c = (b0 & 0xfc) >>> 2;
			         if(notleading || c != 0)
			         {
			            sb.append(base64Table[c]);
			            notleading = true;
			         }
			         c = ((b0 & 3) << 4) | ((b1 & 0xf0) >>> 4);
			         if(notleading || c != 0)
			         {
			            sb.append(base64Table[c]);
			            notleading = true;
			         }
			         c = ((b1 & 0xf) << 2) | ((b2 & 0xc0) >>> 6);
			         if(notleading || c != 0)
			         {
			            sb.append(base64Table[c]);
			            notleading = true;
			         }
			         c = b2 & 0x3f;
			         if(notleading || c != 0)
			         {
			            sb.append(base64Table[c]);
			            notleading = true;
			         }
			         if(pos >= len)
			            break;
			         else
			         {
			            try
			            {
			               b0 = buffer[pos++];
			               b1 = buffer[pos++];
			               b2 = buffer[pos++];
			            }
			            catch(ArrayIndexOutOfBoundsException e)
			            {
			               break;
			            }
			         }
			      } while(true);

			      if(notleading)
			         return sb.toString();
			      else
			         return "0";
			   }
			   
			   
			   
			   public static String encodeBase16(byte[] bytes)
			    {
			       StringBuffer sb = new StringBuffer(bytes.length * 2);
			       for (int i = 0; i < bytes.length; i++)
			       {
			          byte b = bytes[i];
			          // top 4 bits
			          char c = (char)((b >> 4) & 0xf);
			          if(c > 9)
			             c = (char)((c - 10) + 'a');
			          else
			             c = (char)(c + '0');
			          sb.append(c);
			          // bottom 4 bits
			          c = (char)(b & 0xf);
			          if (c > 9)
			             c = (char)((c - 10) + 'a');
			          else
			             c = (char)(c + '0');
			          sb.append(c);
			       }
			       return sb.toString();
			    }

}
