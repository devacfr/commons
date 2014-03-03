package org.cfr.commons.util.compress.zip;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.cfr.commons.util.compress.AbstractJunitTest;
import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

public class ZipExtractorTest extends AbstractJunitTest {

    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    @Test
    public void testDeflateFile() throws Exception {
        File destination = WORK_HOME;

        ZipExtractor extractor = new ZipExtractor(
                resourceLoader.getResource("classpath:org/cfr/commons/util/compress/zip/testzip.zip"));
        extractor.deflate(destination);

        assertTrue(new File(destination, "root.txt").exists());
        assertTrue(new File(destination, "sub-folder/sub-folder.tst").exists());
        new File(destination, "root.txt").delete();
        new File(destination, "sub-folder/sub-folder.tst").delete();
        new File(destination, "sub-folder").delete();
    }

    @Test
    public void testDeflateFileWithPattern() throws Exception {
        File destination = WORK_HOME;

        ZipExtractor extractor = new ZipExtractor(
                resourceLoader.getResource("classpath:org/cfr/commons/util/compress/zip/testzip.zip"));
        extractor.deflate(destination, "*.txt");

        assertTrue(new File(destination, "root.txt").exists());
        assertFalse(new File(destination, "sub-folder/sub-folder.tst").exists());
        new File(destination, "root.txt").delete();
        new File(destination, "sub-folder").delete();
    }

    @Test
    public void testDeflateFileWithPatternAndFlatMode() throws Exception {
        File destination = WORK_HOME;

        ZipExtractor extractor = new ZipExtractor(
                resourceLoader.getResource("classpath:org/cfr/commons/util/compress/zip/testzip.zip"));
        extractor.deflate(destination, "*.tst", true);

        assertTrue(new File(destination, "sub-folder.tst").exists());
        assertFalse(new File(destination, "root.txt").exists());
        assertFalse(new File(destination, "sub-folder").exists());
        assertFalse(new File(destination, "sub-folder/sub-folder.tst").exists());
        new File(destination, "sub-folder.tst").delete();
    }
}
