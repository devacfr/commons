package org.cfr.commons.util.compress.tar;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.cfr.commons.util.compress.AbstractJunitTest;
import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

public class TarExtractorTest extends AbstractJunitTest {

    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    @Test
    public void testDeflateFile() throws Exception {
        File destination = WORK_HOME;

        TarExtractor extractor = new TarExtractor(
                resourceLoader.getResource("classpath:org/cfr/commons/util/compress/tar/testtar.tar"));
        extractor.deflate(destination);

        assertTrue(new File(destination, "root1.tst").exists());
        assertTrue(new File(destination, "root2.tst").exists());
        assertTrue(new File(destination, "sub-folder/sub1.tst").exists());
        assertTrue(new File(destination, "sub-folder/sub2.tst").exists());
        new File(destination, "root1.txt").delete();
        new File(destination, "root2.txt").delete();
        new File(destination, "sub-folder/sub1.tst").delete();
        new File(destination, "sub-folder/sub2.tst").delete();
        new File(destination, "sub-folder").delete();
    }

    @Test
    public void testDeflateFileWithPattern() throws Exception {
        File destination = WORK_HOME;

        TarExtractor extractor = new TarExtractor(
                resourceLoader.getResource("classpath:org/cfr/commons/util/compress/tar/testtar.tar"));
        extractor.deflate(destination, "root*.tst");

        assertTrue(new File(destination, "root1.tst").exists());
        assertTrue(new File(destination, "root2.tst").exists());
        assertFalse(new File(destination, "sub-folder/sub1.tst").exists());
        new File(destination, "root1.tst").delete();
        new File(destination, "root2.tst").delete();
        new File(destination, "sub-folder").delete();
    }

    @Test
    public void testDeflateFileWithPatternAndFlatMode() throws Exception {
        File destination = WORK_HOME;

        TarExtractor extractor = new TarExtractor(
                resourceLoader.getResource("classpath:org/cfr/commons/util/compress/tar/testtar.tar"));
        extractor.deflate(destination, "sub*.tst", true);

        assertTrue(new File(destination, "sub1.tst").exists());
        assertTrue(new File(destination, "sub2.tst").exists());
        assertFalse(new File(destination, "root1.tst").exists());
        assertFalse(new File(destination, "root2.tst").exists());
        assertFalse(new File(destination, "sub-folder").exists());
        assertFalse(new File(destination, "sub-folder/sub1.tst").exists());
        new File(destination, "sub-folder.tst").delete();
    }
}
