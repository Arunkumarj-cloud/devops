FROM openjdk:8

RUN apt-get update && apt-get install -y vim
# tzdata for timzone
RUN apt-get install -y tzdata
 
# timezone env with default
ENV TZ Asia/Kolkata
WORKDIR /usr/src/myapp

COPY target/*.jar /usr/src/myapp/ROOT.jar

EXPOSE 7272
ENTRYPOINT ["java", "-Xmx512m", "-Xms512m" , "-jar" , "ROOT.jar"]