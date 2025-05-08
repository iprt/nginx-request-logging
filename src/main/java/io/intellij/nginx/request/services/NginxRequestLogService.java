package io.intellij.nginx.request.services;

import io.intellij.nginx.request.entities.NginxRequestLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * NginxRequestLogService
 *
 * @author tech@intellij.io
 * @since 2025-04-30
 */
public interface NginxRequestLogService extends ElasticsearchRepository<NginxRequestLog, String> {
}
