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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Nonnull;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.cfr.commons.util.Assert;

/**
 * Abstract ArchiveCreator to implement org.apache.commons.compress archivers.
 *
 * @author acochard [Jul 30, 2009]
 * @author devacfr<christophefriederich@mac.com>
 * @since 1.0
 * @param <O>
 *            the type of {@link ArchiveOutputStream}.
 * @param <E>
 *            the type of archive entry.
 */
public abstract class AbstractArchiveCreator<O extends ArchiveOutputStream, E extends ArchiveEntry>
        implements IArchiveCreator {

    /**
     *
     */
    private static final int BUFFER_SIZE = 2048;

    /**
     * Archive file.
     */
    private final @Nonnull File archiveFile;

    /**
     * Create a new archive creator with the given file as backend.
     *
     * @param archiveFile
     *            the tar file (never {@code null}).
     * @throws IllegalArgumentException
     *             Throws {@code key} if {@code null}.
     */
    public AbstractArchiveCreator(@Nonnull final File archiveFile) {
        this.archiveFile = Assert.checkNotNull(archiveFile, "Archive File");
    }

    /**
     * Add the files within the given directory to the ArchiveOutputStream. This method will call itself for each
     * subdirectory.
     *
     * @param baseName
     *            represents the relative base name within the tar file.
     * @param outStream
     *            the ouput stream.
     * @param directory
     *            the directory.
     * @throws IOException
     *             if an io exception occurs.
     */
    private void addFiles(@Nonnull final String baseName, @Nonnull final O outStream, @Nonnull final File directory)
            throws IOException {
        byte[] data = new byte[BUFFER_SIZE];

        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            String relativeName = getRelativePath(baseName, file);
            E entry = createArchiveEntry(relativeName, file);

            outStream.putArchiveEntry(entry);

            if (file.isDirectory()) {
                // Add the files within the directory
                addFiles(baseName, outStream, file);
                continue;
            }

            FileInputStream fileInputStream = null;
            BufferedInputStream origin = null;

            try {
                fileInputStream = new FileInputStream(file);
                origin = new BufferedInputStream(fileInputStream, BUFFER_SIZE);
                int count;
                while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
                    outStream.write(data, 0, count);
                }
            } finally {
                try {
                    outStream.closeArchiveEntry();
                } finally {
                    org.apache.commons.io.IOUtils.closeQuietly(fileInputStream);
                    org.apache.commons.io.IOUtils.closeQuietly(origin);
                }
            }
        }
    }

    /**
     * @param name
     * @param file
     * @return
     */
    protected abstract @Nonnull E createArchiveEntry(@Nonnull String name, @Nonnull File file);

    /**
     * @param stream
     * @return
     */
    protected abstract @Nonnull O createArchiveOutputStream(@Nonnull BufferedOutputStream stream);

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nonnull File getArchiveFile() {
        return archiveFile;
    }

    /**
     * Returns the relative path.
     *
     * @param baseName
     *            the base path (never {@code null).
     * @param file
     *            the file (never {@code null}).
     * @return the path of the given file relative to the base name.
     * @throws IOException
     *             if an io exception occures.
     * @throws IllegalArgumentException
     *             Throws {@code baseName} or {@code file} are {@code null}.
     */
    private @Nonnull String getRelativePath(@Nonnull final String baseName, @Nonnull final File file)
            throws IOException {
        String name = file.getCanonicalPath().substring(baseName.length() + 1);

        if (file.isDirectory()) {
            name += '/';
        }

        return name.replaceAll("\\\\", "/");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nonnull File inflate(@Nonnull final File... directories) throws IOException {
        Assert.checkNotNull(directories, "directories");
        O outStream = createArchiveOutputStream(new BufferedOutputStream(new FileOutputStream(archiveFile)));

        try {
            for (File directory : directories) {
                String baseName = directory.getCanonicalPath();
                addFiles(baseName, outStream, directory);
            }
        } finally {
            outStream.close();
        }

        return archiveFile;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public File inflate(final File file) throws IOException {
        if (file.isDirectory()) {
            inflate(new File[] { file });
        } else {
            O outStream = createArchiveOutputStream(new BufferedOutputStream(new FileOutputStream(archiveFile)));
            E entry = createArchiveEntry(file.getName(), file);

            outStream.putArchiveEntry(entry);

            InputStream inputStream = new FileInputStream(file);
            try {
                IOUtils.copy(inputStream, outStream);
            } finally {
                try {
                    outStream.closeArchiveEntry();
                } finally {
                    org.apache.commons.io.IOUtils.closeQuietly(outStream);
                    org.apache.commons.io.IOUtils.closeQuietly(inputStream);
                }

            }
        }

        return archiveFile;
    }
}
