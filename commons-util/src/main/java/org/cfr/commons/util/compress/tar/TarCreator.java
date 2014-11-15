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

import java.io.BufferedOutputStream;
import java.io.File;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.cfr.commons.util.compress.AbstractArchiveCreator;

/**
 * Helper class to create TAR archive.
 *
 * @author acochard [Jul 30, 2009]
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 */
public class TarCreator extends AbstractArchiveCreator<TarArchiveOutputStream, TarArchiveEntry> {

    /**
     * Create a new TarCreator Instance
     * 
     * @param archiveFile
     */
    public TarCreator(final File archiveFile) {
        super(archiveFile);
    }

    @Override
    protected TarArchiveEntry createArchiveEntry(final String name, final File file) {
        TarArchiveEntry entry = new TarArchiveEntry(name);
        entry.setSize(file.length());
        return entry;
    }

    @Override
    protected TarArchiveOutputStream createArchiveOutputStream(final BufferedOutputStream stream) {
        return new TarArchiveOutputStream(stream);
    }
}
