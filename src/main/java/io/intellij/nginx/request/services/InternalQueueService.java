package io.intellij.nginx.request.services;

import io.intellij.nginx.request.entities.NginxRequestLog;
import org.springframework.stereotype.Service;

/**
 * InternalQueueService
 *
 * @author tech@intellij.io
 * @since 2025-05-08
 */
@Service
public interface InternalQueueService {

    void add(NginxRequestLog nginxRequestLog);

    void consume(NginxRequestLog nginxRequestLog);

    void stop();
}
