package org.cfr.commons.util.compress.zip;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.cfr.commons.util.compress.AbstractArchiveExtractor;
import org.springframework.core.io.Resource;


/**
 * Helper class to extract ZIP archive.
 * 
 * @author acochard [Jul 30, 2009]
 */
public class ZipExtractor extends AbstractArchiveExtractor<ZipArchiveInputStream> {

    public ZipExtractor(Resource archiveFile) throws FileNotFoundException {
        super(archiveFile);
    }

    @Override
    protected ZipArchiveInputStream createArchiveInputStream(InputStream fileInputStream) {
        return new ZipArchiveInputStream(fileInputStream);
    }

}