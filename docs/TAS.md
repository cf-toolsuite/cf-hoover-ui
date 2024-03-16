# VMware Tanzu Application Service > Hoover UI

## How to deploy to VMware Tanzu Application Service

Please review the [manifest.yml](../manifest.yml) before deploying.

### using scripts

Deploy the app (bound to a pre-existing instance of Spring Cloud Service Registry)

```
./scripts/deploy.sh
```

Shutdown and delete the app with

```
./scripts/destroy.sh
```
