# VMware Tanzu Application Service > Hoover UI

## How to Run with Maven

If you intend to run `cf-hoover-ui` in a local development environment, you must first:

* Launch a standalone instance of [Eureka server](https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-eureka-server.html)
* Launch an instance of [cf-hoover](https://github.com/cf-toolsuite/cf-hoover#how-to-run-with-gradle)

Then:

```
./mvw spring-boot:run -Dspring-boot.run.profiles={target_foundation_profile}
```
where `{target_foundation_profile}` is something like `pws` or `pcfone`

> You'll need to manually stop to the application with `Ctrl+C`
