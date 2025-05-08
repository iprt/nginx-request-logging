# nginx request logging

## nginx 配置

```nginx
location = /save-log-to-es {
    internal;
    proxy_pass http://reqlog.ipcrystal.com/log/record/code.ipcrystal.com;
    
    proxy_set_header X-Proto $saved_scheme;
    proxy_set_header X-Method $saved_method;
    proxy_set_header X-URI $saved_uri;
    proxy_set_header X-IP $saved_ip;
    
    proxy_pass_request_body off;
    proxy_set_header Content-Length "";
}
```


```NGINX
location /your-protected-path {
    # auth_request_set $saved_scheme $scheme;
    # auth_request_set $saved_method $request_method;
    # auth_request_set $saved_uri $request_uri;
    # auth_request_set $saved_ip $remote_addr;
    auth_request /save-log-to-es;
    
    # 可以在这里基于$auth_status的值执行其他操作
    # ...
}

```