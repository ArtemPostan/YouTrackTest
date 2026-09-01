package based;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.util.Optional;

public class TestResultLoggerExtension implements TestWatcher {

    @Override
    public void testSuccessful(ExtensionContext context) {
        System.out.println("✅ ТЕСТ УСПЕШЕН: " + context.getDisplayName());
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        System.out.println("❌ ТЕСТ ПРОВАЛЕН: " + context.getDisplayName());
        System.out.println("   Причина ошибки: " + cause.getMessage());
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        System.out.println("⚠️ ТЕСТ ПРЕРВАН: " + context.getDisplayName());
    }
}