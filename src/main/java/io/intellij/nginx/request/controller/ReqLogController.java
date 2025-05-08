package io.intellij.nginx.request.controller;

import io.intellij.nginx.request.entities.NginxRequestLog;
import io.intellij.nginx.request.services.InternalQueueService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

/**
 * LogController
 *
 * @author tech@intellij.io
 * @since 2025-04j-30
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/log")
@RestController
public class ReqLogController {
    private final InternalQueueService internalQueueService;

    @GetMapping("/record/{domain}")
    public ResponseEntity<Void> saveLog(@PathVariable String domain, HttpServletRequest request) {
        NginxRequestLog requestLog = NginxRequestLog.builder()
                .id(UUID.randomUUID().toString())
                .domain(domain)
                .ts(new Date())
                .protocol(request.getHeader("X-Proto") != null ? request.getHeader("X-Proto") : "http")
                .method(request.getHeader("X-Method") != null ? request.getHeader("X-Method") : request.getMethod())
                .requestUri(request.getHeader("X-URI"))
                .remoteAddr(request.getHeader("X-IP") != null ? request.getHeader("X-IP") : request.getRemoteAddr())
                .build();

        internalQueueService.add(requestLog);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
