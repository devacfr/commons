package org.cfr.commons.util.compress.tar;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.cfr.commons.util.compress.AbstractArchiveExtractor;
import org.springframework.core.io.Resource;


/**
 * Helper class to extract TAR archive.
 * 
 * @author acochard [Jul 30, 2009]
 */
public class TarExtractor extends AbstractArchiveExtractor<TarArchiveInputStream> {

    public TarExtractor(Resource archiveFile) throws FileNotFoundException {
        super(archiveFile);
    }

    @Override
    protected TarArchiveInputStream createArchiveInputStream(InputStream fileInputStream) {
        return new TarArchiveInputStream(fileInputStream);
    }
}