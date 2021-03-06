package org.cfr.commons.util.compress.gzip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import org.cfr.commons.util.compress.IArchiveCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Helper class to create GZIP archive.
 *
 * @author acochard
 */
public class GzipCreator implements IArchiveCreator {

    private static final int BUFFER_LENGTH = 1024;

    private static final Logger LOGGER = LoggerFactory.getLogger(GzipCreator.class);

    /**
     * Archive file.
     */
    private final File archiveFile;

    /**
     * Create a new gzip creator with the given file as backend.
     *
     * @param archiveFile the gzip archive file.
     */
    public GzipCreator(File archiveFile) {
        this.archiveFile = archiveFile;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public File getArchiveFile() {
        return archiveFile;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public File inflate(File... directories) throws IOException {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public File inflate(File file) throws IOException {
        BufferedInputStream fileInput = new BufferedInputStream(new FileInputStream(file));
        GZIPOutputStream fileOutput = new GZIPOutputStream(new BufferedOutputStream(new FileOutputStream(archiveFile)));

        LOGGER.info("Inflating file: '" + file.getAbsolutePath() + "'");

        // Inflate
        byte[] buffer = new byte[BUFFER_LENGTH];
        int count;
        try {
            while ((count = fileInput.read(buffer, 0, BUFFER_LENGTH)) > -1) {
                fileOutput.write(buffer, 0, count);
            }
        } finally {
            // Close output stream
            if (fileOutput != null) {
                fileOutput.close();
            }
            // Close input stream
            if (fileInput != null) {
                fileInput.close();
            }
        }

        return archiveFile;
    }
}
