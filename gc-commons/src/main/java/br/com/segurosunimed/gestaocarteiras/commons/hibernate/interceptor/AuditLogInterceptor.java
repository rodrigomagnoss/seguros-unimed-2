package br.com.segurosunimed.gestaocarteiras.commons.hibernate.interceptor;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.Session;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.com.segurosunimed.gestaocarteiras.commons.audit.IAuditCreateEntity;
import br.com.segurosunimed.gestaocarteiras.commons.audit.IAuditUpdateEntity;
import br.com.segurosunimed.gestaocarteiras.commons.converter.BaseConverter;
import br.com.segurosunimed.gestaocarteiras.commons.dto.BaseDTO;
import br.com.segurosunimed.gestaocarteiras.commons.entity.BaseEntity;
import br.com.segurosunimed.gestaocarteiras.commons.exception.BusinessException;
import br.com.segurosunimed.gestaocarteiras.commons.service.history.HistoryEventService;

@Component("auditLogInterceptor")
@Lazy
public class AuditLogInterceptor extends EmptyInterceptor{

	private static final Logger LOGGER = LoggerFactory.getLogger(AuditLogInterceptor.class);
	 
	public AuditLogInterceptor() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	HistoryEventService historyEvent; 

	Session session;
	private Set inserts = new HashSet();
	private Set updates = new HashSet();
	private Set deletes = new HashSet();

	public void setSession(Session session) {
		this.session=session;
	}


	public boolean onSave(Object entity,Serializable id,
			Object[] state,String[] propertyNames,Type[] types)
					throws CallbackException {

		LOGGER.debug("onSave");

		if (entity instanceof IAuditCreateEntity){
			inserts.add(entity);
		}
		return false;

	}

	public boolean onFlushDirty(Object entity,Serializable id,
			Object[] currentState,Object[] previousState,
			String[] propertyNames,Type[] types)
					throws CallbackException {

		LOGGER.debug("onFlushDirty");

		if (entity instanceof IAuditUpdateEntity){
			updates.add(entity);
		}
		return false;

	}

	public void onDelete(Object entity, Serializable id, 
			Object[] state, String[] propertyNames, 
			Type[] types) {

		LOGGER.debug("onDelete");

		if (entity instanceof IAuditCreateEntity){
			deletes.add(entity);
		}
	}

	//called before commit into database
	public void preFlush(Iterator iterator) {
		LOGGER.debug("preFlush");
	}	

	//called after committed into database
	public void postFlush(Iterator iterator) {

		LOGGER.debug("postFlush");

		try{

			for (Iterator it = inserts.iterator(); it.hasNext();) {
				BaseEntity entity = (BaseEntity) it.next();

				Method method = null;
				BaseConverter baseConverter = null;
				String converterMethoInsert = "_toDTOCreate";
				try {
					try {
						baseConverter = getInstanceConverter(entity);
						method = baseConverter.getClass().getMethod(converterMethoInsert, entity.getClass());
						if(method != null) {
							historyEvent.onPostInsert((BaseDTO) method.invoke(baseConverter, entity));
						}
					} catch (Exception e) {
						method = baseConverter.getClass().getMethod(converterMethoInsert, BaseEntity.class);
						if(method != null) {
							historyEvent.onPostInsert((BaseDTO) method.invoke(baseConverter, entity));
						}
					}
				} catch (BusinessException e) {
					e.printStackTrace();
					throw e;
				} catch (Exception e) {
					e.printStackTrace();
				}

				LOGGER.debug("postFlush - insert");
						
			}	

			for (Iterator it = updates.iterator(); it.hasNext();) {
				BaseEntity entity = (BaseEntity) it.next();
				try {

						Method method = null;
						BaseConverter baseConverter = null;
						String converterMethoUpdate = "_toDTOUpdate";
						try {
//							baseConverter = (BaseConverter) Class.forName(entity.getClass().getCanonicalName().replace(".entity",".converter").replace("Entity", "Converter")).newInstance();
							baseConverter = getInstanceConverter(entity);
							
							method = baseConverter.getClass().getMethod(converterMethoUpdate, entity.getClass());
							if(method != null) {
								historyEvent.onPostUpdate((BaseDTO) method.invoke(baseConverter, entity));
							}
						} catch (Exception e) {
							method = baseConverter.getClass().getMethod(converterMethoUpdate, BaseEntity.class);
							if(method != null) {
								historyEvent.onPostUpdate((BaseDTO) method.invoke(baseConverter, entity));
							}
						}

				} catch (BusinessException e) {
					e.printStackTrace();
					throw e;
				} catch (Exception e) {
					e.printStackTrace();
				}

				LOGGER.debug("postFlush - update");
			}	

			for (Iterator it = deletes.iterator(); it.hasNext();) {
				BaseEntity entity = (BaseEntity) it.next();
				LOGGER.debug("postFlush - delete");
			}	

		} finally {
			inserts.clear();
			updates.clear();
			deletes.clear();
		}
	}

	private BaseConverter getInstanceConverter(BaseEntity entity)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		BaseConverter baseConverter;
		String nomeConverter = entity.getNomeConverter() != null ? entity.getNomeConverter() :
			entity.getClass().getCanonicalName().replace(".entity",".converter").replace("Entity", "Converter");
		baseConverter = (BaseConverter) Class.forName(nomeConverter).newInstance();
		return baseConverter;
	}		
}