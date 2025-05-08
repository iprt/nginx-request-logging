FROM registry.cn-shanghai.aliyuncs.com/iproute/openjdk:21-bookworm

MAINTAINER "devops@kubectl.net"

LABEL email="devops@kubectl.net" \
      author="devops"

WORKDIR /opt/app

ADD build/libs/nginx-request-logging-1.0.0-SNAPSHOT.jar nginx-request-logging-1.0.0-SNAPSHOT.jar

EXPOSE 8080

CMD java $JAVA_OPTIONS -jar nginx-request-logging-1.0.0-SNAPSHOT.jar
