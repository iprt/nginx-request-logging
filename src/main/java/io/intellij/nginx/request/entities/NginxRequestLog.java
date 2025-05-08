package io.intellij.nginx.request.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * NginxRequestLog
 *
 * @author tech@intellij.io
 * @since 2025-04-30
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
@Document(indexName = "nginx_request_log")
public class NginxRequestLog {

    @Id
    @Field(type = FieldType.Text)
    private String id;

    @Field(type = FieldType.Text)
    private String domain;

    @Field(type = FieldType.Date)
    private Date ts;

    @Field(type = FieldType.Text)
    private String protocol;

    @Field(type = FieldType.Text)
    private String method;

    @Field(type = FieldType.Text)
    private String requestUri;

    @Field(type = FieldType.Ip)
    private String remoteAddr;

}
