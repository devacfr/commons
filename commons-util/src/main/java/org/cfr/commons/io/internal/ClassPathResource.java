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
package org.cfr.commons.io.internal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.cfr.commons.io.IResource;
import org.cfr.commons.util.Assert;
import org.cfr.commons.util.ClassUtils;
import org.cfr.commons.util.Paths;

import com.google.common.base.Objects;

/**
 * {@link IResource} implementation for class path resources. Uses either a given ClassLoader or a given Class for
 * loading resources.
 * <p>
 * Supports resolution as {@code java.io.File} if the class path resource resides in the file system, but not for
 * resources in a JAR. Always supports resolution as URL.
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
public class ClassPathResource extends AbstractFileResolvingResource {

    /**
     *
     */
    private final @Nonnull String path;

    /**
     *
     */
    private @Nullable ClassLoader classLoader;

    /**
     *
     */
    private @Nullable Class<?> clazz;

    /**
     * Create a new ClassPathResource for ClassLoader usage. A leading slash will be removed, as the ClassLoader
     * resource access methods will not accept it.
     * <p>
     * The thread context class loader will be used for loading the resource.
     * </p>
     *
     * @param path
     *            the absolute path within the class path (cannot be {@code null}).
     * @see java.lang.ClassLoader#getResourceAsStream(String)
     * @throws IllegalArgumentException
     *             Throws {@code path} if {@code null}.
     */
    public ClassPathResource(@Nonnull final String path) {
        this(path, (ClassLoader) null);
    }

    /**
     * Create a new ClassPathResource for ClassLoader usage. A leading slash will be removed, as the ClassLoader
     * resource access methods will not accept it.
     *
     * @param path
     *            the absolute path within the classpath
     * @param classLoader
     *            the class loader to load the resource with, or <code>null</code> for the thread context class loader
     * @throws IllegalArgumentException
     *             Throws {@code path} if {@code null}.
     * @see java.lang.ClassLoader#getResourceAsStream(String)
     */
    public ClassPathResource(@Nonnull final String path, @Nullable final ClassLoader classLoader) {
        Assert.checkNotNull(path, "path");
        String pathToUse = Paths.cleanPath(path);
        if (pathToUse.startsWith("/")) {
            pathToUse = pathToUse.substring(1);
        }
        this.path = pathToUse;
        this.classLoader = (classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader());
    }

    /**
     * Create a new ClassPathResource for Class usage. The path can be relative to the given class, or absolute within
     * the classpath via a leading slash.
     *
     * @param path
     *            relative or absolute path within the class path
     * @param clazz
     *            the class to load resources with
     * @throws IllegalArgumentException
     *             Throws {@code path} if {@code null}.
     * @see java.lang.Class#getResourceAsStream
     */
    public ClassPathResource(@Nonnull final String path, @Nullable final Class<?> clazz) {
        Assert.checkNotNull(path, "path");
        this.path = Paths.cleanPath(path);
        this.clazz = clazz;
    }

    /**
     * Create a new ClassPathResource with optional ClassLoader and Class. Only for internal usage.
     *
     * @param path
     *            relative or absolute path within the classpath
     * @param classLoader
     *            the class loader to load the resource with, if any
     * @param clazz
     *            the class to load resources with, if any
     */
    protected ClassPathResource(@Nonnull final String path, @Nullable final ClassLoader classLoader,
            @Nullable final Class<?> clazz) {
        this.path = Paths.cleanPath(path);
        this.classLoader = classLoader;
        this.clazz = clazz;
    }

    /**
     * @return Returns the path for this resource (as resource path within the class path).
     */
    public final @Nonnull String getPath() {
        return this.path;
    }

    /**
     * @return Returns the ClassLoader that this resource will be obtained from.
     */
    @SuppressWarnings("PMD")
    public final @Nonnull ClassLoader getClassLoader() {
        if (this.classLoader != null) {
            return this.classLoader;
        }
        if (this.clazz != null) {
            return this.clazz.getClassLoader();
        }
        throw new RuntimeException("You are need set a classloader or the class to load resources with ");
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation checks for the resolution of a resource URL.
     * </p>
     *
     * @see java.lang.ClassLoader#getResource(String)
     * @see java.lang.Class#getResource(String)
     */
    @Override
    public boolean exists() {
        URL url;
        if (this.clazz != null) {
            url = this.clazz.getResource(this.path);
        } else {
            url = this.classLoader.getResource(this.path);
        }
        return url != null;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation opens an InputStream for the given class path resource.
     * </p>
     *
     * @see java.lang.ClassLoader#getResourceAsStream(String)
     * @see java.lang.Class#getResourceAsStream(String)
     */
    @Override
    public InputStream getInputStream() throws IOException {
        InputStream is;
        if (this.clazz != null) {
            is = this.clazz.getResourceAsStream(this.path);
        } else {
            is = this.classLoader.getResourceAsStream(this.path);
        }
        if (is == null) {
            throw new FileNotFoundException(getDescription() + " cannot be opened because it does not exist");
        }
        return is;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation returns a URL for the underlying class path resource.
     * </p>
     *
     * @see java.lang.ClassLoader#getResource(String)
     * @see java.lang.Class#getResource(String)
     */
    @Override
    public URL getURL() throws IOException {
        URL url;
        if (this.clazz != null) {
            url = this.clazz.getResource(this.path);
        } else {
            url = this.classLoader.getResource(this.path);
        }
        if (url == null) {
            throw new FileNotFoundException(getDescription() + " cannot be resolved to URL because it does not exist");
        }
        return url;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation creates a ClassPathResource, applying the given path relative to the path of the underlying
     * resource of this descriptor.
     * </p>
     *
     * @see Paths#applyRelativePath(String, String)
     */
    @Override
    public IResource createRelative(final String relativePath) {
        String pathToUse = Paths.applyRelativePath(this.path, relativePath);
        return new ClassPathResource(pathToUse, this.classLoader, this.clazz);
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation returns the name of the file that this class path resource refers to.
     * </p>
     *
     * @see Paths#getFilename(String)
     */
    @Override
    public @Nullable String getFilename() {
        return Paths.getFilename(this.path);
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation returns a description that includes the class path location.
     * </p>
     */
    @Override
    @Nonnull
    public String getDescription() {
        StringBuilder builder = new StringBuilder("class path resource [");

        String pathToUse = path;

        if (this.clazz != null && !pathToUse.startsWith("/")) {
            builder.append(classPackageAsResourcePath(this.clazz));
            builder.append('/');
        }

        if (pathToUse.startsWith("/")) {
            pathToUse = pathToUse.substring(1);
        }

        builder.append(pathToUse);
        builder.append(']');
        return builder.toString();
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation compares the underlying class path locations.
     * </p>
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ClassPathResource) {
            ClassPathResource otherRes = (ClassPathResource) obj;
            return this.path.equals(otherRes.path) //
                    && Objects.equal(this.classLoader, otherRes.classLoader) //
                    && Objects.equal(this.clazz, otherRes.clazz);
        }
        return false;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation returns the hash code of the underlying class path location.
     * </p>
     */
    @Override
    public int hashCode() {
        return this.path.hashCode();
    }

    /**
     * Given an input class object, return a string which consists of the class's package name as a pathname, i.e., all
     * dots ('.') are replaced by slashes ('/'). Neither a leading nor trailing slash is added. The result could be
     * concatenated with a slash and the name of a resource and fed directly to <code>ClassLoader.getResource()</code>.
     * For it to be fed to <code>Class.getResource</code> instead, a leading slash would also have to be prepended to
     * the returned value.
     *
     * @param clazz
     *            the input class. A <code>null</code> value or the default (empty) package will result in an empty
     *            string ("") being returned.
     * @return a path which represents the package name
     * @see ClassLoader#getResource
     * @see Class#getResource
     */
    private static String classPackageAsResourcePath(final Class<?> clazz) {
        if (clazz == null) {
            return "";
        }
        String className = clazz.getName();
        int packageEndIndex = className.lastIndexOf('.');
        if (packageEndIndex == -1) {
            return "";
        }
        String packageName = className.substring(0, packageEndIndex);
        return packageName.replace('.', '/');
    }

}
