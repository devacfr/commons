package org.cfr.commons.util.compress;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.commons.io.FileUtils;
import org.cfr.commons.util.log.Log4jConfigurer;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RunWith(BlockJUnit4ClassRunner.class)
public abstract class AbstractJunitTest {

    protected static final File WORK_HOME = new File(getBasedir(), "target/work");

    private static String basedir;

    static {
        try {
            Log4jConfigurer.initLogging("classpath:log4j.xml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getBasedir() {
        if (basedir != null) {
            return basedir;
        }

        basedir = System.getProperty("basedir");

        if (basedir == null) {
            basedir = new File("").getAbsolutePath();
        }

        return basedir;
    }

    @BeforeClass
    public static void init() throws Exception {
        basedir = getBasedir();
    }

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before
    public void setUp() throws Exception {
        FileUtils.deleteDirectory(WORK_HOME);
        WORK_HOME.mkdirs();
    }
}
