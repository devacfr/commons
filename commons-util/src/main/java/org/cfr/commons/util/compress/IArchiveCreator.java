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
     * @param directories the directories all files are zipped within
     * @return the zipped file.
     * @throws IOException if an io exception occures.
     */
    File inflate(File... directories) throws IOException;

    /**
     * Inflate the specified file.
     * 
     * @param file the file to inflate.
     * @return the inflated file.
     * @throws IOException if an io exception occures.
     */
    File inflate(File file) throws IOException;
}
