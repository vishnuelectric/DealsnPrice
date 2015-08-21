package com.dnp.httpcall;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;

/**
 * Utilities for transferring data to or from streams.
 */
public class StreamUtils {

    /**
     * Read all bytes from given stream.
     * @param inputStream input stream to read all data from.
     * @return byte array of stream data.
     * @throws IOException If there is an IO error during read.
     */
    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int read;
        int totalRead = 0;
        try {
            while ((read = inputStream.read(buffer)) > 0) {
                if (Thread.currentThread().isInterrupted()) {
                    InterruptedIOException e = new InterruptedIOException("Thread interrupted.");
                    e.bytesTransferred = totalRead;
                    throw e;
                }
                bos.write(buffer, 0, read);
                totalRead += read;
            }
            bos.flush();
            return bos.toByteArray();
        } finally {
            bos.close();
        }
    }
}
