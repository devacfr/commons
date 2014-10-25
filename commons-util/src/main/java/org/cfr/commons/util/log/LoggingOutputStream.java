/**
 * Copyright 2014 devacfr<christophefriederich@mac.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cfr.commons.util.log;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.cfr.commons.util.Assert;

/**
 * An OutputStream that flushes out to a Logger.
 * <p>
 * Note that no data is written out to the logger until the stream is flushed or closed.
 * <p>
 * Example:
 * 
 * <pre>
 * // make sure everything sent to System.err is logged
 * System.setErr(new PrintStream(new LoggingOutputStream(Category.getRoot(), Priority.WARN), true));
 * // make sure everything sent to System.out is also logged
 * System.setOut(new PrintStream(new LoggingOutputStream(Category.getRoot(), Priority.INFO), true));
 * </pre>
 */
public class LoggingOutputStream extends OutputStream {

    /**
     * 
     */
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * Used to maintain the contract of {@link #close()}.
     */
    private boolean fHasBeenClosed;

    /**
     * The internal buffer where data is stored.
     */
    private byte[] fBuf;

    /**
     * The number of valid bytes in the buffer. This value is always in the range <tt>0</tt> through <tt>buf.length</tt>
     * ; elements <tt>buf[0]</tt> through <tt>buf[count-1]</tt> contain valid byte data.
     */
    private int fCount;

    /**
     * Remembers the size of the buffer for speed.
     */
    private int fBufLength;

    /**
     * The default number of bytes in the buffer.
     */
    public static final int DEFAULT_BUFFER_LENGTH = 2048;

    /**
     * The logger to write to.
     */
    private final Logger fLogger;

    /**
     * The priority to use when writing to the Category.
     */
    private final Priority fPriority;

    /**
     * The last framework class for Log4j. Log4j generates a stack trace and uses the first entry after the named class
     * as the location in a log entry.
     */
    private final String fLastFrameworkClassName;

    /**
     * Creates the LoggingOutputStream to flush to the given Category.
     *
     * @param lastFrameworkClass
     *            The last class for log4j to ignore in a stack trace.
     * @param log
     *            The Logger to write to.
     * @param priority
     *            The Priority to use when writing to the Logger.
     * @throws IllegalArgumentException
     *             if one of the argument is null.
     */
    public LoggingOutputStream(Class<?> lastFrameworkClass, Logger log, Priority priority) {
        fBuf = new byte[2048];
        fBufLength = fBuf.length;
        Assert.notNull(lastFrameworkClass, "lastFrameworkClass cannot be null");
        Assert.notNull(log, "log cannot be null");
        Assert.notNull(priority, "priority cannot be null");
        fPriority = priority;
        fLogger = log;
        fLastFrameworkClassName = lastFrameworkClass.getName();
    }

    /**
     * Closes this output stream and releases any system resources associated with this stream. The general contract of
     * <code>close</code> is that it closes the output stream. A closed stream cannot perform output operations and
     * cannot be reopened.
     */
    @Override
    public void close() {
        flush();
        fHasBeenClosed = true;
    }

    /**
     * Flushes this output stream and forces any buffered output bytes to be written out. The general contract of
     * <code>flush</code> is that calling it is an indication that, if any bytes previously written have been buffered
     * by the implementation of the output stream, such bytes should immediately be written to their intended
     * destination.
     */
    @Override
    public void flush() {
        if (fCount == 0)
            return;
        if (fCount == LINE_SEPARATOR.length() && (char) fBuf[0] == LINE_SEPARATOR.charAt(0)
                && (fCount == 1 || fCount == 2 && (char) fBuf[1] == LINE_SEPARATOR.charAt(1))) {
            reset();
            return;
        } else {
            fLogger.log(fLastFrameworkClassName, fPriority, new String(fBuf, 0, fCount), null);
            reset();
            return;
        }
    }

    /**
     * Gets the number of valid bytes in the buffer.
     * 
     * @return return the number of valid bytes in the buffer.
     */
    int getCount() {
        return fCount;
    }

    /**
     * Gets the last class for log4j to ignore in a stack trace.
     * 
     * @return Returns the last class for log4j to ignore in a stack trace.
     */
    String getLastFrameworkClassName() {
        return fLastFrameworkClassName;
    }

    /**
     * Gets the logger to write to.
     * 
     * @return Returns the logger to write to.
     */
    Logger getLogger() {
        return fLogger;
    }

    /**
     * Gets the priority to use when writing to the Category.
     * 
     * @return Returns the priority to use when writing to the Category.
     */
    Priority getPriority() {
        return fPriority;
    }

    /**
     * clear the buffer.
     *
     */
    private void reset() {
        fCount = 0;
    }

    /**
     * Writes the specified byte to this output stream. The general contract for <code>write</code> is that one byte is
     * written to the output stream. The byte to be written is the eight low-order bits of the argument <code>b</code>.
     * The 24 high-order bits of <code>b</code> are ignored.
     *
     * @param b
     *            the <code>byte</code> to write
     * @throws IOException
     *             if an I/O error occurs. In particular, an <code>IOException</code> may be thrown if the output stream
     *             has been closed.
     */
    @Override
    public void write(int b) throws IOException {
        if (fHasBeenClosed)
            throw new IOException("The stream has been closed.");
        if (b == 0)
            return;
        if (fCount == fBufLength) {
            int newBufLength = fBufLength + 2048;
            byte newBuf[] = new byte[newBufLength];
            System.arraycopy(fBuf, 0, newBuf, 0, fBufLength);
            fBuf = newBuf;
            fBufLength = fBuf.length;
        }
        fBuf[fCount++] = (byte) b;
    }

}