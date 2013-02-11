package tomczak.product.backlog.test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;

import org.junit.Test;

public class ProvidersTest {
	@Test
	public void listAvailableProviders() {
		for(Provider p: Security.getProviders()) {
			for(Service s: p.getServices()) {
				if (s.getType().equals("MessageDigest")) {
					System.out.println(s.getAlgorithm());
				}
			}
		}
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return;
		}
		System.out.println(convertToHexString(md.digest("abrakadabra".getBytes())).length());
	}
	
	public static String   convertToHexString(byte[] toBeConverted) {

		         if (toBeConverted == null) {
		             throw new NullPointerException("Parameter to be converted can not be null");
		         }
		         char[] converted = new char[toBeConverted.length * 2];
		         for (int i = 0; i < toBeConverted.length; i++) {
		             byte b = toBeConverted[i];
		             converted[i * 2] = HEX_CHARS[b >> 4 & 0x0F];
		             converted[i * 2 + 1] = HEX_CHARS[b & 0x0F];
		         }
		         return String.valueOf(converted);
		     }
	private static final char[] HEX_CHARS = new char[]
			             {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
}
