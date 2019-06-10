FROM maven:3-jdk-8 as builder
ARG MAVEN_MIRROR_OF=external:*
ARG MAVEN_MIRROR_URL=http://maven.goodrain.me
RUN sed -i "/<mirrors>/a\ <mirror>\n<id>goodrain-repo</id>\n<name>goodrain repo</name>\n<url>$MAVEN_MIRROR_URL</url>\n<mirrorOf>$MAVEN_MIRROR_OF</mirrorOf>\n</mirror>" /usr/share/maven/conf/settings.xml
ADD . /app
WORKDIR /app
RUN mvn clean install

FROM goodrainapps/tomcat:8.5.20-jre8-alpine as runtime
COPY --from=builder  /app/target/DemoJSPServlet-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
