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