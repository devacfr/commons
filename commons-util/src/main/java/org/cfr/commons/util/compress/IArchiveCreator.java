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
 * Helper interface to create archive.
 * 
 * @author acochard
 */
public interface IArchiveCreator {

    /**
     * @return the archive file.
     */
    File getArchiveFile();

    /**
     * Inflate all files within the given directories.
     * 
     * @param directories
     *            the directories all files are zipped within
     * @return the zipped file.
     * @throws IOException
     *             if an io exception occures.
     */
    File inflate(File... directories) throws IOException;

    /**
     * Inflate the specified file.
     * 
     * @param file
     *            the file to inflate.
     * @return the inflated file.
     * @throws IOException
     *             if an io exception occures.
     */
    File inflate(File file) throws IOException;
}
