package com.agoda.common.helper;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Custom class to calculate output stream written so far.
 */
public final class CountOutputStream extends FilterOutputStream {

    private long count;
    private static final Logger LOGGER = LoggerFactory.getLogger(CountOutputStream.class);

    public CountOutputStream(OutputStream out) {
        super(out);
    }

    public long getCount() {
        return count;
    }

    @Override
    public synchronized void write(byte[] b, int off, int len) throws IOException {
        out.write(b, off, len);
        count += len;
    }

    @Override
    public synchronized void write(int b) throws IOException {
        out.write(b);
        count++;
    }


    @Override
    public void close() throws IOException {
        out.close();
    }
}