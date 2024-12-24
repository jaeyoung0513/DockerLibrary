FROM mysql:8.0

COPY ./J_Library_Dump.sql /docker-entrypoint-initdb.d

ENV MYSQL_ROOT_PASSWORD=71028776
ENV MYSQL_DATABASE=J_Library
ENV MYSQL_HOST=%