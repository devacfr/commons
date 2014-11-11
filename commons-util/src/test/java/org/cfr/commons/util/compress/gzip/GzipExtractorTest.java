package org.cfr.commons.util.compress.gzip;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.cfr.commons.io.DefaultResourceLoader;
import org.cfr.commons.io.IResourceLoader;
import org.cfr.commons.util.compress.AbstractJunitTest;
import org.cfr.commons.util.compress.IArchiveExtractor;
import org.junit.Test;

public class GzipExtractorTest extends AbstractJunitTest {

    private final IResourceLoader resourceLoader = new DefaultResourceLoader();

    @Test
    public void testExtract() throws Exception {
        File destination = new File(WORK_HOME, "testgzip.txt");
        IArchiveExtractor extractor =
                new GzipExtractor(
                        resourceLoader.getResource("classpath:org/cfr/commons/util/compress/gzip/testgzip.txt.gz"));
        extractor.deflate(destination);
        assertTrue(destination.exists());
    }
}
