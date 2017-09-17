FROM stackbrew/ubuntu:13.10
FROM jamesdbloom/docker-java8-maven

WORKDIR .
RUN echo 'ping localhost &' > /bootstrap.sh
RUN echo 'sleep infinity' >> /bootstrap.sh
RUN chmod +x /bootstrap.sh

CMD /bootstrap.sh



