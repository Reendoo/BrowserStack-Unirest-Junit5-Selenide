package base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import drivers.BrowserBase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;

import java.rmi.Remote;

import static Utils.Utils.generateBuildName;

public class TestBase {
    private static SessionId sessionId;
    @BeforeAll
    static void beforeAll() {
        BrowserBase.getCapabilities().setCapability("buildName",generateBuildName());
        Configuration.baseUrl = "https://www.kiwi.com/en";
        Configuration.browserSize = "1920x1080";
    }

    @BeforeEach
    void setUp(TestInfo info) {
        var name = info.getDisplayName();
        BrowserBase.getCapabilities().setCapability("name", name);
    }

    @AfterEach
    void tearDown() {
        sessionId = ((RemoteWebDriver)WebDriverRunner.getWebDriver()).getSessionId();
        WebDriverRunner.getWebDriver().quit();
    }

    public static SessionId getSessionId() {
        return sessionId;
    }
}
