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
package org.cfr.commons.util.compress.zip;

import java.io.BufferedOutputStream;
import java.io.File;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.cfr.commons.util.compress.AbstractArchiveCreator;

/**
 * Helper class to create ZIP archive.
 * 
 * @author acochard [Jul 30, 2009]
 */
public class ZipCreator extends AbstractArchiveCreator<ZipArchiveOutputStream, ZipArchiveEntry> {

    public ZipCreator(File archiveFile) {
        super(archiveFile);
    }

    @Override
    protected ZipArchiveEntry createArchiveEntry(String name, File file) {
        return new ZipArchiveEntry(name);
    }

    @Override
    protected ZipArchiveOutputStream createArchiveOutputStream(BufferedOutputStream stream) {
        return new ZipArchiveOutputStream(stream);
    }

}