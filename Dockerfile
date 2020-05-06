FROM openjdk:8-jre-slim

WORKDIR /

COPY build/libs/unknown-currency.jar unknown-currency.jar
COPY run.sh /run.sh

EXPOSE 8080/tcp
EXPOSE 7979/tcp

RUN chmod +x /run.sh

ENTRYPOINT ["/bin/bash", "-c"]

CMD ["./run.sh"]