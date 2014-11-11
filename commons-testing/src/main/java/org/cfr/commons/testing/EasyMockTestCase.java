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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IArgumentMatcher;
import org.easymock.IExpectationSetters;
import org.easymock.IMockBuilder;
import org.easymock.internal.LastControl;
import org.easymock.internal.MocksControl;
import org.easymock.internal.matchers.Captures;
import org.junit.Before;

/**
 * This class allow to migrate form JUnit 3.x syntax to JUnit 4.</p> it is also Mock facility.
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
public abstract class EasyMockTestCase extends TestCase {

    /** Tracks all EasyMock objects created for a test. */
    private final List<Object> mocks = new ArrayList<Object>();

    /**
     * Allows to clean mock context before each test methods called (default is {@code true}).
     */
    private final boolean resetBefore;

    /**
     * Creates new instance with default configuration. Mock context cleaned before each test methods called.
     */
    public EasyMockTestCase() {
        this.resetBefore = true;
    }

    /**
     * Creates new instance.
     *
     * @param resetBefore
     *            Allows to clean mock context before each test methods called.
     */
    public EasyMockTestCase(@Nonnull final boolean resetBefore) {
        this.resetBefore = resetBefore;
    }

    /**
     * Gets the file system path representation of this test class.
     *
     * @return Returns {@code String} representing the file system location path of this test class.
     * @deprecated use instead {@link #getPackagePath()} method
     */
    @Deprecated
    @Nonnull
    public final String getPackageName() {
        return this.getPackagePath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Before
    public void setUp() throws Exception {
        if (this.resetBefore) {
            LastControl.pullMatchers();
            reset();
            mocks.clear();
        }
    }

    /**
     * Switches order checking of the given mock object (more exactly: the control of the mock object) the on and off.
     * For details, see the EasyMock documentation.
     *
     * @param mock
     *            the mock object.
     * @param state
     *            <code>true</code> switches order checking on, <code>false</code> switches it off.
     */
    public static void checkOrder(@Nonnull final Object mock, @Nonnull final boolean state) {
        getControl(mock).checkOrder(state);
    }

    /**
     * Gets the {@link MocksControl} associated to mock object
     *
     * @param mock
     *            the mock object.
     * @return Returns {@link MocksControl} associated to mock object
     */
    @Nonnull
    private static MocksControl getControl(@Nonnull final Object mock) {
        return MocksControl.getControl(mock);
    }

    /**
     * Reports an argument matcher. This method is needed to define own argument matchers. For details, see the EasyMock
     * documentation.
     *
     * @param matcher
     *            a argument matcher.
     */
    public static void reportMatcher(@Nonnull final IArgumentMatcher matcher) {
        LastControl.reportMatcher(matcher);
    }

    /**
     * Gets the arguments of the current mock method call, if inside an {@code IAnswer} callback - be careful here,
     * reordering parameters of method changes the semantics of your tests.
     *
     * @return Returns the arguments of the current mock method call.
     */
    @CheckReturnValue
    public static @Nonnull Object[] getCurrentArguments() {
        return EasyMock.getCurrentArguments();
    }

    /**
     * Returns the expectation setter for the last expected invocation in the current thread.
     *
     * @param value
     *            the parameter is used to transport the type to the ExpectationSetter. It allows writing the expected
     *            call as argument, i.e. expect(mock.getName()).andReturn("John Doe").
     * @param <T>
     *            type returned by the expected method
     * @return Returns the expectation setter.
     */
    protected @Nonnull <T> IExpectationSetters<T> expect(@Nonnull final T value) {
        return EasyMock.expect(value);
    }

    /**
     * Returns the expectation setter for the last expected invocation in the current thread. This method is used for
     * expected invocations on void methods.
     *
     * @return the expectation setter.
     * @param <T>
     *            type returned by the expected method.
     */
    protected @Nonnull <T> IExpectationSetters<T> expectLastCall() {
        return EasyMock.expectLastCall();
    }

    /**
     * Expects any Object argument. For details, see the EasyMock documentation. This matcher (and
     * {@link #anyObject(Class)}) can be used in these three ways:
     * <ul>
     * <li><code>(T)EasyMock.anyObject() // explicit cast</code></li>
     * <li>
     * <code>EasyMock.&lt;T&gt; anyObject() // fixing the returned generic</code></li>
     * <li>
     * <code>EasyMock.anyObject(T.class) // pass the returned type in parameter</code></li>
     * </ul>
     *
     * @param <T>
     *            type of the method argument to match
     * @return <code>null</code>.
     */
    public @Nullable <T> T anyObject() {
        return EasyMock.<T> anyObject();
    }

    /**
     * Expects any Object argument. For details, see the EasyMock documentation. To work well with generics, this
     * matcher can be used in three different ways. See {@link #anyObject()}.
     *
     * @param cl
     *            the class of the argument to match
     * @param <T>
     *            type of the method argument to match
     * @return <code>null</code>.
     */
    public @Nullable <T> T anyObject(@Nonnull final Class<T> cl) {
        return EasyMock.anyObject(cl);
    }

    /**
     * Creates a strict mock object for the given class, adds it to the internal list of all mocks, and returns it.
     *
     * @param clazz
     *            Class to be mocked.
     * @param <T>
     *            type of the method argument to match
     * @return A mock instance of the given type.
     **/

    protected @Nullable <T> T mock(@Nonnull final Class<T> clazz) {
        return mock(clazz, false);
    }

    /**
     * Creates a strict or nice mock object for the given class, adds it to the internal list of all mocks, and returns
     * it.
     *
     * @param clazz
     *            Class to be mocked.
     * @param strict
     *            whether or not to make a strict mock
     * @param <T>
     *            type of the method argument to match
     * @return A mock instance of the given type.
     **/
    protected @Nonnull <T> T mock(@Nonnull final Class<T> clazz, @Nonnull final boolean strict) {
        T m = strict ? EasyMock.createMock(clazz) : EasyMock.createNiceMock(clazz);
        mocks.add(m);
        return m;
    }

    /**
     * Creates a nice mock object for the given class, adds it to the internal list of all mocks, and returns it.
     *
     * @param clazz
     *            Class to be mocked.
     * @param methods
     *            methods to be mocked in the testing class.
     * @param <T>
     *            type of the method argument to match
     * @return A mock instance of the given type.
     **/
    protected @Nonnull <T> T mock(@Nonnull final Class<T> clazz, @Nonnull final Method... methods) {
        return mock(clazz, methods, false);
    }

    /**
     * Creates a strict mock object for the given class, adds it to the internal list of all mocks, and returns it.
     *
     * @param clazz
     *            Class to be mocked.
     * @param methods
     *            methods to be mocked in the testing class.
     * @param strict
     *            whether or not to make a strict mock.
     * @param <T>
     *            type of the method argument to match
     * @return A mock instance of the given type.
     **/
    protected @Nonnull <T> T mock(@Nonnull final Class<T> clazz, @Nonnull final Method[] methods,
                                  @Nonnull final boolean strict) {
        IMockBuilder<T> builder = EasyMock.createMockBuilder(clazz).addMockedMethods(methods);
        T m = strict ? builder.createMock() : builder.createNiceMock();
        mocks.add(m);

        return m;
    }

    /**
     * Sets each mock to replay mode in the order they were created. Call this after setting all of the mock
     * expectations for a test.
     */
    protected void replay() {
        EasyMock.replay(mocks.toArray());
    }

    /**
     * Switches the given mock objects (more exactly: the controls of the mock objects) to replay mode. For details, see
     * the EasyMock documentation.
     *
     * @param mock
     *            the mock objects.
     */
    protected void replay(@Nonnull final Object mock) {
        EasyMock.replay(mock);
    }

    /**
     * Verifies each mock in the order they were created. Call this at the end of each test to verify the expectations
     * were satisfied.
     */
    protected void verify() {
        EasyMock.verify(mocks.toArray());
    }

    /**
     * Verifies the given mock objects (more exactly: the controls of the mock objects).
     *
     * @param mock
     *            the mock objects.
     */
    protected void verify(@Nonnull final Object mock) {
        EasyMock.verify(mock);
    }

    /**
     * Resets all of the mocks.
     */
    protected void reset() {
        EasyMock.reset(mocks.toArray());
    }

    /**
     * Resets the given mock objects (more exactly: the controls of the mock objects). For details, see the EasyMock
     * documentation.
     *
     * @param mock
     *            the mock objects.
     */
    protected void reset(@Nonnull final Object mock) {
        EasyMock.reset(mock);
    }

    /**
     * Expect any object but captures it for later use.
     *
     * @param <T>
     *            Type of the captured object
     * @param captured
     *            Where the parameter is captured
     * @return <code>null</code>
     */
    public static @Nullable <T> T capture(@Nonnull final Capture<T> captured) {
        reportMatcher(new Captures<T>(captured));
        return null;
    }

    /**
     * Expects an Object that is equal to the given value.
     *
     * @param <T>
     *            type of the method argument to match
     * @param value
     *            the given value.
     * @return <code>null</code>.
     */
    @SuppressWarnings("PMD.ShortMethodName")
    public @Nullable <T> T eq(@Nonnull final T value) {
        return EasyMock.eq(value);
    }
}
