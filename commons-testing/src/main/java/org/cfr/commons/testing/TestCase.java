package org.cfr.commons.testing;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

/**
 * This class allow to migrate form JUnit 3.x syntax to JUnit 4.</p>
 *
 * @since 4.0
 *
 */
@RunWith(BlockJUnit4ClassRunner.class)
public abstract class TestCase extends Assert {

    public TestCase() {

    }

    public final String getPackagePath() {
        return this.getClass().getPackage().getName().replace('.', '/');
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {

    }

    public static void assertContains(String text, String... substrings) {
        int startingFrom = 0;
        for (String substring : substrings) {
            int index = text.indexOf(substring, startingFrom);
            assertTrue(String.format("Expected \"%s\" to contain substring \"%s\"", text, substring),
                index >= startingFrom);
            startingFrom = index + substring.length();
        }

        String lastSubstring = substrings[substrings.length - 1];
        assertTrue(String.format("Expected \"%s\" to contain substring \"%s\" only once),", text, lastSubstring),
            text.indexOf(lastSubstring, startingFrom) == -1);
    }

}
