/**
 * Copyright 2016 devacfr<christophefriederich@mac.com>
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
package org.cfr.commons.testing.hamcrest;

import static org.hamcrest.core.IsEqual.equalTo;

import java.io.File;
import java.io.IOException;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * A slightly modified version of the FileMatchers class
 * <a href="http://www.time4tea.net/wiki/display/MAIN/Testing+Files+with+Hamcrest">published</a> by time4tea. The code
 * is freely distributable, with acknowledgement, under the <a href="http://creativecommons.org/licenses/by/3.0/">CC BY
 * 3.0</a> license.
 * 
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0.2
 */
public class FileMatchers {

    /** Prevents instantiation */
    private FileMatchers() {
        throw new UnsupportedOperationException("Attempt to instantiate utility class");
    }

    /**
     * Create a {@link Matcher} indicating whether the file is a directory.
     * 
     * @return Returns new instance of {@link Matcher} indicating whether the file is a directory.
     */
    public static Matcher<File> isDirectory() {
        return new TypeSafeMatcher<File>() {

            private File fileTested;

            @Override
            public boolean matchesSafely(final File item) {
                fileTested = item;
                return item.isDirectory();
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText(" that ");
                description.appendValue(fileTested);
                description.appendText("is a directory");
            }
        };
    }

    /**
     * Create a {@link Matcher} indicating whether the file exists.
     * 
     * @return Returns new instance of {@link Matcher} indicating whether the file exists.
     */
    public static Matcher<File> exists() {
        return new TypeSafeMatcher<File>() {

            private File fileTested;

            @Override
            public boolean matchesSafely(final File item) {
                fileTested = item;
                return item.exists();
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText(" that file ");
                description.appendValue(fileTested);
                description.appendText(" exists");
            }
        };
    }

    /**
     * Create a {@link Matcher} indicating whether the file denoted by this abstract pathname is a normal file. A file
     * is normal if it is not a directory and, in addition, satisfies other system-dependent criteria. Any non-directory
     * file created by a Java application is guaranteed to be a normal file.
     * 
     * @return Returns new instance of {@link Matcher} indicating whether the file denoted by this abstract pathname
     *         exists and is a normal file.
     */
    public static Matcher<File> isFile() {
        return new TypeSafeMatcher<File>() {

            private File fileTested;

            @Override
            public boolean matchesSafely(final File item) {
                fileTested = item;
                return item.isFile();
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText(" that ");
                description.appendValue(fileTested);
                description.appendText("is a file");
            }
        };
    }

    /**
     * Create a {@link Matcher} indicating whether the application can read the file denoted by this abstract pathname.
     * 
     * @return Returns new instance of {@link Matcher} indicating whether the file specified by this abstract pathname
     *         exists and can be read by the application.
     */
    public static Matcher<File> readable() {
        return new TypeSafeMatcher<File>() {

            private File fileTested;

            @Override
            public boolean matchesSafely(final File item) {
                fileTested = item;
                return item.canRead();
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText(" that file ");
                description.appendValue(fileTested);
                description.appendText("is readable");
            }
        };
    }

    /**
     * Create a {@link Matcher} indicating whether the application can modify the file denoted by this abstract
     * pathname.
     * 
     * @return Returns new instance of {@link Matcher} indicating whether the file system actually contains a file
     *         denoted by this abstract pathname and the application is allowed to write to the file.
     */
    public static Matcher<File> writable() {
        return new TypeSafeMatcher<File>() {

            private File fileTested;

            @Override
            public boolean matchesSafely(final File item) {
                fileTested = item;
                return item.canWrite();
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText(" that file ");
                description.appendValue(fileTested);
                description.appendText("is writable");
            }
        };
    }

    /**
     * Create a {@link Matcher} indicating the file has specific size pathname.
     * 
     * @param size
     *            the size to test.
     * @return Returns new instance of {@link Matcher} indicating whether the file has specific size.
     */
    public static Matcher<File> sized(final long size) {
        return sized(equalTo(size));
    }

    /**
     * Create a {@link Matcher} indicating the file has specific size.
     * 
     * @param size
     *            matcher used to match the size of file.
     * @return Returns new instance of {@link Matcher} indicating whether the file has specific size.
     */
    public static Matcher<File> sized(final Matcher<Long> size) {
        return new TypeSafeMatcher<File>() {

            private File fileTested;

            private long length;

            @Override
            public boolean matchesSafely(final File item) {
                fileTested = item;
                length = item.length();
                return size.matches(length);
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText(" that file ").appendValue(fileTested);
                description.appendText(" is sized ").appendDescriptionOf(size);
                description.appendText(", not " + length);
            }
        };
    }

    /**
     * Create a {@link Matcher} indicating the file has specific name.
     * 
     * @param name
     *            matcher used to match the name of file.
     * @return Returns new instance of {@link Matcher} indicating whether the file has specific name.
     */
    public static Matcher<File> named(final Matcher<String> name) {
        return new TypeSafeMatcher<File>() {

            private File fileTested;

            @Override
            public boolean matchesSafely(final File item) {
                fileTested = item;
                return name.matches(item.getName());
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText(" that file ").appendValue(fileTested);
                description.appendText(" is named");
                description.appendDescriptionOf(name).appendText(" not ");
                description.appendValue(fileTested.getName());
            }
        };
    }

    /**
     * Create a {@link Matcher} indicating the file has specific {@link File#getCanonicalFile() canonical path}.
     * 
     * @param path
     *            matcher used to match the {@link File#getCanonicalFile() canonical path} of file.
     * @return Returns new instance of {@link Matcher} indicating whether the file has specific
     *         {@link File#getCanonicalFile() canonical path}.
     */
    public static Matcher<File> withCanonicalPath(final Matcher<String> path) {
        return new TypeSafeMatcher<File>() {

            @Override
            public boolean matchesSafely(final File item) {
                try {
                    return path.matches(item.getCanonicalPath());
                } catch (final IOException e) {
                    return false;
                }
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("with canonical path '");
                description.appendDescriptionOf(path);
                description.appendText("'");
            }
        };
    }

    /**
     * Create a {@link Matcher} indicating the file has specific {@link File#getAbsolutePath() absolute path}.
     * 
     * @param path
     *            matcher used to match the {@link File#getAbsolutePath() absolute path} of file.
     * @return Returns new instance of {@link Matcher} indicating whether the file has specific
     *         {@link File#getAbsolutePath() absolute path}.
     */
    public static Matcher<File> withAbsolutePath(final Matcher<String> path) {
        return new TypeSafeMatcher<File>() {

            @Override
            public boolean matchesSafely(final File item) {
                return path.matches(item.getAbsolutePath());
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("with absolute path '");
                description.appendDescriptionOf(path);
                description.appendText("'");
            }
        };
    }
}