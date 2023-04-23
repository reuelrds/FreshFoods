# Stage 1: Build the Angular app
FROM maven:3.8.7-openjdk-18-slim AS build
WORKDIR /app
COPY ./FreshFoods/ .


ARG MYSQL_USER
ARG MYSQL_PASSWORD
ARG MYSQL_DATABASE
ARG MYSQL_HOST
ARG MYSQL_PORT
ARG SERVLET_CONTEXT_NAME

RUN sed -i "s|\${SERVLET_CONTEXT_NAME}|${SERVLET_CONTEXT_NAME}|g; \
            s|\${DATABASE_USERNAME}|${MYSQL_USER}|g; \
            s|\${DATABASE_PASSWORD}|${MYSQL_PASSWORD}|g; \
            s|\${DATABASE_NAME}|${MYSQL_DATABASE}|g; \
            s|\${MYSQL_HOST}|${MYSQL_HOST}|g; \
            s|\${MYSQL_PORT}|${MYSQL_PORT}|g" ./WebContent/META-INF/context.xml

RUN mvn clean package -DskipTests -e

FROM tomcat:9
COPY --from=build /app/target/FreshFoods.war /usr/local/tomcat/webapps/FreshFoods.war
