# ETAPA 1: BUILD DO PROJETO COM MAVEN
FROM maven:3.9-eclipse-temurin-17 AS builder

# Copia todo o código
COPY . /app
WORKDIR /app

# Gera o WAR
RUN mvn clean package -DskipTests

# ETAPA 2: RUNTIME COM TOMCAT
FROM tomcat:10.1.28-jre17-temurin-jammy

# Remove apps padrão
RUN rm -rf /usr/local/tomcat/webapps/*

# Copia o WAR gerado na etapa anterior
COPY --from=builder /app/target/ROOT.war /usr/local/tomcat/webapps/ROOT.war

# Porta
EXPOSE 8080

# Inicia Tomcat
CMD ["catalina.sh", "run"]