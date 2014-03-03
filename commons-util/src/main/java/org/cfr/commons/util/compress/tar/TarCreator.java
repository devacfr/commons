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
 */
public class TarCreator extends AbstractArchiveCreator<TarArchiveOutputStream, TarArchiveEntry> {

    public TarCreator(File archiveFile) {
        super(archiveFile);
    }

    @Override
    protected TarArchiveEntry createArchiveEntry(String name, File file) {
        TarArchiveEntry entry = new TarArchiveEntry(name);
        entry.setSize(file.length());
        return entry;
    }

    @Override
    protected TarArchiveOutputStream createArchiveOutputStream(BufferedOutputStream stream) {
        return new TarArchiveOutputStream(stream);
    }
}
