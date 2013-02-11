package tomczak.product.backlog.util;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;

import org.openid4java.association.AssociationSessionType;
import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.InMemoryConsumerAssociationStore;
import org.openid4java.consumer.InMemoryNonceVerifier;

@RequestScoped
public class ConsumerManagerProducer {
	@Produces
	public ConsumerManager getConsumerManager() {
		ConsumerManager manager = null;
		try {
			manager = new ConsumerManager();
			manager.setAssociations(new InMemoryConsumerAssociationStore());
			manager.setNonceVerifier(new InMemoryNonceVerifier(5000));
			manager.setMinAssocSessEnc(AssociationSessionType.DH_SHA256);
		} catch (ConsumerException e) {
			// TODO report error
			e.printStackTrace();
		}
		return manager;
	}
}
