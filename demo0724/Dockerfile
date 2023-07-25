FROM openjdk:17
EXPOSE 8093

ADD target/testapi.jar testapi.jar
ENTRYPOINT ["java","-jar","testapi.jar"]