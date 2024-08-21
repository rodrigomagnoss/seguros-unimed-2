package br.com.segurosunimed.gestaocarteiras.commons.hibernate.listener;

import org.hibernate.HibernateException;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.segurosunimed.gestaocarteiras.commons.service.history.HistoryLogServiceSender;

@Component
public class ReplicationInsertEventListener implements PostInsertEventListener {

	public static final ReplicationInsertEventListener INSTANCE =
			new ReplicationInsertEventListener();

	private static HistoryLogServiceSender histLogServiceSender = new HistoryLogServiceSender();

	@Autowired
	private HistoryLogServiceSender histLogServiceSenderInj;

	@Override
	public void onPostInsert(
			PostInsertEvent event)
					throws HibernateException {
		
		final Object entity = event.getEntity();

	//	histLogServiceSender.sendLogCreate(entity);
		
//		if(entity instanceof Post) {
//			Post post = (Post) entity;
//
//			event.getSession().createNativeQuery(
//					"INSERT INTO old_post (id, title, version) " +
//					"VALUES (:id, :title, :version)")
//			.setParameter("id", post.getId())
//			.setParameter("title", post.getTitle())
//			.setParameter("version", post.getVersion())
//			.setFlushMode(FlushMode.MANUAL)
//			.executeUpdate();
//		}
		
	}

	@Override
	public boolean requiresPostCommitHanding(EntityPersister persister) {
		// TODO Auto-generated method stub
		return false;
	}


}