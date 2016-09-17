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
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import javax.annotation.Nonnull;

import org.cfr.commons.util.ResourceUtils;

/**
 * Abstract base class for resources which resolve URLs into File references, such as {@link UrlResource} or
 * {@link ClassPathResource}.
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
public abstract class AbstractFileResolvingResource extends AbstractResource {

    /**
     * This implementation returns a File reference for the underlying class path resource, provided that it refers to a
     * file in the file system.
     *
     * @return a corresponding File object
     * @throws IOException
     *             if the URL cannot be resolved to a file in the file system.
     */
    @Override
    public @Nonnull File getFile() throws IOException {
        URL url = getURL();
        return ResourceUtils.getFile(url, getDescription());
    }

    /**
     * @return Returns This implementation determines the underlying File (or jar file, in case of a resource in a
     *         jar/zip). can not be {@code null}.
     * @throws IOException
     *             if the URL cannot be resolved to a file in the file system.
     */
    @Override
    protected @Nonnull File getFileForLastModifiedCheck() throws IOException {
        URL url = getURL();
        if (ResourceUtils.isJarURL(url)) {
            URL actualUrl = ResourceUtils.extractJarFileURL(url);
            return ResourceUtils.getFile(actualUrl, "Jar URL");
        } else {
            return getFile();
        }
    }

    /**
     * Gets a File reference for the underlying class path resource.
     *
     * @param uri
     *            a URI (cannot be {@code null}).
     * @return Returns a File reference for the underlying class path resource, provided that it refers to a file in the
     *         file system.
     * @throws IOException
     *             if the URL cannot be resolved to a file in the file system.
     */
    protected @Nonnull File getFile(@Nonnull final URI uri) throws IOException {
        return ResourceUtils.getFile(uri, getDescription());
    }

    @Override
    public boolean exists() {
        try {
            URL url = getURL();
            if (ResourceUtils.isFileURL(url)) {
                // Proceed with file system resolution...
                return getFile().exists();
            } else {
                // Try a URL connection content-length header...
                URLConnection con = url.openConnection();
                ResourceUtils.useCachesIfNecessary(con);
                HttpURLConnection httpCon = null;
                if (con instanceof HttpURLConnection) {
                    httpCon = (HttpURLConnection) con;
                }
                if (httpCon != null) {
                    httpCon.setRequestMethod("HEAD");
                    int code = httpCon.getResponseCode();
                    if (code == HttpURLConnection.HTTP_OK) {
                        return true;
                    } else if (code == HttpURLConnection.HTTP_NOT_FOUND) {
                        return false;
                    }
                }
                if (con.getContentLength() >= 0) {
                    return true;
                }
                if (httpCon != null) {
                    // no HTTP OK status, and no content-length header: give up
                    httpCon.disconnect();
                    return false;
                } else {
                    // Fall back to stream existence: can we open the stream?
                    InputStream is = getInputStream();
                    is.close();
                    return true;
                }
            }
        } catch (IOException ex) {
            return false;
        }
    }

    @Override
    public boolean isReadable() {
        try {
            URL url = getURL();
            if (ResourceUtils.isFileURL(url)) {
                // Proceed with file system resolution...
                File file = getFile();
                return file.canRead() && !file.isDirectory();
            } else {
                return true;
            }
        } catch (IOException ex) {
            return false;
        }
    }

    @Override
    public long contentLength() throws IOException {
        URL url = getURL();
        if (ResourceUtils.isFileURL(url)) {
            // Proceed with file system resolution...
            return getFile().length();
        } else {
            // Try a URL connection content-length header...
            URLConnection con = url.openConnection();
            ResourceUtils.useCachesIfNecessary(con);
            if (con instanceof HttpURLConnection) {
                ((HttpURLConnection) con).setRequestMethod("HEAD");
            }
            return con.getContentLength();
        }
    }

    @Override
    public long lastModified() throws IOException {
        URL url = getURL();
        if (ResourceUtils.isFileURL(url) || ResourceUtils.isJarURL(url)) {
            // Proceed with file system resolution...
            return super.lastModified();
        } else {
            // Try a URL connection last-modified header...
            URLConnection con = url.openConnection();
            ResourceUtils.useCachesIfNecessary(con);
            if (con instanceof HttpURLConnection) {
                ((HttpURLConnection) con).setRequestMethod("HEAD");
            }
            return con.getLastModified();
        }
    }

}
