FROM openjdk:8
MAINTAINER Vladimir Iakovlev <nvbn.rm@gmail.com>

EXPOSE 3000

RUN apt-get update -yqq
RUN apt-get upgrade -yqq
RUN apt-get install curl -yqq --no-install-recommends
RUN curl -s https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein > /usr/local/bin/lein
RUN chmod +x /usr/local/bin/lein

RUN adduser --disabled-password --gecos "" mrw-server
WORKDIR /home/mrw-server
USER mrw-server

COPY . /home/mrw-server/
USER root
RUN chown -R mrw-server /home/mrw-server/
USER mrw-server

RUN lein ring uberjar

CMD java -jar target/mrw-server-*-standalone.jar
