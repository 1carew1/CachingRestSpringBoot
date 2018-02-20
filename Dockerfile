FROM java:8
VOLUME /tmp
ADD cachingrest-0.0.1-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Dspring.active.profiles=prod","-jar","/app.jar"]