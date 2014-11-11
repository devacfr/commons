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
package org.cfr.commons.util.compress;

import java.io.File;
import java.io.IOException;

/**
 * Helper interface to extract archive.
 *
 * @author acochard
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
public interface IArchiveExtractor {

    /**
     * Extract the archive file to the given destination.
     *
     * @param destination
     *            the destination the file will be extracted to.
     * @throws IOException
     *             if an io exception occures.
     */
    void deflate(File destination) throws IOException;

    /**
     * Extract the archive file to the given destination.
     *
     * @param destination
     *            the destination the file will be extracted to.
     * @param outputFilePattern
     *            only file matching this pattern will be deflated.
     * @throws IOException
     *             if an io exception occures.
     */
    void deflate(File destination, String outputFilePattern) throws IOException;

    /**
     * Extract the archive file to the given destination, without directory tree structure.
     *
     * @param destination
     *            the destination the file will be extracted to.
     * @param outputFilePattern
     *            only file matching this pattern will be deflated.
     * @param flat
     *            all files will be placed in the destination folder without the directory structure.
     * @throws IOException
     *             if an io exception occures.
     */
    void deflate(File destination, String outputFilePattern, boolean flat) throws IOException;

}
