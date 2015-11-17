FROM java:8
VOLUME /tmp
ADD *.jar dataflow-admin-lattice.jar
RUN bash -c 'touch /dataflow-admin-lattice.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/dataflow-admin-lattice.jar"]
