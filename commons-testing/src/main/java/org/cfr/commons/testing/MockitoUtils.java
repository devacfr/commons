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
package org.cfr.commons.testing;

import java.util.List;
import java.util.Map;

import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.common.collect.Lists;

/**
 * 
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0.2
 */
public class MockitoUtils {

    private MockitoUtils() {
        throw new UnsupportedOperationException("Attempt to instantiate utility class");
    }

    /**
     * 
     * @param type
     * @param captor
     * @return
     */
    public static <T> T getFirstCaptorOfType(final Class<T> type, final ArgumentCaptor<T> captor) {
        for (final Object value : captor.getAllValues()) {
            if (value.getClass().isAssignableFrom(type)) {
                return type.cast(value);
            }
        }
        return null;
    }

    /**
     * 
     * @param type
     * @param captor
     * @return
     */
    public static <T> List<T> getCaptorsOfType(final Class<T> type, final ArgumentCaptor<T> captor) {
        final List<T> values = Lists.newArrayList();
        for (final Object value : captor.getAllValues()) {
            if (value.getClass().isAssignableFrom(type)) {
                values.add(type.cast(value));
            }
        }
        return values;
    }

    /**
     * 
     * @param map
     * @return
     */
    public static <K, V> Answer<Void> putsValueInMap(final Map<K, V> map) {
        return putsValueInMap(map, 0, 1);
    }

    /**
     * 
     * @param map
     * @param keyIndex
     * @param valueIndex
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Answer<Void> putsValueInMap(final Map<K, V> map, final int keyIndex, final int valueIndex) {
        return new Answer<Void>() {

            @Override
            public Void answer(final InvocationOnMock invocation) throws Throwable {
                map.put((K) invocation.getArguments()[keyIndex], (V) invocation.getArguments()[valueIndex]);
                return null;
            }
        };
    }

    /**
     * 
     * @param map
     * @return
     */
    public static <K, V> Answer<Void> removesValueInMap(final Map<K, V> map) {
        return removesValueInMap(map, 0);
    }

    /**
     * 
     * @param map
     * @param keyIndex
     * @return
     */
    public static <K, V> Answer<Void> removesValueInMap(final Map<K, V> map, final int keyIndex) {
        return new Answer<Void>() {

            @Override
            public Void answer(final InvocationOnMock invocation) throws Throwable {
                map.remove(invocation.getArguments()[keyIndex]);
                return null;
            }
        };
    }

    /**
     * 
     * @param index
     * @return
     */
    public static <T> Answer<T> returnArg(final int index) {
        return new Answer<T>() {

            @SuppressWarnings("unchecked")
            @Override
            public T answer(final InvocationOnMock invocationOnMock) throws Throwable {
                return (T) invocationOnMock.getArguments()[index];
            }
        };
    }

    /**
     * @param <T>
     *            the return type of the Answer
     * @return an Answer that returns the first argument
     */
    public static <T> Answer<T> returnFirst() {
        return returnArg(0);
    }

    /**
     * 
     * @param mockType
     * @return
     */
    public static SelfAnswer returnSelf(final Class<?> mockType) {
        return new SelfAnswer(mockType);
    }

    /**
     * 
     * @param mockType
     * @param defaultAnswer
     * @return
     */
    public static SelfAnswer returnSelf(final Class<?> mockType, final Answer<Object> defaultAnswer) {
        return new SelfAnswer(mockType, defaultAnswer);
    }

    /**
     * 
     * @param map
     * @return
     */
    public static <K, V> Answer<V> returnsValueFromMap(final Map<K, V> map) {
        return returnsValueFromMap(map, 0);
    }

    /**
     * 
     * @param map
     * @param keyIndex
     * @return
     */
    @SuppressWarnings("SuspiciousMethodCalls")
    public static <K, V> Answer<V> returnsValueFromMap(final Map<K, V> map, final int keyIndex) {
        return new Answer<V>() {

            @Override
            public V answer(final InvocationOnMock invocation) throws Throwable {
                return map.get(invocation.getArguments()[keyIndex]);
            }
        };
    }

}
