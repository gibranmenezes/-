FROM maven:3-eclipse-temurin-8
WORKDIR /home/folha-salarial/

RUN apt update -y && apt upgrade -y
RUN mnv compile && mvn spring-boot:run

CMD tail -f /dev/null
