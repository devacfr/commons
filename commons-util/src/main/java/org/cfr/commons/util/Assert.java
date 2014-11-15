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
package org.cfr.commons.util;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.cfr.commons.util.collection.CollectionUtil;

import com.google.common.base.Strings;

/**
 * Assertion utility class that assists in validating arguments. Useful for identifying programmer errors early and
 * clearly at runtime.
 * 
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
public class Assert {

    /**
     * Singleton restriction instantiation of the class
     */
    private Assert() {
    }

    /**
     * Assert a boolean expression, throwing <code>IllegalArgumentException</code> if the test result is
     * <code>false</code>.
     *
     * <pre class="code">
     * Assert.isTrue(i &gt; 0, &quot;The value must be greater than zero&quot;);
     * </pre>
     *
     * @param expression
     *            a boolean expression
     * @param message
     *            the exception message to use if the assertion fails
     * @throws IllegalArgumentException
     *             if expression is <code>false</code>
     */
    public static void isTrue(final boolean expression, final String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert a boolean expression, throwing <code>IllegalArgumentException</code> if the test result is
     * <code>false</code>.
     *
     * <pre class="code">
     * Assert.isTrue(i &gt; 0);
     * </pre>
     *
     * @param expression
     *            a boolean expression
     * @throws IllegalArgumentException
     *             if expression is <code>false</code>
     */
    public static void isTrue(final boolean expression) {
        isTrue(expression, "[Assertion failed] - this expression must be true");
    }

    /**
     * Assert that an object is <code>null</code> .
     *
     * <pre class="code">
     * Assert.isNull(value, &quot;The value must be null&quot;);
     * </pre>
     *
     * @param object
     *            the object to check
     * @param message
     *            the exception message to use if the assertion fails
     * @throws IllegalArgumentException
     *             if the object is not <code>null</code>
     */
    public static <T> T isNull(final T object, final String message) {
        if (object != null) {
            throw new IllegalArgumentException(message);
        }
        return object;
    }

    /**
     * Assert that an object is <code>null</code> .
     *
     * <pre class="code">
     * Assert.isNull(value);
     * </pre>
     *
     * @param object
     *            the object to check
     * @throws IllegalArgumentException
     *             if the object is not <code>null</code>
     */
    public static <T> T isNull(final T object) {
        return isNull(object, "[Assertion failed] - the object argument must be null");
    }

    /**
     * @param object
     * @param parameterName
     * @return
     */
    public static <T> T checkNotNull(@Nullable final T object, @Nonnull final String parameterName) {
        if (object == null) {
            throw new IllegalArgumentException(required(parameterName));
        }
        return object;
    }

    /**
     * Assert that an object is not <code>null</code> .
     *
     * <pre class="code">
     * Assert.notNull(clazz, &quot;The class must not be null&quot;);
     * </pre>
     *
     * @param object
     *            the object to check
     * @param message
     *            the exception message to use if the assertion fails
     * @throws IllegalArgumentException
     *             if the object is <code>null</code>
     */
    public static <T> T notNull(final T object, final String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
        return object;
    }

    /**
     * Assert that an object is not <code>null</code> .
     *
     * <pre class="code">
     * Assert.notNull(clazz);
     * </pre>
     *
     * @param object
     *            the object to check
     * @throws IllegalArgumentException
     *             if the object is <code>null</code>
     */
    public static <T> T notNull(final T object) {
        return notNull(object, "[Assertion failed] - this argument is required; it must not be null");
    }

    /**
     * Assert that the given String is not empty; that is, it must not be <code>null</code> and not the empty String.
     *
     * <pre class="code">
     * Assert.hasLength(name, &quot;Name must not be empty&quot;);
     * </pre>
     *
     * @param text
     *            the String to check
     * @param message
     *            the exception message to use if the assertion fails
     * @see StringUtils#hasLength
     */
    public static String hasLength(final String text, final String message) {
        if (Strings.isNullOrEmpty(text)) {
            throw new IllegalArgumentException(message);
        }
        return text;
    }

    /**
     * Assert that the given String is not empty; that is, it must not be <code>null</code> and not the empty String.
     *
     * <pre class="code">
     * Assert.hasLength(name);
     * </pre>
     *
     * @param text
     *            the String to check
     * @see StringUtils#hasLength
     */
    public static String hasLength(final String text) {
        return hasLength(text,
            "[Assertion failed] - this String argument must have length; it must not be null or empty");
    }

    /**
     * @param text
     * @param parameterName
     * @return
     */
    public static String checkHasText(@Nullable final String text, @Nonnull final String parameterName) {
        if (isBlank(text)) {
            throw new IllegalArgumentException(required(parameterName));
        }
        return text;
    }

    /**
     * Assert that the given String has valid text content; that is, it must not be <code>null</code> and must contain
     * at least one non-whitespace character.
     *
     * <pre class="code">
     * Assert.hasText(name, &quot;'name' must not be empty&quot;);
     * </pre>
     *
     * @param text
     *            the String to check
     * @param message
     *            the exception message to use if the assertion fails
     * @see StringUtils#hasText
     */
    public static String hasText(final String text, final String message) {
        if (isBlank(text)) {
            throw new IllegalArgumentException(message);
        }
        return text;
    }

    /**
     * Assert that the given String has valid text content; that is, it must not be <code>null</code> and must contain
     * at least one non-whitespace character.
     *
     * <pre class="code">
     * Assert.hasText(name, &quot;'name' must not be empty&quot;);
     * </pre>
     *
     * @param text
     *            the String to check
     * @see StringUtils#hasText
     */
    public static String hasText(final String text) {
        return hasText(text,
            "[Assertion failed] - this String argument must have text; it must not be null, empty, or blank");
    }

    /**
     * Assert that the given text does not contain the given substring.
     *
     * <pre class="code">
     * Assert.doesNotContain(name, &quot;rod&quot;, &quot;Name must not contain 'rod'&quot;);
     * </pre>
     *
     * @param textToSearch
     *            the text to search
     * @param substring
     *            the substring to find within the text
     * @param message
     *            the exception message to use if the assertion fails
     */
    public static void doesNotContain(final String textToSearch, final String substring, final String message) {
        if (!Strings.isNullOrEmpty(textToSearch) && !Strings.isNullOrEmpty(substring)
                && textToSearch.indexOf(substring) != -1) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that the given text does not contain the given substring.
     *
     * <pre class="code">
     * Assert.doesNotContain(name, &quot;rod&quot;);
     * </pre>
     *
     * @param textToSearch
     *            the text to search
     * @param substring
     *            the substring to find within the text
     */
    public static void doesNotContain(final String textToSearch, final String substring) {
        doesNotContain(textToSearch,
            substring,
            "[Assertion failed] - this String argument must not contain the substring [" + substring + "]");
    }

    /**
     * Assert that an array has elements; that is, it must not be <code>null</code> and must have at least one element.
     *
     * <pre class="code">
     * Assert.notEmpty(array, &quot;The array must have elements&quot;);
     * </pre>
     *
     * @param array
     *            the array to check
     * @param message
     *            the exception message to use if the assertion fails
     * @throws IllegalArgumentException
     *             if the object array is <code>null</code> or has no elements
     */
    public static <T> T[] notEmpty(final T[] array, final String message) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException(message);
        }
        return array;
    }

    /**
     * Assert that an array has elements; that is, it must not be <code>null</code> and must have at least one element.
     *
     * <pre class="code">
     * Assert.notEmpty(array);
     * </pre>
     *
     * @param array
     *            the array to check
     * @throws IllegalArgumentException
     *             if the object array is <code>null</code> or has no elements
     */
    public static <T> T[] notEmpty(final T[] array) {
        return notEmpty(array, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
    }

    /**
     * Assert that an array has no null elements. Note: Does not complain if the array is empty!
     *
     * <pre class="code">
     * Assert.noNullElements(array, &quot;The array must have non-null elements&quot;);
     * </pre>
     *
     * @param array
     *            the array to check
     * @param message
     *            the exception message to use if the assertion fails
     * @throws IllegalArgumentException
     *             if the object array contains a <code>null</code> element
     */
    public static <T> T[] noNullElements(final T[] array, final String message) {
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                if (array[i] == null) {
                    throw new IllegalArgumentException(message);
                }
            }
        }
        return array;
    }

    /**
     * Assert that an array has no null elements. Note: Does not complain if the array is empty!
     *
     * <pre class="code">
     * Assert.noNullElements(array);
     * </pre>
     *
     * @param array
     *            the array to check
     * @throws IllegalArgumentException
     *             if the object array contains a <code>null</code> element
     */
    public static <T> T[] noNullElements(final T[] array) {
        return noNullElements(array, "[Assertion failed] - this array must not contain any null elements");
    }

    /**
     * Assert that a collection has elements; that is, it must not be <code>null</code> and must have at least one
     * element.
     *
     * <pre class="code">
     * Assert.notEmpty(collection, &quot;Collection must have elements&quot;);
     * </pre>
     *
     * @param collection
     *            the collection to check
     * @param message
     *            the exception message to use if the assertion fails
     * @throws IllegalArgumentException
     *             if the collection is <code>null</code> or has no elements
     */
    public static <T> Collection<T> notEmpty(final Collection<T> collection, final String message) {
        if (CollectionUtil.isEmpty(collection)) {
            throw new IllegalArgumentException(message);
        }
        return collection;
    }

    /**
     * Assert that a collection has elements; that is, it must not be <code>null</code> and must have at least one
     * element.
     *
     * <pre class="code">
     * Assert.notEmpty(collection, &quot;Collection must have elements&quot;);
     * </pre>
     *
     * @param collection
     *            the collection to check
     * @throws IllegalArgumentException
     *             if the collection is <code>null</code> or has no elements
     */
    public static <T> Collection<T> notEmpty(final Collection<T> collection) {
        return notEmpty(collection,
            "[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
    }

    /**
     * Assert that a Map has entries; that is, it must not be <code>null</code> and must have at least one entry.
     *
     * <pre class="code">
     * Assert.notEmpty(map, &quot;Map must have entries&quot;);
     * </pre>
     *
     * @param map
     *            the map to check
     * @param message
     *            the exception message to use if the assertion fails
     * @throws IllegalArgumentException
     *             if the map is <code>null</code> or has no entries
     */
    public static <T, R> Map<T, R> notEmpty(final Map<T, R> map, final String message) {
        if (map == null || map.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return map;
    }

    /**
     * Assert that a Map has entries; that is, it must not be <code>null</code> and must have at least one entry.
     *
     * <pre class="code">
     * Assert.notEmpty(map);
     * </pre>
     *
     * @param map
     *            the map to check
     * @throws IllegalArgumentException
     *             if the map is <code>null</code> or has no entries
     */
    public static <T, R> Map<T, R> notEmpty(final Map<T, R> map) {
        return notEmpty(map, "[Assertion failed] - this map must not be empty; it must contain at least one entry");
    }

    /**
     * Assert that the provided object is an instance of the provided class.
     *
     * <pre class="code">
     * Assert.instanceOf(Foo.class, foo);
     * </pre>
     *
     * @param clazz
     *            the required class
     * @param obj
     *            the object to check
     * @throws IllegalArgumentException
     *             if the object is not an instance of clazz
     * @see Class#isInstance
     */
    public static void isInstanceOf(final Class<?> clazz, final Object obj) {
        isInstanceOf(clazz, obj, "");
    }

    /**
     * Assert that the provided object is an instance of the provided class.
     *
     * <pre class="code">
     * Assert.instanceOf(Foo.class, foo);
     * </pre>
     *
     * @param type
     *            the type to check against
     * @param obj
     *            the object to check
     * @param message
     *            a message which will be prepended to the message produced by the function itself, and which may be
     *            used to provide context. It should normally end in a ": " or ". " so that the function generate
     *            message looks ok when prepended to it.
     * @throws IllegalArgumentException
     *             if the object is not an instance of clazz
     * @see Class#isInstance
     */
    public static void isInstanceOf(final Class<?> type, final Object obj, final String message) {
        notNull(type, "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            throw new IllegalArgumentException(message + "Object of class ["
                    + (obj != null ? obj.getClass().getName() : "null") + "] must be an instance of " + type);
        }
    }

    /**
     * Assert that <code>superType.isAssignableFrom(subType)</code> is <code>true</code>.
     *
     * <pre class="code">
     * Assert.isAssignable(Number.class, myClass);
     * </pre>
     *
     * @param superType
     *            the super type to check
     * @param subType
     *            the sub type to check
     * @throws IllegalArgumentException
     *             if the classes are not assignable
     */
    public static void isAssignable(final Class<?> superType, final Class<?> subType) {
        isAssignable(superType, subType, "");
    }

    /**
     * Assert that <code>superType.isAssignableFrom(subType)</code> is <code>true</code>.
     *
     * <pre class="code">
     * Assert.isAssignable(Number.class, myClass);
     * </pre>
     *
     * @param superType
     *            the super type to check against
     * @param subType
     *            the sub type to check
     * @param message
     *            a message which will be prepended to the message produced by the function itself, and which may be
     *            used to provide context. It should normally end in a ": " or ". " so that the function generate
     *            message looks ok when prepended to it.
     * @throws IllegalArgumentException
     *             if the classes are not assignable
     */
    public static void isAssignable(final Class<?> superType, final Class<?> subType, final String message) {
        notNull(superType, "Type to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            throw new IllegalArgumentException(message + subType + " is not assignable to " + superType);
        }
    }

    /**
     * Assert a boolean expression, throwing <code>IllegalStateException</code> if the test result is <code>false</code>
     * . Call isTrue if you wish to throw IllegalArgumentException on an assertion failure.
     *
     * <pre class="code">
     * Assert.state(id == null, &quot;The id property must not already be initialized&quot;);
     * </pre>
     *
     * @param expression
     *            a boolean expression
     * @param message
     *            the exception message to use if the assertion fails
     * @throws IllegalStateException
     *             if expression is <code>false</code>
     */
    public static void state(final boolean expression, final String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
    }

    /**
     * Assert a boolean expression, throwing {@link IllegalStateException} if the test result is <code>false</code>.
     * <p>
     * Call {@link #isTrue(boolean)} if you wish to throw {@link IllegalArgumentException} on an assertion failure.
     *
     * <pre class="code">
     * Assert.state(id == null);
     * </pre>
     *
     * @param expression
     *            a boolean expression
     * @throws IllegalStateException
     *             if the supplied expression is <code>false</code>
     */
    public static void state(final boolean expression) {
        state(expression, "[Assertion failed] - this state invariant must be true");
    }

    /**
     * Asserts that two objects are equal. If they are not an @{link IllegalArgumentException} is thrown.
     */
    @SuppressWarnings("PMD.SuspiciousEqualsMethodName")
    public static <T> T equals(final String name, final T expected, final T got) throws IllegalArgumentException {
        if (!expected.equals(got)) {
            throw new IllegalArgumentException(name + ". Expected:" + expected + " but got: " + got);
        }
        return got;
    }

    private static String required(final String parameterName) {
        return format("%s parameter is required", parameterName);
    }

    @Nonnull
    private static String format(@Nullable String template, @Nullable final Object... args) {
        template = String.valueOf(template); // null -> "null"

        // start substituting the arguments into the '%s' placeholders
        StringBuilder builder = new StringBuilder(template.length() + 16 * args.length);
        int templateStart = 0;
        int i = 0;
        while (i < args.length) {
            int placeholderStart = template.indexOf("%s", templateStart);
            if (placeholderStart == -1) {
                break;
            }
            builder.append(template.substring(templateStart, placeholderStart));
            builder.append(args[i++]);
            templateStart = placeholderStart + 2;
        }
        builder.append(template.substring(templateStart));

        // if we run out of placeholders, append the extra args in square braces
        if (i < args.length) {
            builder.append(" [");
            builder.append(args[i++]);
            while (i < args.length) {
                builder.append(", ");
                builder.append(args[i++]);
            }
            builder.append(']');
        }

        return builder.toString();
    }

    /**
     * <p>
     * Checks if a String is whitespace, empty ("") or null.
     * </p>
     *
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param str
     *            the String to check, may be null
     * @return <code>true</code> if the String is null, empty or whitespace
     * @since 2.0
     */
    private static boolean isBlank(final String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

}