package io.intellij.nginx.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * NginxRequestLoggingApp
 *
 * @author tech@intellij.io
 * @since 2025-04-30
 */
@EnableElasticsearchRepositories
@SpringBootApplication
@RestController
public class NginxRequestLoggingApp {

    public static void main(String[] args) {
        SpringApplication.run(NginxRequestLoggingApp.class, args);
    }

    @GetMapping("/")
    public String root() {
        return "nginx-request-logging";
    }

    @ControllerAdvice
    @Slf4j
    static class GlobalExceptionHandler {
        @ExceptionHandler(Exception.class)
        public ResponseEntity<String> handleException(Exception e) {
            log.error("handleException|{}", e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
