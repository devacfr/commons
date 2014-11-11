/**
 * Copyright 2014 devacfr<christophefriederich@mac.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cfr.commons.testing;

import javax.annotation.Nonnull;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

/**
 * This class allow to migrate form JUnit 3.x syntax to JUnit 4.</p>
 *
 * @since 1.0
 */
public abstract class TestCase extends Assert {

    /**
     * Creates new instance.
     */
    public TestCase() {

    }

    /**
     * Gets the file system path representation of this test class.
     *
     * @return Returns {@code String} representing the file system location path of this test class.
     */
    @Nonnull
    public final String getPackagePath() {
        return this.getClass().getPackage().getName().replace('.', '/');
    }

    /**
     * Sets up the fixture, for example, open a network connection. This method is called before a test is executed (for
     * compatibility).
     *
     * @throws Exception
     *             exception to raise.
     */
    @Before
    @SuppressWarnings("PMD.EmptyMethodInAbstractClassShouldBeAbstract")
    public void setUp() throws Exception {
    }

    /**
     * Tears down the fixture, for example, close a network connection. This method is called after a test is executed
     * (for compatibility).
     *
     * @throws Exception
     *             exception to raise.
     */
    @After
    @SuppressWarnings("PMD.EmptyMethodInAbstractClassShouldBeAbstract")
    public void tearDown() throws Exception {

    }

    /**
     * Asserts that {@code text} contains all {@code substrings} parameters. If they are not, an {@link AssertionError}
     * is thrown.
     *
     * @param text
     *            the text to assert.
     * @param substrings
     *            list of string must be contained in text.
     */
    public static void assertContains(@Nonnull final String text, @Nonnull final String... substrings) {
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
