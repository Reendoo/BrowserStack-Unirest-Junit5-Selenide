package browserStack;


import browserStack.Result;
import kong.unirest.Unirest;

import static browserStack.BsConfigHelper.genConfigValue;

public class BrowserStackIntegration {
    public static void setResult(String sessionID, Result result) {
        Unirest.put(genConfigValue("session-endpoint").concat("{sessionId}.json"))
                .routeParam("sessionId", sessionID)
                .header("Content-Type", "application/json")
                .basicAuth(genConfigValue("name"), genConfigValue("automate-key"))
                .body(result)
                .asJson();
    }

    public static String getPublicUrl(String sessionID) {
        return Unirest.get(genConfigValue("session-endpoint").concat("{sessionId}.json"))
                .routeParam("sessionId", sessionID)
                .basicAuth(genConfigValue("name"), genConfigValue("automate-key"))
                .asJson().getBody().getObject().getJSONObject("automation_session").get("public_url").toString();
    }

    public static void main(String[] args) {
        System.out.println(getPublicUrl("4e81a77052870865dce7b9bacba089b290da2acc"));
    }
}
