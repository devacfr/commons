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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.io.IOUtils;
import org.cfr.commons.io.IResource;
import org.cfr.commons.util.ResourceUtils;

/**
 * Convenience base class for {@link Resource} implementations, pre-implementing typical behavior.
 * <p>
 * The "exists" method will check whether a File or InputStream can be opened; "isOpen" will always return false;
 * "getURL" and "getFile" throw an exception; and "toString" will return the description.
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
public abstract class AbstractResource implements IResource {

    /**
     * {@inheritDoc}
     * <p>
     * This implementation checks whether a File can be opened, falling back to whether an InputStream can be opened.
     * This will cover both directories and content resources.
     * </p>
     */
    @Override
    @SuppressWarnings("PMD")
    public boolean exists() {
        // Try file existence: can we find the file in the file system?
        try {
            return getFile().exists();
        } catch (IOException ex) {
            // Fall back to stream existence: can we open the stream?
            try {
                InputStream is = getInputStream();
                is.close();
                return true;
            } catch (Throwable isEx) {
                return false;
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation always returns {@code true}.
     * </p>
     */
    @Override
    public boolean isReadable() {
        return true;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation always returns {@code false}.
     * </p>
     */
    @Override
    public boolean isOpen() {
        return false;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation throws a FileNotFoundException, assuming that the resource cannot be resolved to a URL.
     * </p>
     */
    @Override
    public URL getURL() throws IOException {
        throw new FileNotFoundException(getDescription() + " cannot be resolved to URL");
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation builds a URI based on the URL returned by {@link #getURL()}.
     * </p>
     */
    @Override
    public URI getURI() throws IOException {
        URL url = getURL();
        try {
            return ResourceUtils.toURI(url);
        } catch (URISyntaxException ex) {
            throw new IOException("Invalid URI [" + url + "]", ex);
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation throws a FileNotFoundException, assuming that the resource cannot be resolved to an absolute
     * file path.
     * </p>
     */
    @Override
    public File getFile() throws IOException {
        throw new FileNotFoundException(getDescription() + " cannot be resolved to absolute file path");
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation reads the entire InputStream to calculate the content length. Subclasses will almost always
     * be able to provide a more optimal version of this, e.g. checking a File length.
     * </p>
     *
     * @see #getInputStream()
     */
    @Override
    @SuppressWarnings("PMD")
    public long contentLength() throws IOException {
        InputStream is = getInputStream();
        try {
            long size = 0;
            byte[] buf = new byte[255];
            int read;
            while ((read = is.read(buf)) != -1) {
                size += read;
            }
            return size;
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation checks the timestamp of the underlying File, if available.
     * </p>
     *
     * @see #getFileForLastModifiedCheck()
     */
    @Override
    public long lastModified() throws IOException {
        long lastModified = getFileForLastModifiedCheck().lastModified();
        if (lastModified == 0L) {
            throw new FileNotFoundException(getDescription()
                    + " cannot be resolved in the file system for resolving its last-modified timestamp");
        }
        return lastModified;
    }

    /**
     * Determine the File to use for timestamp checking.
     * <p>
     * The default implementation delegates to {@link #getFile()}.
     *
     * @return the File to use for timestamp checking (never {@code null})
     * @throws IOException
     *             if the resource cannot be resolved as absolute file path, i.e. if the resource is not available in a
     *             file system
     */
    protected @Nonnull File getFileForLastModifiedCheck() throws IOException {
        return getFile();
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation throws a FileNotFoundException, assuming that relative resources cannot be created for this
     * resource.
     * </p>
     */
    @Override
    public IResource createRelative(@Nonnull final String relativePath) throws IOException {
        throw new FileNotFoundException("Cannot create a relative resource for " + getDescription());
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation always returns {@code null}, assuming that this resource type does not have a filename.
     * </p>
     */
    @Override
    @SuppressWarnings("PMD")
    public @Nullable String getFilename() {
        return null;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation returns the description of this resource.
     * </p
     *
     * @see #getDescription()
     */
    @Override
    public String toString() {
        return getDescription();
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation compares description strings.
     * </p>
     *
     * @see #getDescription()
     */
    @Override
    public boolean equals(final Object obj) {
        return (obj == this || (obj instanceof IResource && //
        ((IResource) obj).getDescription().equals(getDescription())));
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation returns the description's hash code.
     * </p>
     *
     * @see #getDescription()
     */
    @Override
    public int hashCode() {
        return getDescription().hashCode();
    }

}