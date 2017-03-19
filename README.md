# iis-feature-specs
End-to-end tests for the IIS Historical Offenders Application

## Pre-Requisites

### Environment
The following environment variables must be set

* IIS_URI - root URI for the IIS application. Defaults to `http://localhost:3000`

In addition, the IIS application must be running at IIS_URI, and if you have authentication
enabled then you need the IIS Mock SSO server running too.

## Execution

Run using `./gradlew test` or execute a specific test using your IDE

In src.test/resources/GebConfig.groovy you can change from headless mode (Phantom JS)
to browser mode with ChromeDriver. If not using the bundled Linux ChromeDriver, set the
webdriver.chrome.driver property with your ChromeDriver path.


## Test organisation

Note the because of the need to log in, many test specs use the `@Stepwise` annotation which
means that all tests in the Spec share the same context. The `setupSpec()` method performs login
and `cleanupSpec()` performs logout.

To make tests in a Spec independent from each other, do not use `@Stepwise` and use `setup()` and
`cleanup()` to login before each test and logout after each test.