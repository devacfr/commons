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
package org.cfr.commons.util.compress.tar;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.cfr.commons.io.IResource;
import org.cfr.commons.util.compress.AbstractArchiveExtractor;

/**
 * Helper class to extract TAR archive.
 *
 * @author acochard [Jul 30, 2009]
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
public class TarExtractor extends AbstractArchiveExtractor<TarArchiveInputStream> {

    /**
     * @param archiveFile
     * @throws FileNotFoundException
     */
    public TarExtractor(final IResource archiveFile) throws FileNotFoundException {
        super(archiveFile);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected TarArchiveInputStream createArchiveInputStream(final InputStream fileInputStream) {
        return new TarArchiveInputStream(fileInputStream);
    }
}
