package org.cfr.commons.util.compress.tar;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.cfr.commons.util.ResourceUtils;
import org.cfr.commons.util.compress.AbstractJunitTest;
import org.cfr.commons.util.compress.IArchiveCreator;
import org.junit.Test;

public class TarCreatorTest extends AbstractJunitTest {

    @Test
    public void testInflateFile() throws Exception {
        File source = ResourceUtils.getFile("classpath:org/cfr/commons/util/compress/tar/testtar.txt");
        File destination = new File(WORK_HOME, "testzip.txt.tar");
        IArchiveCreator creator = new TarCreator(destination);
        creator.inflate(source);
        assertTrue(destination.exists());
        assertTrue(destination.length() > 0);
    }
}
