package net.gabor7d2.pcbuilder;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class UtilsTest {
    @Test
    void copyFileFromClasspathTest() {
        File result = Utils.copyFileFromClasspath("ignored", new File("notDir"));
        assertNull(result);

        result = Utils.copyFileFromClasspath("notFile", new File("src"));
        assertNull(result);

        File testFile = new File("./src/test/resources/settingsDir/settings.json");
        testFile.delete();
        result = Utils.copyFileFromClasspath("/settings.json", new File("./src/test/resources/settingsDir"));
        assertNotNull(result);
        assertTrue(testFile.isFile());

        result = Utils.copyFileFromClasspath("./settingsDir", new File("./src/test/resources/settingsDir"));
        assertNull(result);
    }
}
