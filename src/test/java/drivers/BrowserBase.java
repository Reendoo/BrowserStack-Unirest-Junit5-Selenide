package drivers;

import org.openqa.selenium.remote.DesiredCapabilities;

import static browserStack.BsConfigHelper.genConfigValue;

public class BrowserBase {
    private static DesiredCapabilities capabilities;
    public static String getUrl(){
        return genConfigValue("base-url");
    }
    public static DesiredCapabilities getCapabilities() {
        if(capabilities==null){
            capabilities = new DesiredCapabilities();
        }
        return capabilities;
    }
}
