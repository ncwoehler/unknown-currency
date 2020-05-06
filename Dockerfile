FROM openjdk:11-jre-slim

WORKDIR /

COPY build/libs/unknown-currency-all.jar /unknown-currency-all.jar
COPY run.sh /run.sh

EXPOSE 8080/tcp
EXPOSE 7979/tcp

RUN chmod +x /run.sh

ENTRYPOINT ["/bin/bash", "-c"]

CMD ["./run.sh"]