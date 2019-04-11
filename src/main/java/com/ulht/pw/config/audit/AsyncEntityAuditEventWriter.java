package com.ulht.pw.config.audit;

import java.io.IOException;
import java.lang.reflect.Field;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ulht.pw.domain.BaseEntity;
import com.ulht.pw.domain.audit.EntityAuditEvent;
import com.ulht.pw.repository.audit.EntityAuditEventRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Async Entity Audit Event writer
 * This is invoked by Hibernate entity listeners to write audit event for entitities
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class AsyncEntityAuditEventWriter {

	private final EntityAuditEventRepository auditingEntityRepository;

	private final ObjectMapper objectMapper; // Jackson object mapper

	/**
	 * Writes audit events to DB asynchronously in a new thread
	 */
	@Async
	public void writeAuditEvent(Object target, EntityAuditAction action) {
		log.debug("-------------- Post {} audit  --------------", action.value());
		try {
			EntityAuditEvent auditedEntity = prepareAuditEntity(target, action);
			if (auditedEntity != null) {
				auditingEntityRepository.save(auditedEntity);
			}
		} catch (Exception e) {
			log.error("Exception while persisting audit entity for {} error: {}", target, e);
		}
	}

	/**
	 * Method to prepare auditing entity
	 *
	 * @param entity
	 * @param action
	 * @return
	 */
	private EntityAuditEvent prepareAuditEntity(final Object entity, EntityAuditAction action) {
		EntityAuditEvent auditedEntity = new EntityAuditEvent();
		Class<?> entityClass = entity.getClass(); // Retrieve entity class with reflection
		auditedEntity.setAction(action.value());
		auditedEntity.setEntityType(entityClass.getName());
		Long entityId;
		String entityData;
		log.trace("Getting Entity Id and Content");
		try {
			Field privateLongField = entityClass.getDeclaredField("id");
			privateLongField.setAccessible(true);
			entityId = (Long) privateLongField.get(entity);
			privateLongField.setAccessible(false);
			entityData = objectMapper.writeValueAsString(entity);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException | IOException e) {
			log.error("Exception while getting entity ID and content {}", e);
			// returning null as we dont want to raise an application exception here
			return null;
		}
		auditedEntity.setEntityId(entityId);
		auditedEntity.setEntityValue(entityData);
		final BaseEntity abstractAuditEntity = (BaseEntity) entity;
		if (EntityAuditAction.CREATE.equals(action)) {
			auditedEntity.setLastModifiedBy(abstractAuditEntity.getCreatedBy());
			auditedEntity.setLastModifiedDate(abstractAuditEntity.getCreatedDate());
			auditedEntity.setCommitVersion(1);
		} else {
			auditedEntity.setLastModifiedBy(abstractAuditEntity.getLastModifiedBy());
			auditedEntity.setLastModifiedDate(abstractAuditEntity.getLastModifiedDate());
			calculateVersion(auditedEntity);
		}
		log.trace("Audit Entity --> {} ", auditedEntity.toString());
		return auditedEntity;
	}

	private void calculateVersion(EntityAuditEvent auditedEntity) {
		log.trace("Version calculation. for update/remove");
		Integer lastCommitVersion = auditingEntityRepository.findMaxCommitVersion(auditedEntity
				.getEntityType(), auditedEntity.getEntityId());
		log.trace("Last commit version of entity => {}", lastCommitVersion);
		if (lastCommitVersion != null && lastCommitVersion != 0) {
			log.trace("Present. Adding version..");
			auditedEntity.setCommitVersion(lastCommitVersion + 1);
		} else {
			log.trace("No entities.. Adding new version 1");
			auditedEntity.setCommitVersion(1);
		}
	}
}