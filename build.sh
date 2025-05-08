#!/bin/bash
# shellcheck disable=SC2164 disable=SC2086 disable=SC1090
SHELL_FOLDER=$(cd "$(dirname "$0")" && pwd)
cd "$SHELL_FOLDER"

export ROOT_URI="https://dev.kubectl.net"
source <(curl -sSL $ROOT_URI/func/log.sh)
source <(curl -sSL $ROOT_URI/func/ostype.sh)

if is_windows; then
  export MSYS_NO_PATHCONV=1
fi

log_info "step 1" "gradle build jar"

bash <(curl -sSL $ROOT_URI/gradle/build.sh) \
  -i "registry.cn-shanghai.aliyuncs.com/iproute/gradle:8.10.2-jdk21-jammy" \
  -c "gradle-cache" \
  -x "gradle clean build -x test --info"

jar_name="nginx-request-logging-1.0.0-SNAPSHOT.jar"

if [ ! -f "build/libs/$jar_name" ]; then
  log "validate" "$jar_name 不存在，打包失败，退出"
  exit 1
fi

log "step 2" "docker build and push"

image="registry.cn-shanghai.aliyuncs.com/iproute/nginx-request-logging"

version="latest"

bash <(curl -sSL $ROOT_URI/docker/build.sh) \
  -i "$image" \
  -v "$version" \
  -r "false" \
  -p "true"
