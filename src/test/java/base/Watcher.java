package base;

import browserStack.Result;
import browserStack.BrowserStackIntegration;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
public class Watcher implements TestWatcher {
    @Override
    public void testSuccessful(ExtensionContext context) {
        var sessionId = TestBase.getSessionId().toString();
        BrowserStackIntegration.setResult(sessionId,new Result("passed","ok"));
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        var sessionId = TestBase.getSessionId().toString();
        var result = new Result("failed", cause.getMessage());
        BrowserStackIntegration.setResult(sessionId,result);
    }

}
