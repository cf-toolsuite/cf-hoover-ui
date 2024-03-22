# VMware Tanzu Application Service > Hoover UI

## How to Build

```
./mvnw --batch-mode --update-snapshots -DskipTests -Pproduction verify
```

### Alternatives

The below represent a collection of Maven profiles available in the Maven POM.

* Log4J2 logging (log4j2)
  * swaps out [Logback](http://logback.qos.ch/documentation.html) logging provider for [Log4J2](https://logging.apache.org/log4j/2.x/manual/async.html) and [Disruptor](https://lmax-exchange.github.io/disruptor/user-guide/index.html#_introduction)
* Native image (native)
  * uses [Spring AOT](https://docs.spring.io/spring-native/docs/current/reference/htmlsingle/#spring-aot-maven) to compile a native executable with [GraalVM](https://www.graalvm.org/docs/introduction/)


```
./mvnw --batch-mode --update-snapshots -DskipTests -Pproduction,log4j2 verify
```
> Swap out default "lossy" logging provider


```
# Using Cloud Native Buildpacks image
./mvnw spring-boot:build-image -Pnative --batch-mode --update-snapshots -DskipTests -Pproduction

# Using pre-installed Graal CE
./mvnw native:compile --batch-mode --update-snapshots -DskipTests -Pproduction -Pnative
```
