package com.ulht.pw.config.audit;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class EntityAuditEventListener extends AuditingEntityListener {

	private static BeanFactory beanFactory;

	@PostPersist
	public void onPostCreate(Object target) {
		try {
			AsyncEntityAuditEventWriter asyncEntityAuditEventWriter = beanFactory.getBean(AsyncEntityAuditEventWriter.class);
			asyncEntityAuditEventWriter.writeAuditEvent(target, EntityAuditAction.CREATE);
		} catch (NoSuchBeanDefinitionException e) {
			log.error("No bean found for AsyncEntityAuditEventWriter");
		} catch (Exception e) {
			log.error("Exception while persisting create audit entity {}", e);
		}
	}

	@PostUpdate
	public void onPostUpdate(Object target) {
		try {
			AsyncEntityAuditEventWriter asyncEntityAuditEventWriter = beanFactory.getBean(AsyncEntityAuditEventWriter.class);
			asyncEntityAuditEventWriter.writeAuditEvent(target, EntityAuditAction.UPDATE);
		} catch (NoSuchBeanDefinitionException e) {
			log.error("No bean found for AsyncEntityAuditEventWriter");
		} catch (Exception e) {
			log.error("Exception while persisting update audit entity {}", e);
		}
	}

	@PostRemove
	public void onPostRemove(Object target) {
		try {
			AsyncEntityAuditEventWriter asyncEntityAuditEventWriter = beanFactory.getBean(AsyncEntityAuditEventWriter.class);
			asyncEntityAuditEventWriter.writeAuditEvent(target, EntityAuditAction.DELETE);
		} catch (NoSuchBeanDefinitionException e) {
			log.error("No bean found for AsyncEntityAuditEventWriter");
		} catch (Exception e) {
			log.error("Exception while persisting delete audit entity {}", e);
		}
	}

	static void setBeanFactory(BeanFactory beanFactory) {
		EntityAuditEventListener.beanFactory = beanFactory;
	}
}
