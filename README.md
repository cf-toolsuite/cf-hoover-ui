# Pivotal Application Service > Hoover UI

[![Build Status](https://travis-ci.org/pacphi/cf-hoover.svg?branch=master)](https://travis-ci.org/pacphi/cf-hoover-ui) [![Known Vulnerabilities](https://snyk.io/test/github/pacphi/cf-hoover-ui/badge.svg)](https://snyk.io/test/github/pacphi/cf-hoover-ui)

> Status: Incubating

You're a platform operator and you've managed to get [cf-hoover](https://github.com/pacphi/) deployed. It's happily aggregating usage data from multiple foundations.  Now your CIO wants you to build a dashboard.  You've come to the right place friend.

## Prerequisites

Required

* [Pivotal Application Service](https://pivotal.io/platform/pivotal-application-service) account


## Tools

* [git](https://git-scm.com/downloads) 2.20.1 or better
* [JDK](http://openjdk.java.net/install/) 11 or better
* [cf](https://docs.cloudfoundry.org/cf-cli/install-go-cli.html) CLI 6.41.0 or better


## Clone

```
git clone https://github.com/pacphi/cf-hoover-ui.git
```


## How to configure

Make a copy of then edit the contents of the `application.yml` file located in `src/main/resources`.  A best practice is to append a suffix representating the target deployment environment (e.g., `application-pws.yml`, `application-pcfone.yml`). You will need to provide administrator credentials to Apps Manager for the foundation if you want the butler to keep your entire foundation tidy.

> You really should not bundle configuration with the application. To take some of the sting away, you might consider externalizing and/or [encrypting](https://blog.novatec-gmbh.de/encrypted-properties-spring/) this configuration.

// TODO Add some notes on how cf-hoover-ui interacts with cf-hoover via Service Registry

### General configuration notes

If you copied and appended a suffix to the original `application.yml` then you would set `spring.profiles.active` to be that suffix

E.g., if you had a configuration file named `application-pws.yml`

```
./gradlew bootRun -Dspring.profiles.active=pws
```

> See the [samples](samples) directory for some examples of configuration when deploying to [Pivotal Web Services](https://login.run.pivotal.io/login) or [PCF One](https://login.run.pcfone.io/login).


## How to Build

```
./gradlew build
```


## How to Run with Gradle

// FIXME Need to add local profile and appropriate scripting and config for tests and running "off platform"

```
./gradlew bootRun -Dspring.profiles.active={target_foundation_profile}
```
where `{target_foundation_profile}` is something like `pws` or `pcfone`

> You'll need to manually stop to the application with `Ctrl+C`


## How to deploy to Pivotal Application Service

Please review the [manifest.yml](manifest.yml) before deploying.

### using scripts

Deploy the app (bound to an instance of Spring Cloud Service Registry)

```
./deploy.sh
```

Shutdown and destroy the app and service instances with

```
./destroy.sh
```

## Credits

* [Vaadin Flow and Reactive Spring](https://committed.software/posts/vaadin-and-spring/vaadin-spring/)