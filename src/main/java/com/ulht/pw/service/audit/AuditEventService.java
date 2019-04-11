package com.ulht.pw.service.audit;

import java.time.Instant;
import java.util.Optional;

import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ulht.pw.config.audit.AuditEventConverter;
import com.ulht.pw.repository.PersistenceAuditEventRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service for managing audit events.
 * <p>
 * This is the default implementation to support SpringBoot Actuator AuditEventRepository
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AuditEventService {

	private final PersistenceAuditEventRepository persistenceAuditEventRepository;

	private final AuditEventConverter auditEventConverter;

	public Page<AuditEvent> findAll(Pageable pageable) {
		return persistenceAuditEventRepository.findAll(pageable)
				.map(auditEventConverter::convertToAuditEvent);
	}

	public Page<AuditEvent> findByDates(Instant fromDate, Instant toDate, Pageable pageable) {
		return persistenceAuditEventRepository.findAllByAuditEventDateBetween(fromDate, toDate, pageable)
				.map(auditEventConverter::convertToAuditEvent);
	}

	public Optional<AuditEvent> find(Long id) {
		return Optional.ofNullable(persistenceAuditEventRepository.findById(id))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.map(auditEventConverter::convertToAuditEvent);
	}
}
