package io.intellij.nginx.request.services;

import io.intellij.nginx.request.entities.NginxRequestLog;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * InternalQueueServiceImpl
 *
 * @author tech@intellij.io
 * @since 2025-05-08
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
@Slf4j
public class InternalQueueServiceImpl implements InternalQueueService, InitializingBean {
    private final NginxRequestLogService nginxRequestLogService;
    private final ThreadPoolTaskExecutor taskExecutor;

    private final BlockingQueue<NginxRequestLog> queue = new LinkedBlockingQueue<>();
    private final AtomicBoolean running = new AtomicBoolean(false);

    @Override
    @Async
    public void add(NginxRequestLog requestLog) {
        try {
            queue.put(requestLog);
        } catch (InterruptedException e) {
            log.error("put occurred error|{}", e.getMessage());
        }
    }

    @Override
    @Async
    public void consume(NginxRequestLog requestLog) {
        try {
            log.info("consume|{}", requestLog);
            String ipv4 = requestLog.getRemoteAddr();
            // 忽略内网IP
            if (ipv4.startsWith("10.") || ipv4.startsWith("192.168.") || ipv4.startsWith("172.")) {
                log.warn("ignore internal ip|{}", ipv4);
                return;
            }
            nginxRequestLogService.save(requestLog);
        } catch (Exception e) {
            log.error("save occurred error|{}", e.getMessage());
        }
    }

    @PreDestroy
    @Override
    public void stop() {
        this.running.set(false);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        running.set(true);
        taskExecutor.execute(() -> {
            log.info("start consume log record queue");
            while (running.get()) {
                try {
                    NginxRequestLog nginxRequestLog = queue.take();
                    this.consume(nginxRequestLog);
                } catch (InterruptedException e) {
                    log.error("take occurred error|{}", e.getMessage());
                }
            }
        });
    }

}

