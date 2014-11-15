/**
 * Copyright 2002-2009 the original author or authors.
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

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Utility methods for resolving resource locations to files in the file system. Mainly for internal use within the
 * framework.
 *
 * @author Juergen Hoeller (SpringFramework)
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
public class ResourceUtils {

    /** Pseudo URL prefix for loading from the class path: "classpath:" */
    public static final String CLASSPATH_URL_PREFIX = "classpath:";

    /** URL prefix for loading from the file system: "file:" */
    public static final String FILE_URL_PREFIX = "file:";

    /** URL protocol for a file in the file system: "file" */
    public static final String URL_PROTOCOL_FILE = "file";

    /** URL protocol for an entry from a jar file: "jar" */
    public static final String URL_PROTOCOL_JAR = "jar";

    /** URL protocol for an entry from a zip file: "zip" */
    public static final String URL_PROTOCOL_ZIP = "zip";

    /** URL protocol for an entry from a JBoss jar file: "vfszip" */
    public static final String URL_PROTOCOL_VFSZIP = "vfszip";

    /** URL protocol for a JBoss VFS resource: "vfs" */
    public static final String URL_PROTOCOL_VFS = "vfs";

    /** URL protocol for an entry from a WebSphere jar file: "wsjar" */
    public static final String URL_PROTOCOL_WSJAR = "wsjar";

    /** URL protocol for an entry from an OC4J jar file: "code-source" */
    public static final String URL_PROTOCOL_CODE_SOURCE = "code-source";

    /** Separator between JAR URL and file path within the JAR */
    public static final String JAR_URL_SEPARATOR = "!/";

    /**
     * Singleton restriction instantiation of the class
     */
    private ResourceUtils() {

    }

    /**
     * Return whether the given resource location is a URL: either a special "classpath" pseudo URL or a standard URL.
     *
     * @param resourceLocation
     *            the location String to check
     * @return whether the location qualifies as a URL
     * @see #CLASSPATH_URL_PREFIX
     * @see java.net.URL
     */
    public static boolean isUrl(final String resourceLocation) {
        if (resourceLocation == null) {
            return false;
        }
        if (resourceLocation.startsWith(CLASSPATH_URL_PREFIX)) {
            return true;
        }
        try {
            new URL(resourceLocation);
            return true;
        } catch (MalformedURLException ex) {
            return false;
        }
    }

    /**
     * Resolve the given resource location to a {@code java.net.URL}.
     * <p>
     * Does not check whether the URL actually exists; simply returns the URL that the given location would correspond
     * to.
     * </p>
     *
     * @param resourceLocation
     *            the resource location to resolve: either a "classpath:" pseudo URL, a "file:" URL, or a plain file
     *            path. Can not be {@code null}.
     * @return a corresponding URL object
     * @throws FileNotFoundException
     *             if the resource cannot be resolved to a URL
     * @throws IllegalArgumentException
     *             Throws {@code resourceLocation} if {@code null}.
     */
    @SuppressWarnings("PMD.PreserveStackTrace")
    public static @Nonnull URL getURL(@Nonnull final String resourceLocation) throws FileNotFoundException {
        Assert.checkNotNull(resourceLocation, "Resource location");
        if (resourceLocation.startsWith(CLASSPATH_URL_PREFIX)) {
            String path = resourceLocation.substring(CLASSPATH_URL_PREFIX.length());
            URL url = ClassUtils.getDefaultClassLoader().getResource(path);
            if (url == null) {
                String description = "class path resource [" + path + "]";
                throw new FileNotFoundException(description + " cannot be resolved to URL because it does not exist");
            }
            return url;
        }
        try {
            // try URL
            return new URL(resourceLocation);
        } catch (MalformedURLException ex) {
            // no URL -> treat as file path
            try {
                return new File(resourceLocation).toURI().toURL();
            } catch (MalformedURLException ex2) {
                throw new FileNotFoundException("Resource location [" + resourceLocation
                        + "] is neither a URL not a well-formed file path");
            }
        }
    }

    /**
     * Resolve the given resource location to a <code>java.io.File</code>, i.e. to a file in the file system.
     * <p>
     * Does not check whether the file actually exists; simply returns the File that the given location would correspond
     * to.
     * </p>
     *
     * @param resourceLocation
     *            the resource location to resolve: either a "classpath:" pseudo URL, a "file:" URL, or a plain file
     *            path. Can not be {@code null}.
     * @return a corresponding File object
     * @throws FileNotFoundException
     *             if the resource cannot be resolved to a file in the file system
     * @throws IllegalArgumentException
     *             Throws {@code resourceLocation} if {@code null}.
     */

    public static @Nonnull File getFile(@Nonnull final String resourceLocation) throws FileNotFoundException {
        Assert.checkNotNull(resourceLocation, "Resource location");
        if (resourceLocation.startsWith(CLASSPATH_URL_PREFIX)) {
            String path = resourceLocation.substring(CLASSPATH_URL_PREFIX.length());
            String description = "class path resource [" + path + "]";
            URL url = ClassUtils.getDefaultClassLoader().getResource(path);
            if (url == null) {
                throw new FileNotFoundException(description + " cannot be resolved to absolute file path "
                        + "because it does not reside in the file system");
            }
            return getFile(url, description);
        }
        try {
            // try URL
            return getFile(new URL(resourceLocation));
        } catch (MalformedURLException ex) {
            // no URL -> treat as file path
            return new File(resourceLocation);
        }
    }

    /**
     * Resolve the given resource URL to a {@code java.io.File}, i.e. to a file in the file system.
     *
     * @param resourceUrl
     *            the resource URL to resolve. Can not be {@code null}.
     * @return a corresponding File object
     * @throws FileNotFoundException
     *             if the URL cannot be resolved to a file in the file system
     * @throws IllegalArgumentException
     *             Throws {@code resourceUrl} if {@code null}.
     */
    public static @Nonnull File getFile(@Nonnull final URL resourceUrl) throws FileNotFoundException {
        return getFile(resourceUrl, "URL");
    }

    /**
     * Resolve the given resource URL to a {@code java.io.File}, i.e. to a file in the file system.
     *
     * @param resourceUrl
     *            the resource URL to resolve
     * @param description
     *            a description of the original resource that the URL was created for (for example, a class path
     *            location). Can not be {@code null}.
     * @return a corresponding File object
     * @throws FileNotFoundException
     *             if the URL cannot be resolved to a file in the file system.
     * @throws IllegalArgumentException
     *             Throws {@code resourceUrl} if {@code null}.
     */
    public static @Nonnull File getFile(@Nonnull final URL resourceUrl, @Nullable final String description)
            throws FileNotFoundException {
        Assert.checkNotNull(resourceUrl, "Resource URL");
        if (!URL_PROTOCOL_FILE.equals(resourceUrl.getProtocol())) {
            throw new FileNotFoundException(description + " cannot be resolved to absolute file path "
                    + "because it does not reside in the file system: " + resourceUrl);
        }
        try {
            return new File(toURI(resourceUrl).getSchemeSpecificPart());
        } catch (URISyntaxException ex) {
            // Fallback for URLs that are not valid URIs (should hardly ever happen).
            return new File(resourceUrl.getFile());
        }
    }

    /**
     * Resolve the given resource URI to a {@code java.io.File}, i.e. to a file in the file system.
     *
     * @param resourceUri
     *            the resource URI to resolve. Can not be {@code null}.
     * @return a corresponding File object
     * @throws FileNotFoundException
     *             if the URL cannot be resolved to a file in the file system.
     * @throws IllegalArgumentException
     *             Throws {@code resourceUrl} if {@code null}.
     */
    public static @Nonnull File getFile(@Nonnull final URI resourceUri) throws FileNotFoundException {
        return getFile(resourceUri, "URI");
    }

    /**
     * Resolve the given resource URI to a {@code java.io.File}, i.e. to a file in the file system.
     *
     * @param resourceUri
     *            the resource URI to resolve. Can not be {@code null}.
     * @param description
     *            a description of the original resource that the URI was created for (for example, a class path
     *            location)
     * @return a corresponding File object
     * @throws FileNotFoundException
     *             if the URL cannot be resolved to a file in the file system
     * @throws IllegalArgumentException
     *             Throws {@code resourceUri} if {@code null}.
     */
    public static File getFile(@Nonnull final URI resourceUri, @Nullable final String description)
            throws FileNotFoundException {
        Assert.checkNotNull(resourceUri, "Resource URI");
        if (!URL_PROTOCOL_FILE.equals(resourceUri.getScheme())) {
            throw new FileNotFoundException(description + " cannot be resolved to absolute file path "
                    + "because it does not reside in the file system: " + resourceUri);
        }
        return new File(resourceUri.getSchemeSpecificPart());
    }

    /**
     * Determine whether the given URL points to a resource in the file system, that is, has protocol "file" or "vfs".
     *
     * @param url
     *            the URL to check. Can not be {@code null}.
     * @return whether the URL has been identified as a file system URL
     * @throws IllegalArgumentException
     *             Throws {@code url} if {@code null}.
     */
    public static boolean isFileURL(@Nonnull final URL url) {
        String protocol = Assert.checkNotNull(url, "url").getProtocol();
        return (URL_PROTOCOL_FILE.equals(protocol) || protocol.startsWith(URL_PROTOCOL_VFS));
    }

    /**
     * Determine whether the given URL points to a resource in a jar file, that is, has protocol "jar", "zip", "wsjar"
     * or "code-source".
     * <p>
     * "zip" and "wsjar" are used by BEA WebLogic Server and IBM WebSphere, respectively, but can be treated like jar
     * files. The same applies to "code-source" URLs on Oracle OC4J, provided that the path contains a jar separator.
     *
     * @param url
     *            the URL to check. can not be {@code null}.
     * @return whether the URL has been identified as a JAR URL
     * @throws IllegalArgumentException
     *             Throws {@code url} if {@code null}.
     */
    public static boolean isJarURL(@Nonnull final URL url) {
        String protocol = Assert.checkNotNull(url, "url").getProtocol();
        return (URL_PROTOCOL_JAR.equals(protocol) || URL_PROTOCOL_ZIP.equals(protocol)
                || URL_PROTOCOL_WSJAR.equals(protocol) || (URL_PROTOCOL_CODE_SOURCE.equals(protocol) && url.getPath()
                .contains(JAR_URL_SEPARATOR)));
    }

    /**
     * Extract the URL for the actual jar file from the given URL (which may point to a resource in a jar file or to a
     * jar file itself).
     *
     * @param jarUrl
     *            the original URL. Can not be {@code null}.
     * @return the URL for the actual jar file
     * @throws MalformedURLException
     *             if no valid jar file URL could be extracted
     * @throws IllegalArgumentException
     *             Throws {@code jarUrl} if {@code null}.
     */
    public static @Nonnull URL extractJarFileURL(@Nonnull final URL jarUrl) throws MalformedURLException {
        String urlFile = Assert.checkNotNull(jarUrl, "jarUrl").getFile();
        int separatorIndex = urlFile.indexOf(JAR_URL_SEPARATOR);
        if (separatorIndex != -1) {
            String jarFile = urlFile.substring(0, separatorIndex);
            try {
                return new URL(jarFile);
            } catch (MalformedURLException ex) {
                // Probably no protocol in original jar URL, like "jar:C:/mypath/myjar.jar".
                // This usually indicates that the jar file resides in the file system.
                if (!jarFile.startsWith("/")) {
                    jarFile = "/" + jarFile;
                }
                return new URL(FILE_URL_PREFIX + jarFile);
            }
        } else {
            return jarUrl;
        }
    }

    /**
     * Create a URI instance for the given URL, replacing spaces with "%20" quotes first.
     * <p>
     * Furthermore, this method works on JDK 1.4 as well, in contrast to the <code>URL.toURI()</code> method.
     *
     * @param url
     *            the URL to convert into a URI instance
     * @return the URI instance
     * @throws URISyntaxException
     *             if the URL wasn't a valid URI
     * @see java.net.URL#toURI()
     */
    public static URI toURI(final URL url) throws URISyntaxException {
        return toURI(url.toString());
    }

    /**
     * Create a URI instance for the given location String, replacing spaces with "%20" quotes first.
     *
     * @param location
     *            the location String to convert into a URI instance
     * @return the URI instance
     * @throws URISyntaxException
     *             if the location wasn't a valid URI
     */
    public static URI toURI(final String location) throws URISyntaxException {
        return new URI(Paths.replace(location, " ", "%20"));
    }

    /**
     * Set the {@link URLConnection#setUseCaches "useCaches"} flag on the given connection, preferring
     * <code>false</code> but leaving the flag at <code>true</code> for JNLP based resources.
     *
     * @param con
     *            the URLConnection to set the flag on
     */
    public static void useCachesIfNecessary(final URLConnection con) {
        con.setUseCaches(con.getClass().getName().startsWith("JNLP"));
    }

}