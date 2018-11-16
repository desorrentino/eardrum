- Download the JME-2.1.1e from https://www.oracle.com/technetwork/java/javase/download-142937.html
- Install the jmf.jar file into your local maven repository. 
  If you don't have maven, just install it with homebrew.

mvn install:install-file -Dfile=jmf.jar -DgroupId=hackathon \
-DartifactId=jmf -Dversion=2.1.1 -Dpackaging=jar

- Open this project in IDEA.