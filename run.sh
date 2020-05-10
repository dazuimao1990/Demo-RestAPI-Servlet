#!/bin/bash
#通过特定环境变量判断是否启动 pinpoint agent
if [[ $ENABLE_APM == "true" ]];then 
 AGENT_ID=${SERVICE_ID:0:10}
 PINPOINT_AGETN_VERSION=1.7.2
 PINPOINT_AGENT_PATH=/app/pinpoint
#将 pinpoint agent 启动参数加⼊到 $JAVA_OPTS 中 
export JAVA_OPTS="$JAVA_OPTS -javaagent:${PINPOINT_AGENT_PATH}/pinpoint-bootstrap-${PINPOINT_AGETN_VERSION}-SNAPSHOT.jar -Dpinpoint.agentId=${AGENT_ID:-${SERVICE_ID:0:10}} -Dpinpoint.applicationName=${APP_NAME:-${SERVICE_NAME:-$HOSTNAME}}"
fi
PORT=${PORT:-5000}
sleep ${PAUSE:-0}
#最终启动命令
exec java $JAVA_OPTS -jar /opt/webapp-runner.jar --port ${PORT} target/*.war