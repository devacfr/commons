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
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.cfr.commons.io.IResource;
import org.cfr.commons.util.Assert;
import org.cfr.commons.util.Paths;
import org.cfr.commons.util.ResourceUtils;

/**
 * {@link Resource} implementation for {@code java.net.URL} locators. Obviously supports resolution as URL, and also as
 * File in case of the "file:" protocol.
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 * @see java.net.URL
 */
public class UrlResource extends AbstractFileResolvingResource {

    /**
     * Original URL, used for actual access.
     */
    private final @Nonnull URL url;

    /**
     * Cleaned URL (with normalized path), used for comparisons.
     */
    private final @Nonnull URL cleanedUrl;

    /**
     * Original URI, if available; used for URI and File access.
     */
    private @Nullable URI uri = null;

    /**
     * Create a new UrlResource instance.
     *
     * @param url
     *            a URL (never {@code null}).
     * @throws IllegalArgumentException
     *             Throws {@code url} if {@code null}.
     */
    public UrlResource(@Nonnull final URL url) {
        this.url = Assert.checkNotNull(url, "URL");
        this.cleanedUrl = getCleanedUrl(this.url, url.toString());
    }

    /**
     * Create a new UrlResource instance.
     *
     * @param uri
     *            a URI (never {@code null}).
     * @throws MalformedURLException
     *             if the given URL path is not valid
     * @throws IllegalArgumentException
     *             Throws {@code uri} if {@code null}.
     */
    public UrlResource(@Nonnull final URI uri) throws MalformedURLException {
        Assert.checkNotNull(uri, "uri");
        this.url = uri.toURL();
        this.cleanedUrl = getCleanedUrl(this.url, uri.toString());
        this.uri = uri;
    }

    /**
     * Create a new UrlResource instance.
     *
     * @param path
     *            a URL path (never {@code null}).
     * @throws MalformedURLException
     *             if the given URL path is not valid
     * @throws IllegalArgumentException
     *             Throws {@code path} if {@code null}.
     */
    public UrlResource(final String path) throws MalformedURLException {
        Assert.checkNotNull(path, "Path");
        this.url = new URL(path);
        this.cleanedUrl = getCleanedUrl(this.url, path);
    }

    /**
     * Determine a cleaned URL for the given original URL.
     *
     * @param originalUrl
     *            the original URL.
     * @param originalPath
     *            the original URL path (never {@code null}).
     * @return the cleaned URL
     * @see Paths#cleanPath
     * @throws IllegalArgumentException
     *             Throws {@code path} if {@code null}.
     */
    private @Nullable URL getCleanedUrl(@Nullable final URL originalUrl, @Nonnull final String originalPath) {
        try {
            return new URL(Paths.cleanPath(originalPath));
        } catch (MalformedURLException ex) {
            // Cleaned URL path cannot be converted to URL
            // -> take original URL.
            return originalUrl;
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation opens an InputStream for the given URL. It sets the "UseCaches" flag to <code>false</code>,
     * mainly to avoid jar file locking on Windows.
     * </p>
     *
     * @see java.net.URL#openConnection()
     * @see java.net.URLConnection#setUseCaches(boolean)
     * @see java.net.URLConnection#getInputStream()
     */
    @Override
    public @Nonnull InputStream getInputStream() throws IOException {
        URLConnection con = this.url.openConnection();
        ResourceUtils.useCachesIfNecessary(con);
        try {
            return con.getInputStream();
        } catch (IOException ex) {
            // Close the HTTP connection (if applicable).
            if (con instanceof HttpURLConnection) {
                ((HttpURLConnection) con).disconnect();
            }
            throw ex;
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation returns the underlying URL reference.
     * </p>
     */
    @Override
    public @Nonnull URL getURL() throws IOException {
        return this.url;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation returns the underlying URI directly, if possible.
     * </p>
     */
    @Override
    public @Nonnull URI getURI() throws IOException {
        if (this.uri != null) {
            return this.uri;
        } else {
            return super.getURI();
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation returns a File reference for the underlying URL/URI, provided that it refers to a file in the
     * file system.
     * </p>
     *
     * @see org.springframework.util.ResourceUtils#getFile(java.net.URL, String)
     */
    @Override
    public @Nonnull File getFile() throws IOException {
        if (this.uri != null) {
            return super.getFile(this.uri);
        } else {
            return super.getFile();
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation creates a UrlResource, applying the given path relative to the path of the underlying URL of
     * this resource descriptor.
     * </p>
     *
     * @see java.net.URL#URL(java.net.URL, String)
     */
    @Override
    public @Nonnull IResource createRelative(@Nonnull String relativePath) throws MalformedURLException {
        if (relativePath.startsWith("/")) {
            relativePath = relativePath.substring(1);
        }
        return new UrlResource(new URL(this.url, relativePath));
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation returns the name of the file that this URL refers to.
     * </p>
     *
     * @see java.net.URL#getFile()
     * @see java.io.File#getName()
     */
    @Override
    public @Nonnull String getFilename() {
        return new File(this.url.getFile()).getName();
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation returns a description that includes the URL.
     * </p>
     */
    @Override
    public @Nonnull String getDescription() {
        return "URL [" + this.url + "]";
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation compares the underlying URL references.
     * </p>
     */
    @Override
    public boolean equals(@Nullable final Object obj) {
        return (obj == this || (obj instanceof UrlResource && this.cleanedUrl.equals(((UrlResource) obj).cleanedUrl)));
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation returns the hash code of the underlying URL reference.
     * </p>
     */
    @Override
    public int hashCode() {
        return this.cleanedUrl.hashCode();
    }

}
