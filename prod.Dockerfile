FROM 844080524398.dkr.ecr.us-east-1.amazonaws.com/tiptap_java:17-ubuntu

EXPOSE 443

COPY target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
