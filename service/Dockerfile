FROM alpine:latest

RUN apk add --update --no-cache curl py-pip

ADD index.html /code/

EXPOSE 8080
WORKDIR /code
CMD ["/usr/bin/python","-m", "SimpleHTTPServer","8080"]
