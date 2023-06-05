package drivers;

import com.codeborne.selenide.WebDriverProvider;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.annotation.Nonnull;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashMap;

public class ChromeBrowser implements WebDriverProvider {

    @Nonnull
    @Override
    public WebDriver createDriver(@Nonnull Capabilities capabilities) {
        WebDriver driver = null;
        try {
            driver = new RemoteWebDriver(URI.create(BrowserBase.getUrl()).toURL(), getCapabilities());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return driver;
    }

    private static DesiredCapabilities getCapabilities() {
        // Add the following capabilities to your test script
        DesiredCapabilities caps =  BrowserBase.getCapabilities();
        caps.setCapability("browserName", "chrome");
        caps.setCapability("browserVersion", "81.0 beta");
        HashMap<String, Object> browserstackOptions = new HashMap<>();
        browserstackOptions.put("os", "OS X");
        browserstackOptions.put("osVersion", "Catalina");
        caps.setCapability("bstack:options", browserstackOptions);
        return caps;
    }
}
