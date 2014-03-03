package org.cfr.commons.util.log;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.cfr.commons.util.log.LoggingOutputStream;
import org.easymock.EasyMock;
import org.easymock.IArgumentMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public final class LoggingOutputStreamTest {

    private static final class LoggingEventStringMatcher implements IArgumentMatcher {

        private final StringBuilder fSb;

        private LoggingEventStringMatcher(StringBuilder sb) {
            fSb = sb;
        }

        @Override
        public void appendTo(StringBuffer buffer) {
            buffer.append("(" + fSb.toString() + ")");
        }

        @Override
        public boolean matches(Object argument) {
            return fSb.toString().equals(((LoggingEvent) argument).getMessage());
        }

    }

    private Logger fLOG;

    private LoggingOutputStream fLoggingOutputStream;

    //    private MockControl<Appender> fAppenderControl;

    private Appender fAppenderMock;

    private static final Class<?> LAST_FRAMEWORK_CLASS;

    private static final Level TEST_PRIORITY;

    static {
        LAST_FRAMEWORK_CLASS = org.apache.log4j.Logger.class;
        TEST_PRIORITY = Level.INFO;
    }

    public LoggingOutputStreamTest() {
    }

    @Before
    public void setUp() throws Exception {
        //        fAppenderControl = MockControl.createControl(Appender.class);
        fAppenderMock = EasyMock.createMock(Appender.class);
        fLOG = Logger.getLogger(LoggingOutputStreamTest.class);
        fLOG.removeAllAppenders();
        fLOG.addAppender(fAppenderMock);
        fLOG.setLevel(TEST_PRIORITY);
        fLoggingOutputStream = new LoggingOutputStream(LAST_FRAMEWORK_CLASS, fLOG, TEST_PRIORITY);
    }

    @After
    public void tearDown() throws Exception {
        EasyMock.reset(fAppenderMock);
    }

    @Test(expected = IOException.class)
    public void testClose() throws Exception {
        StringBuilder sb = new StringBuilder("foo");
        EasyMock.reportMatcher(new LoggingEventStringMatcher(sb));
        fAppenderMock.doAppend(new LoggingEvent(LAST_FRAMEWORK_CLASS.getName(), fLOG, TEST_PRIORITY, "foo",
                null));
        EasyMock.replay(fAppenderMock);

        fLoggingOutputStream.write(sb.toString().getBytes());
        fLoggingOutputStream.close();
        EasyMock.verify(fAppenderMock);

        fLoggingOutputStream.write(32);
    }

    @Test
    public void testFlushSkipsEmptyLine() throws Exception {
        EasyMock.replay(fAppenderMock);
        fLoggingOutputStream.write(LoggingOutputStream.LINE_SEPARATOR.getBytes());
        fLoggingOutputStream.flush();
        EasyMock.verify(fAppenderMock);
    }

    @Test
    public void testFlushSkipsOnlyEmptyLine() throws IOException {
        LoggingOutputStream loggingOutputStream = null;
        try {
            loggingOutputStream = new LoggingOutputStream(LAST_FRAMEWORK_CLASS, Logger.getRootLogger(),
                    TEST_PRIORITY);
            byte testBytes[] = LoggingOutputStream.LINE_SEPARATOR.getBytes();
            for (int i = 0; i < testBytes.length; i++)
                testBytes[i] = 120;

            loggingOutputStream.write(testBytes);
            assertEquals(LoggingOutputStream.LINE_SEPARATOR.length(), loggingOutputStream.getCount());
            loggingOutputStream.flush();
            assertEquals(0, loggingOutputStream.getCount());
        } finally {
            IOUtils.closeQuietly(loggingOutputStream);
        }
    }

    @Test
    public void testLoggingEventStringMatcher() {
        StringBuilder sb = new StringBuilder();
        Object arguments = new LoggingEvent(LAST_FRAMEWORK_CLASS.getName(), fLOG, TEST_PRIORITY, "message",
                null);
        LoggingEventStringMatcher lesm = new LoggingEventStringMatcher(sb);
        assertFalse(lesm.matches(arguments));
        sb.append("message");
        assertTrue(lesm.matches(arguments));
    }

    @Test(expected = RuntimeException.class)
    public void testLoggingOutputStream1() throws Exception {
        LoggingOutputStream loggingOutputStream = null;
        try {
            loggingOutputStream = new LoggingOutputStream(null, fLOG, LoggingOutputStreamTest.TEST_PRIORITY);
        } finally {
            IOUtils.closeQuietly(loggingOutputStream);
        }
    }

    @Test(expected = RuntimeException.class)
    public void testLoggingOutputStream2() throws Exception {
        LoggingOutputStream loggingOutputStream = null;
        try {
            loggingOutputStream = new LoggingOutputStream(LoggingOutputStreamTest.LAST_FRAMEWORK_CLASS, null,
                    LoggingOutputStreamTest.TEST_PRIORITY);
        } finally {
            IOUtils.closeQuietly(loggingOutputStream);
        }
    }

    @Test(expected = RuntimeException.class)
    public void testLoggingOutputStream3() throws Exception {
        LoggingOutputStream loggingOutputStream = null;
        try {
            loggingOutputStream = new LoggingOutputStream(LoggingOutputStreamTest.LAST_FRAMEWORK_CLASS,
                    Logger.getRootLogger(), null);
        } finally {
            IOUtils.closeQuietly(loggingOutputStream);
        }
    }

    @Test
    public void testLoggingOutputStream4() throws Exception {
        assertEquals(fLoggingOutputStream.getLastFrameworkClassName(), LAST_FRAMEWORK_CLASS.getName());
        assertEquals(fLoggingOutputStream.getLogger(), fLOG);
        assertEquals(fLoggingOutputStream.getPriority(), TEST_PRIORITY);
    }

    @Test
    public void testWriteGrowsBuffer() throws Exception {
        StringBuilder sb = new StringBuilder(2049);
        EasyMock.reportMatcher(new LoggingEventStringMatcher(sb));
        fAppenderMock.doAppend(new LoggingEvent(LAST_FRAMEWORK_CLASS.getName(), fLOG, TEST_PRIORITY, null,
                null));
        EasyMock.replay(fAppenderMock);
        assertEquals(0, fLoggingOutputStream.getCount());
        writeAndStoreACharacter(sb, 'A');
        for (int i = 1; i < 2048; i++)
            writeAndStoreACharacter(sb, 'B');

        writeAndStoreACharacter(sb, 'C');
        assertEquals(2049, fLoggingOutputStream.getCount());
        fLoggingOutputStream.flush();
        EasyMock.verify(fAppenderMock);
    }

    @Test
    public void testWriteNull() throws Exception {
        EasyMock.replay(fAppenderMock);
        assertEquals(0, fLoggingOutputStream.getCount());
        fLoggingOutputStream.write(0);
        assertEquals(0, fLoggingOutputStream.getCount());
        EasyMock.verify(fAppenderMock);
    }

    private void writeAndStoreACharacter(StringBuilder sb, char b) throws IOException {
        fLoggingOutputStream.write(b);
        sb.append(b);
    }

}
