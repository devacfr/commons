package org.cfr.commons.util.compress.zip;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.cfr.commons.util.ResourceUtils;
import org.cfr.commons.util.compress.AbstractJunitTest;
import org.cfr.commons.util.compress.IArchiveCreator;
import org.junit.Test;

public class ZipCreatorTest extends AbstractJunitTest {

    @Test
    public void testInflateFile() throws Exception {
        File source = ResourceUtils.getFile("classpath:org/cfr/commons/util/compress/zip/testzip.txt");
        File destination = new File(WORK_HOME, "testzip.txt.zip");
        IArchiveCreator creator = new ZipCreator(destination);
        creator.inflate(source);
        assertTrue(destination.exists());
    }
}
