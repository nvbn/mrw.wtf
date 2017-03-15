FROM openjdk:8
MAINTAINER Vladimir Iakovlev <nvbn.rm@gmail.com>

EXPOSE 3000

RUN apt-get update -yqq
RUN apt-get upgrade -yqq
RUN apt-get install curl -yqq --no-install-recommends
RUN curl -s https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein > /usr/local/bin/lein
RUN chmod +x /usr/local/bin/lein

RUN adduser --disabled-password --gecos "" mrw-web
WORKDIR /home/mrw-web
USER mrw-web

COPY . /home/mrw-web/
USER root
RUN chown -R mrw-web /home/mrw-web/
USER mrw-web

RUN lein cljsbuild once production
RUN mkdir static
RUN mv resources/public/production/ static/compiled/
RUN cp resources/public/index.html resources/public/favicon.png resources/public/main.css static/
