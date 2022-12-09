package net.gabor7d2.pcbuilder;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class UtilsTest {
    @Test
    void copyFileFromClasspathTest() {
        // test if returns null on non-existing dir
        File result = Utils.copyFileFromClasspath("ignored", new File("notDir"));
        assertNull(result);

        // test if non-existing file on classpath copied to existing dir returns null
        result = Utils.copyFileFromClasspath("notFile", new File("src"));
        assertNull(result);

        // test if file is successfully copied
        File testFile = new File("./out/settings.json");
        testFile.delete();
        result = Utils.copyFileFromClasspath("/settings.json", new File("./out"));
        assertNotNull(result);
        assertTrue(testFile.isFile());

        // clean up
        testFile.delete();
    }
}
