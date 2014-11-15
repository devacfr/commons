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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.WillClose;

/**
 * Interface for a resource descriptor that abstracts from the actual type of underlying resource, such as a file or
 * class path resource.
 * <p>
 * An InputStream can be opened for every resource if it exists in physical form, but a URL or File handle can just be
 * returned for certain resources. The actual behavior is implementation-specific.
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
public interface IResource {

    /**
     * Return an {@link InputStream}.
     * <p>
     * It is expected that each call creates a <i>fresh</i> stream.
     * <p>
     * This requirement is particularly important when you consider an API such as JavaMail, which needs to be able to
     * read the stream multiple times when creating mail attachments. For such a use case, it is <i>required</i> that
     * each <code>getInputStream()</code> call returns a fresh stream.
     *
     * @return the input stream for the underlying resource (must not be {@code null})
     * @throws IOException
     *             if the stream could not be opened.
     */
    @Nonnull
    @WillClose
    InputStream getInputStream() throws IOException;

    /**
     * Gets indicating whether this resource actually exists in physical form.
     * <p>
     * This method performs a definitive existence check, whereas the existence of a <code>Resource</code> handle only
     * guarantees a valid descriptor handle.
     *
     * @return Returns {@code true} whether this resource actually exists in physical form, otherwise {@code false}.
     */
    boolean exists();

    /**
     * gets indicating whether the contents of this resource can be read, e.g. via {@link #getInputStream()} or
     * {@link #getFile()}.
     * <p>
     * Will be <code>true</code> for typical resource descriptors; note that actual content reading may still fail when
     * attempted. However, a value of <code>false</code> is a definitive indication that the resource content cannot be
     * read.
     *
     * @return Returns {@code true} whether the contents of this resource can be read, otherwise {@code false}.
     */
    boolean isReadable();

    /**
     * Gets indicating whether this resource represents a handle with an open stream. If true, the InputStream cannot be
     * read multiple times, and must be read and closed to avoid resource leaks.
     * <p>
     * Will be {@code false} for typical resource descriptors.
     * </p>
     *
     * @return Return {@code true} whether this resource represents a handle with an open stream, otherwise
     *         {@code false}.
     */
    boolean isOpen();

    /**
     * Gets a URL handle for this resource.
     *
     * @return Returns a URL handle for this resource.
     * @throws IOException
     *             if the resource cannot be resolved as URL, i.e. if the resource is not available as descriptor
     */
    @Nonnull
    URL getURL() throws IOException;

    /**
     * Gets a URI handle for this resource.
     *
     * @return a URI handle for this resource.
     * @throws IOException
     *             if the resource cannot be resolved as URI, i.e. if the resource is not available as descriptor
     */
    @Nonnull
    URI getURI() throws IOException;

    /**
     * @return Returns a File handle for this resource.
     * @throws IOException
     *             if the resource cannot be resolved as absolute file path, i.e. if the resource is not available in a
     *             file system
     */
    @Nonnull
    File getFile() throws IOException;

    /**
     * @return Determines the content length for this resource.
     * @throws IOException
     *             if the resource cannot be resolved (in the file system or as some other known physical resource type)
     */
    long contentLength() throws IOException;

    /**
     * @return Determines the last-modified timestamp for this resource.
     * @throws IOException
     *             if the resource cannot be resolved (in the file system or as some other known physical resource type)
     */
    long lastModified() throws IOException;

    /**
     * Create a resource relative to this resource.
     *
     * @param relativePath
     *            the relative path (relative to this resource). Can not be {@code null}.
     * @return the resource handle for the relative resource
     * @throws IOException
     *             if the relative resource cannot be determined
     * @throws IllegalArgumentException
     *             Throws {@code relativePath} if {@code null}.
     */
    IResource createRelative(@Nonnull String relativePath) throws IOException;

    /**
     * @return Determine a filename for this resource, i.e. typically the last part of the path: for example,
     *         "myfile.txt". Returns {@code null} if this type of resource does not have a filename.
     */
    @Nullable
    @CheckReturnValue
    String getFilename();

    /**
     * @return Returns a description for this resource, to be used for error output when working with the resource.
     *         Implementations are also encouraged to return this value from their {@code toString} method.
     */
    @Nonnull
    String getDescription();

}
