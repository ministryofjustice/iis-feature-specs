import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.remote.DesiredCapabilities

driver = {
    // Use the following to run tests in headless mode
    //   new PhantomJSDriver(new DesiredCapabilities())

    // Use the following to set path to chromedriver if not using linux chromedriver
    // System.setProperty('webdriver.chrome.driver', '/usr/local/bin/chromedriver')

    // Use the following to run tests with Chromedriver
    new ChromeDriver()
}

baseUrl = System.getenv('IIS_URI') ?: "http://localhost:3000/"

reportsDir = "build/geb-reports"
