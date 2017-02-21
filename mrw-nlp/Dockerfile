FROM python:3.6
MAINTAINER Vladimir Iakovlev <nvbn.rm@gmail.com>

EXPOSE 5000

RUN apt-get update -yqq
RUN apt-get upgrade -yqq

RUN adduser --disabled-password --gecos "" mrw-nlp
WORKDIR /home/mrw-nlp
USER mrw-nlp

COPY . /home/mrw-nlp/
USER root
RUN chown -R mrw-nlp /home/mrw-nlp/
RUN pip install -r requirements.txt
USER mrw-nlp
RUN python -m mrw_nlp --initial

CMD python -m mrw_nlp
