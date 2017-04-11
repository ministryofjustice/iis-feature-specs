# iis-feature-specs
End-to-end tests for the IIS Historical Offenders Application

## Pre-Requisites

### Environment
The following environment variables must be set:

* IIS_URI - root URI for the IIS application. Defaults to `http://localhost:3000`

The following must be set if running Sign On in interactive mode:

* IIS_USER - root URI for the IIS application. Defaults to `user`
* IIS_PASSWORD - root URI for the IIS application. Defaults to `password`
* SIGNON_URI - root URI for the IIS application. Defaults to `http://localhost:3001/oauth/authorize`
* PROFILE_URI - root URI for the IIS application. Defaults to `http://localhost:3001/profile`

In addition, the IIS application must be running at IIS_URI, and if you have authentication
enabled then you need the IIS Mock SSO server running too. 

If the mock SSO is running in interactive mode then the SIGNON_URI and PROFILE_URI values must match your SSO server.

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


## Sign On

If running with Sign On required, the tests will perform sign on using the user email and password supplied by the
environment variables given above.

If your test spec extends SignOnBaseSpec, then you can use the signIn() and signOut() functions to execute sign on,
automatically catering for interactive/automatic modes of the mock SSO server or where authorization is switched off.