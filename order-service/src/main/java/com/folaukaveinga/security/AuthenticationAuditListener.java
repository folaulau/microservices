package com.folaukaveinga.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationAuditListener {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Async
	@EventListener
    public void onAuditEvent(AuditApplicationEvent event) {
        AuditEvent auditEvent = event.getAuditEvent();
        log.info("Status = {}, {}, {}, {}", auditEvent.getType(), auditEvent.getPrincipal(), auditEvent.getTimestamp(), auditEvent.getData());
    }
}
