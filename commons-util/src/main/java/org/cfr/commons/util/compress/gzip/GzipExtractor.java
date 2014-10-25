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
package org.cfr.commons.util.compress.gzip;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.IOUtils;
import org.cfr.commons.util.Assert;
import org.cfr.commons.util.compress.IArchiveExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

/**
 * Helper class to extract GZIP archive.
 *
 * @author acochard
 */
public class GzipExtractor implements IArchiveExtractor {

    private static final Logger LOGGER = LoggerFactory.getLogger(GzipExtractor.class);

    /**
     * Archive file.
     */
    private final Resource archiveFile;

    /**
     * Creates a new extractor for the given file.
     *
     * @param archiveFile
     *            the gzip archive file.
     */
    public GzipExtractor(Resource archiveFile) {
        this.archiveFile = Assert.notNull(archiveFile);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deflate(File destination) throws IOException {
        if (destination.isDirectory()) {
            throw new IllegalArgumentException("Destination is a directory: " + destination.getCanonicalPath());
        }

        // Open input & output file
        GZIPInputStream fileInput = new GZIPInputStream(archiveFile.getInputStream());
        BufferedOutputStream fileOutput = new BufferedOutputStream(new FileOutputStream(destination));

        LOGGER.info("Unflating file: '" + archiveFile.getFilename() + "'");

        try {
            IOUtils.copy(fileInput, fileOutput);
        } finally {
            IOUtils.closeQuietly(fileOutput);
            IOUtils.closeQuietly(fileInput);
        }
    }

    @Override
    public void deflate(File destination, String outputFilePattern) throws IOException {
        deflate(destination);
    }

    @Override
    public void deflate(File destination, String outputFilePattern, boolean flat) throws IOException {
        deflate(destination);
    }
}
