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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.io.IOUtils;
import org.cfr.commons.io.IResource;
import org.cfr.commons.util.AntPathMatcher;
import org.cfr.commons.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

/**
 * Abstract ArchiveExtractor to implement org.apache.commons.compress archivers.
 *
 * @author acochard [Jul 30, 2009]
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 * @param <I>
 *            the Archive input streams used.
 */
public abstract class AbstractArchiveExtractor<I extends ArchiveInputStream> implements IArchiveExtractor {

    /**
     *
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(AbstractArchiveExtractor.class);

    /**
     *
     */
    private final AntPathMatcher matcher = new AntPathMatcher();

    /**
     * Archive file.
     */
    private final IResource archiveFile;

    /**
     * Creates a new extractor for the given file.
     *
     * @param archiveFile
     *            the archive file that will be extracted.
     * @throws FileNotFoundException
     *             if the file does not exist.
     */
    public AbstractArchiveExtractor(@Nonnull final IResource archiveFile) throws FileNotFoundException {
        Assert.checkNotNull(archiveFile, "archiveFile");
        if (!archiveFile.exists()) {
            throw new FileNotFoundException("File not found " + archiveFile.getFilename());
        }

        this.archiveFile = archiveFile;

    }

    /**
     * Create new specific inputstream.
     *
     * @param fileInputStream
     *            the inputstream to use.
     * @return Returns new specific inputstream.
     */
    protected abstract I createArchiveInputStream(@Nonnull InputStream fileInputStream);

    /**
     * {@inheritDoc}
     */
    @Override
    public void deflate(@Nonnull final File destination) throws IOException {
        deflate(destination, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deflate(final File destination, final String outputFilePattern) throws IOException {
        deflate(destination, outputFilePattern, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deflate(@Nonnull final File destination, @Nullable final String outputFilePattern, final boolean flat)
            throws IOException {
        Assert.checkNotNull(destination, "destination");
        if (!destination.exists() || !destination.isDirectory()) {
            throw new IllegalArgumentException("Invalid destination: " + destination.getCanonicalPath());
        }

        AntPathMatcher matcher = new AntPathMatcher();
        I archiveInputStream = null;

        try {
            archiveInputStream = createArchiveInputStream(archiveFile.getInputStream());

            for (ArchiveEntry entry = archiveInputStream.getNextEntry(); entry != null; entry =
                    archiveInputStream.getNextEntry()) {

                String entryName = entry.getName();

                // Is a directory
                if (entry.isDirectory() && !flat) {
                    File newDir = new File(destination, entryName);
                    newDir.mkdirs();
                    continue;
                }

                // Output file pattern check
                if (!Strings.isNullOrEmpty(outputFilePattern)
                        && !matcher.match(outputFilePattern, new File(entryName).getName())) {
                    continue;
                }

                // Remove directory strucutre if flat mode actived
                if (flat) {
                    entryName = new File(entryName).getName();
                }
                File newFile = new File(destination, entryName);

                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Deflating" + archiveFile.getFilename());
                    LOGGER.debug("Extracting file " + newFile.getAbsolutePath());
                }

                // Make the directory structure
                newFile.getParentFile().mkdirs();
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(newFile);
                    IOUtils.copy(archiveInputStream, fileOutputStream);
                } finally {
                    IOUtils.closeQuietly(fileOutputStream);
                }
            }
        } finally {
            IOUtils.closeQuietly(archiveInputStream);
        }
    }

    /**
     * Deflates the first entry in archive that matches with the {@code outputFilePattern} parameter Ant pattern.
     *
     * @param outputFilePattern
     *            Ant pattern used.
     * @return Returns the first outputstream according to the <code>outputFilePattern</code> parameter.
     * @throws IOException
     *             if IO errors occurs
     */
    @CheckReturnValue
    public @Nullable InputStream deflate(@Nonnull final String outputFilePattern) throws IOException {
        I archiveInputStream = null;
        try {
            archiveInputStream = createArchiveInputStream(archiveFile.getInputStream());

            for (ArchiveEntry entry = archiveInputStream.getNextEntry(); entry != null; entry =
                    archiveInputStream.getNextEntry()) {

                String entryName = entry.getName();

                // Output file pattern check
                if (!Strings.isNullOrEmpty(outputFilePattern)
                        && !matcher.match(outputFilePattern, new File(entryName).getName())) {
                    continue;
                }
                return IOUtils.toBufferedInputStream(archiveInputStream);
            }
        } finally {
            IOUtils.closeQuietly(archiveInputStream);
        }
        return null;
    }

    /**
     * @param outputFilePattern
     * @return
     * @throws IOException
     */
    public boolean entryExist(@Nonnull final String outputFilePattern) throws IOException {
        Assert.checkHasText(outputFilePattern, "outputFilePattern");
        I archiveInputStream = null;

        try {
            archiveInputStream = createArchiveInputStream(archiveFile.getInputStream());

            for (ArchiveEntry entry = archiveInputStream.getNextEntry(); entry != null; entry =
                    archiveInputStream.getNextEntry()) {

                String entryName = entry.getName();

                // Output file pattern check
                if (!Strings.isNullOrEmpty(outputFilePattern)
                        && !matcher.match(outputFilePattern, new File(entryName).getName())) {
                    continue;
                }

                return true;
            }
        } finally {
            IOUtils.closeQuietly(archiveInputStream);
        }
        return false;
    }
}
