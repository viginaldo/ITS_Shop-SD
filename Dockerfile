FROM tomcat:10.1-jre17

# Remove app padrão
RUN rm -rf /usr/local/tomcat/webapps/*

# Copia o WAR gerado
COPY target/ROOT.war /usr/local/tomcat/webapps/ROOT.war

# Porta
EXPOSE 8080

# Inicia Tomcat
CMD ["catalina.sh", "run"]