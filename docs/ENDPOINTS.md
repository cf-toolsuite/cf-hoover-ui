# VMware Tanzu Application Service > Hoover UI

* [Endpoints](#endpoints)
  * [Accounting](#accounting)
  * [Snapshot](#snapshot)
  * [Cache refresh](#cache-refresh)

Note: _Accounting_ endpoints are only available when the target foundation hosts Tanzu Application Service.

## Endpoints

These REST endpoints have been exposed for reporting and administrative purposes.

### Accounting

Official system-wide reporting for all foundations registered

```
GET /accounting/applications
```
> Provides summary metrics for application instances by year and month across all registered foundations

```
GET /accounting/service/plans
```
> Provides summary metrics for service instances by year and month and then by plan across all registered foundations

```
GET /accounting/services
```
> Provides summary metrics for service instances by year and month across all registered foundations

```
GET /accounting/tasks
```
> Provides summary metrics for tasks by year and month across all registered foundations


### Snapshot

Point in time capture of all workloads

```
GET /snapshot/detail/ai
```
> Provides filterable list of all applications (by foundation, organization and space)

```
GET /snapshot/detail/si
```
> Provides filterable list of all service instances (by foundation, organization and space)

```
GET /snapshot/detail/users
```
> Provides filterable lists of all user and service accounts

```
GET /snapshot/summary/ai
```
> Provides summary metrics for applications across all registered foundations

```
GET /snapshot/summary/si
```
> Provides summary metrics for service instances across all registered foundations

```
GET /snapshot/demographics
```
> Provides summary metrics for organizations, spaces, user accounts and service accounts across all registered foundations


### Cache refresh

```
POST /cache/refresh
```
> Triggers snapshot data collection from cf-hoover and refreshes cache
