package sk.peterrendek.tests;

import base.TestBase;
import base.Watcher;
import com.codeborne.selenide.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(Watcher.class)
public class KiwiTest extends TestBase {
    @RepeatedTest(5)
    @DisplayName("Check price for reservation")
    void itShouldOpenMainPage() {
        Configuration.browser = "drivers.ChromeBrowser";
        open("/");
        WebDriverRunner.getWebDriver().manage().window().maximize();
        setCookieConsent();
        refresh();
        fixProblemWithBooking();

        $(byDataTestAttribute("LandingSearchButton")).click();
        $(byDataTestAttribute("PictureCard")).click();


        var resultCardWrapper = $(byDataTestAttribute("ResultCardWrapper"))
                .shouldBe(Condition.appear, Duration.ofSeconds(15));

        var price = resultCardWrapper.find("strong[class*='PriceText']")
                .shouldNotBe(Condition.empty)
                .getText();

        resultCardWrapper.click();

        var price2 = $(byDataTestAttribute("ModalFooter"))
                .find("div[class*='FooterPriceWrapper']")
                .shouldHave(Condition.text(price))
                .getText();

        $(byDataTestAttribute("ModalFooter"))
                .find(byText("Select"))
                .shouldBe(Condition.appear)
                .shouldBe(Condition.enabled);

        System.out.println("============================================");
        System.out.println("Price1:" + price);
        System.out.println("Price2:" + price2);
        System.out.println("============================================");

    }

    @Test
    @DisplayName("Check price for Nomad reservation")
    void nomadTest() {
        Configuration.browser = "drivers.ChromeBrowser";
        open("/nomad");
        WebDriverRunner.getWebDriver().manage().window().maximize();
        setCookieConsent();
        refresh();

        $(byDataTestAttribute("ExampleContent-asia"))
                .shouldBe(Condition.appear, Duration.ofSeconds(15))
                .click();

        var element = $(byCssSelector("div[class*='NomadResultBookingButton']"))
                .shouldBe(Condition.appear, Duration.ofSeconds(15));
        String text = element.getText();
        element.click();


        var element2 = $(byCssSelector("div[class*='AutoScaleText']"))
                .shouldBe(Condition.appear, Duration.ofSeconds(15));

        String text2 = element2.getText();
        System.out.println(text + " : " + text2);
        assertTrue(text.contains(text2), "Should contains");
    }

    @ParameterizedTest(name = "Check price in YEN")
    @ValueSource(strings = {"drivers.ChromeBrowser", "drivers.EdgeBrowser", "drivers.FirefoxBrowser"})
    void YenTest(String browser) {
        System.out.println(browser);
        Configuration.browser = browser;
        open("/");
        WebDriverRunner.getWebDriver().manage().window().maximize();
        setCookieConsent();
        refresh();
        fixProblemWithBooking();
        fixProblemWithGoogleLogin();
        setCurrency();
        setDestination();
        $(byDataTestAttribute("LandingSearchButton")).click();
        var resultCardWrapper = $(byDataTestAttribute("ResultCardWrapper"))
                .shouldHave(Condition.appear, Duration.ofSeconds(15));

        resultCardWrapper.find("strong[class*='PriceText']")
                .shouldNotBe(Condition.empty)
                .shouldHave(Condition.partialText("¥"));
    }

    private static void setCookieConsent() {
        var cookie = new Cookie("__kwc_agreed", "");
        WebDriverRunner.getWebDriver().manage().addCookie(cookie);
    }

    private static void setDestination() {
        var destination = $(byDataTestAttribute("PlacePickerInput-destination"));

        if (destination.$(byDataTestAttribute("PlacePickerInputPlace-close")).exists()) {
            destination.$(byDataTestAttribute("PlacePickerInputPlace-close")).click();
        }
        destination.click();
        destination.$(byDataTestAttribute("SearchField-input")).setValue("Tokyo");

        $(byCssSelector("div[data-test='PlacePickerRow-city']"))
                .shouldHave(Condition.partialText("Tokyo"), Duration.ofSeconds(15))
                .click();
    }

    private static void setCurrency() {
        var v = $(byCssSelector("button[data-test*='RegionalSettingsButton']"))
                .shouldBe(Condition.appear, Duration.ofSeconds(15));
        v.click();
        $(byDataTestAttribute("CurrencySelect")).selectOptionByValue("jpy");
        $(byDataTestAttribute("SubmitRegionalSettingsButton")).click();
    }

    private static void fixProblemWithGoogleLogin() {
        if (!$(byCssSelector("iframe[src*='https://accounts']")).exists()) {
            return;
        }

        var originFrame = WebDriverRunner.getWebDriver().getWindowHandle();
        WebDriverRunner.getWebDriver().switchTo()
                .frame($(byCssSelector("iframe[src*='https://accounts']")).shouldBe(Condition.appear, Duration.ofSeconds(15)));
        $(By.xpath("//*[@id='close']")).click();
        WebDriverRunner.getWebDriver().switchTo().window(originFrame);
    }

    private static By byDataTestAttribute(String attributeValue) {
        return byAttribute("data-test", attributeValue);
    }

    private static void fixProblemWithBooking() {
        $(By.className("Checkbox__StyledIconContainer-sc-y66hzm-0")).click();
    }
}
