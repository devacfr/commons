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

import java.net.MalformedURLException;
import java.net.URL;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.cfr.commons.io.internal.ClassPathResource;
import org.cfr.commons.io.internal.UrlResource;
import org.cfr.commons.util.Assert;
import org.cfr.commons.util.ClassUtils;
import org.cfr.commons.util.Paths;

/**
 * Default implementation of {@link IResourceLoader} interface.
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
public class DefaultResourceLoader implements IResourceLoader {

    /**
     * associated class loader.
     */
    private @Nullable ClassLoader classLoader;

    /**
     * Create a new DefaultResourceLoader.
     * <p>
     * ClassLoader access will happen using the thread context class loader at the time of this ResourceLoader's
     * initialization.
     * </p>
     *
     * @see java.lang.Thread#getContextClassLoader()
     */
    public DefaultResourceLoader() {
        this.classLoader = ClassUtils.getDefaultClassLoader();
    }

    /**
     * Create a new DefaultResourceLoader.
     *
     * @param classLoader
     *            the ClassLoader to load class path resources with, or {@code null} for using the thread context class
     *            loader at the time of actual resource access
     */
    public DefaultResourceLoader(@Nullable final ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    /**
     * Specify the ClassLoader to load class path resources with, or <code>null</code> for using the thread context
     * class loader at the time of actual resource access.
     * <p>
     * The default is that ClassLoader access will happen using the thread context class loader at the time of this
     * ResourceLoader's initialization.
     *
     * @param classLoader
     *            the ClassLoader to load class path resources with, or {@code null} for using the thread context class
     *            loader at the time of actual resource access
     */
    public void setClassLoader(@Nullable final ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns the ClassLoader to load class path resources with.
     * </p>
     * <p>
     * Will get passed to ClassPathResource's constructor for all ClassPathResource objects created by this resource
     * loader.
     * </p>
     *
     * @see ClassPathResource
     */
    @Override
    public @Nonnull ClassLoader getClassLoader() {
        return (this.classLoader != null ? this.classLoader : ClassUtils.getDefaultClassLoader());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nonnull IResource getResource(@Nonnull final String location) {
        Assert.checkNotNull(location, "Location");
        if (location.startsWith(CLASSPATH_URL_PREFIX)) {
            return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()), getClassLoader());
        } else {
            try {
                // Try to parse the location as a URL...
                URL url = new URL(location);
                return new UrlResource(url);
            } catch (MalformedURLException ex) {
                // No URL -> resolve as resource path.
                return getResourceByPath(location);
            }
        }
    }

    /**
     * Returns a Resource handle for the resource at the given path.
     * <p>
     * The default implementation supports class path locations. This should be appropriate for standalone
     * implementations but can be overridden, e.g. for implementations targeted at a Servlet container.
     *
     * @param path
     *            the path to the resource (nver {@code null})
     * @return the corresponding Resource handle
     * @see ClassPathResource
     */
    protected IResource getResourceByPath(@Nonnull final String path) {
        return new ClassPathContextResource(path, getClassLoader());
    }

    /**
     * ClassPathResource that explicitly expresses a context-relative path.
     */
    private static class ClassPathContextResource extends ClassPathResource {

        public ClassPathContextResource(@Nonnull final String path, @Nullable final ClassLoader classLoader) {
            super(path, classLoader);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @Nonnull IResource createRelative(@Nonnull final String relativePath) {
            String pathToUse = Paths.applyRelativePath(getPath(), relativePath);
            return new ClassPathContextResource(pathToUse, getClassLoader());
        }
    }

}