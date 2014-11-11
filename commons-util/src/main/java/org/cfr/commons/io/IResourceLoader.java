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
package org.cfr.commons.io;

import javax.annotation.Nonnull;

import org.cfr.commons.util.ResourceUtils;

/**
 * Strategy interface for loading resources (e.. class path or file system resources).
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 * @see IResource
 */
public interface IResourceLoader {

    /** Pseudo URL prefix for loading from the class path: "classpath:" */
    String CLASSPATH_URL_PREFIX = ResourceUtils.CLASSPATH_URL_PREFIX;

    /**
     * Get a Resource handle for the specified resource. The handle should always be a reusable resource descriptor,
     * allowing for multiple {@link Resource#getInputStream()} calls.
     * <p>
     * <ul>
     * <li>Must support fully qualified URLs, e.g. "file:C:/test.dat".
     * <li>Must support classpath pseudo-URLs, e.g. "classpath:test.dat".
     * <li>Should support relative file paths, e.g. "WEB-INF/test.dat".
     * </ul>
     * <p>
     * Note that a Resource handle does not imply an existing resource; you need to invoke {@link Resource#exists} to
     * check for existence.
     *
     * @param location
     *            the resource location. Can not be {@code null} or empty.
     * @return a corresponding Resource handle
     * @throws IllegalArgumentException
     *             Throws {@code location} if {@code null}.
     * @see #CLASSPATH_URL_PREFIX
     * @see IResource#exists
     * @see IResource#getInputStream
     */
    @Nonnull
    IResource getResource(@Nonnull String location);

    /**
     * Expose the ClassLoader used by this ResourceLoader.
     * <p>
     * Clients which need to access the ClassLoader directly can do so in a uniform manner with the IResourceLoader,
     * rather than relying on the thread context ClassLoader.
     *
     * @return the ClassLoader (never <code>null</code>)
     */
    @Nonnull
    ClassLoader getClassLoader();

}