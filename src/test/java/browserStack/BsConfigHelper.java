package browserStack;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class BsConfigHelper {
    private static final Config config = ConfigFactory.load("bs.conf");
    public static String genConfigValue(String key){
        return config.getString(key);
    }
}
