package org.cfr.commons.util.compress.gzip;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.cfr.commons.util.ResourceUtils;
import org.cfr.commons.util.compress.AbstractJunitTest;
import org.cfr.commons.util.compress.IArchiveCreator;
import org.junit.Test;

public class GzipCreatorTest extends AbstractJunitTest {

    @Test
    public void testInflateFile() throws Exception {
        File source = ResourceUtils.getFile("classpath:org/cfr/commons/util/compress/gzip/testgzip.txt");
        File destination = new File(WORK_HOME, "testgzip.txt.gz");
        IArchiveCreator creator = new GzipCreator(destination);
        creator.inflate(source);
        assertTrue(destination.exists());
    }
}
