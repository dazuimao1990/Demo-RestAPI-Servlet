FROM percona:5.7

ENV TZ=Asia/Shanghai \
    MYSQL_DATABASE=todos \
    MYSQL_USER=admin \
    MYSQL_PASSWORD=password \
    MYSQL_ROOT_PASSWORD=password


MAINTAINER guox(guox@goodrain.com)

VOLUME ["/var/lib/mysql", "/var/log/mysql"]

EXPOSE 3306

COPY init.sql /docker-entrypoint-initdb.d
COPY mysqld.cnf /etc/percona-server.conf.d/mysqld.cnf