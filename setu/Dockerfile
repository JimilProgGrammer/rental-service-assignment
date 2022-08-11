FROM maven:3.8.2-jdk-8
WORKDIR /setu
COPY . .
ENV MYSQL_ROOT_USERNAME=root
ENV MYSQL_ROOT_PASSWORD=setu123
RUN mvn clean install
CMD mvn spring-boot:run
