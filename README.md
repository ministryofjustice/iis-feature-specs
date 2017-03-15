# iis-feature-specs
End-to-end tests for the IIS Historical Offenders Application

## Pre-Requisites

### Environment
The following environment variables must be set

* IIS_USER - username for access to IIS app
* IIS_PASSWORD - password for the IIS_USER account
* IIS_URI - root URI for the IIS application. Defaults to `http://localhost:3000`

In addition, the IIS application must be running at IIS_URI

## Execution

Run using `./gradlew test` or execute a specific test using your IDE


## Test organisation

Note the because of the need to log in, many test specs use the `@Stepwise` annotation which
means that all tests in the Spec share the same context. The `setupSpec()` method performs login
and `cleanupSpec()` performs logout.

To make tests in a Spec independent from each other, do not use `@Stepwise` and use `setup()` and
`cleanup()` to login before each test and logout after each test.