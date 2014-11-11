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
 *
 * @author devacfr<christophefriederich@mac.com>
 * @since1.0
 */
public class LoggingOutputStream extends OutputStream {

    /**
     * The default number of bytes in the buffer.
     */
    public static final int DEFAULT_BUFFER_LENGTH = 2048;

    /**
     *
     */
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * Used to maintain the contract of {@link #close()}.
     */
    private boolean hasBeenClosed;

    /**
     * The internal buffer where data is stored.
     */
    private byte[] buffer;

    /**
     * The number of valid bytes in the buffer. This value is always in the range <tt>0</tt> through <tt>buf.length</tt>
     * ; elements <tt>buf[0]</tt> through <tt>buf[count-1]</tt> contain valid byte data.
     */
    private int count;

    /**
     * Remembers the size of the buffer for speed.
     */
    private int bufferLength;

    /**
     * The logger to write to.
     */
    @SuppressWarnings("PMD.LoggerIsNotStaticFinal")
    private final Logger logger;

    /**
     * The priority to use when writing to the Category.
     */
    private final Priority priority;

    /**
     * The last framework class for Log4j. Log4j generates a stack trace and uses the first entry after the named class
     * as the location in a log entry.
     */
    private final String lastFrameworkClassName;

    /**
     * Creates the LoggingOutputStream to flush to the given Category.
     *
     * @param lastFrameworkClass
     *            The last class for log4j to ignore in a stack trace.
     * @param log
     *            The Logger to write to.
     * @param priority
     *            The Priority to use when writing to the Logger.
     */
    public LoggingOutputStream(final Class<?> lastFrameworkClass, final Logger log, final Priority priority) {
        buffer = new byte[2048];
        bufferLength = buffer.length;
        Assert.notNull(lastFrameworkClass, "lastFrameworkClass cannot be null");
        Assert.notNull(log, "log cannot be null");
        Assert.notNull(priority, "priority cannot be null");
        this.priority = priority;
        logger = log;
        lastFrameworkClassName = lastFrameworkClass.getName();
    }

    /**
     * Closes this output stream and releases any system resources associated with this stream. The general contract of
     * <code>close</code> is that it closes the output stream. A closed stream cannot perform output operations and
     * cannot be reopened.
     */
    @Override
    public void close() {
        flush();
        hasBeenClosed = true;
    }

    /**
     * Flushes this output stream and forces any buffered output bytes to be written out. The general contract of
     * <code>flush</code> is that calling it is an indication that, if any bytes previously written have been buffered
     * by the implementation of the output stream, such bytes should immediately be written to their intended
     * destination.
     */
    @Override
    @SuppressWarnings("CheckStyle")
    public void flush() {
        if (count == 0) {
            return;
        }
        // TODO [devacfr] add test before simplify condition
        if (count == LINE_SEPARATOR.length() && (char) buffer[0] == LINE_SEPARATOR.charAt(0)
                && (count == 1 || count == 2 && (char) buffer[1] == LINE_SEPARATOR.charAt(1))) {
            reset();
            return;
        } else {
            logger.log(lastFrameworkClassName, priority, new String(buffer, 0, count), null);
            reset();
            return;
        }
    }

    /**
     * Gets the number of valid bytes in the buffer.
     *
     * @return return the number of valid bytes in the buffer.
     */
    public int getCount() {
        return count;
    }

    /**
     * Gets the last class for log4j to ignore in a stack trace.
     *
     * @return Returns the last class for log4j to ignore in a stack trace.
     */
    public String getLastFrameworkClassName() {
        return lastFrameworkClassName;
    }

    /**
     * Gets the logger to write to.
     *
     * @return Returns the logger to write to.
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * Gets the priority to use when writing to the Category.
     *
     * @return Returns the priority to use when writing to the Category.
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * clear the buffer.
     */
    private void reset() {
        count = 0;
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
    public void write(final int b) throws IOException {
        if (hasBeenClosed) {
            throw new IOException("The stream has been closed.");
        }
        if (b == 0) {
            return;
        }
        if (count == bufferLength) {
            int newBufLength = bufferLength + 2048;
            byte[] newBuf = new byte[newBufLength];
            System.arraycopy(buffer, 0, newBuf, 0, bufferLength);
            buffer = newBuf;
            bufferLength = buffer.length;
        }
        buffer[count++] = (byte) b;
    }

}