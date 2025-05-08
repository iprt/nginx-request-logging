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
    auth_request /save-log-to-es;
    # ...
}
```
