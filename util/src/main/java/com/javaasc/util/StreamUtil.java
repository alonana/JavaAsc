package com.javaasc.util;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.io.OutputStream;

public class StreamUtil {
    public static void copy(InputStream in, OutputStream out) throws Exception {
        IOUtils.copy(in, out);
        in.close();
        out.close();
    }

    public static String loadToString(InputStream in) throws Exception {
        return IOUtils.toString(in);
    }

    public static void closeSafe(InputStream inputStream) throws Exception {
        if (inputStream!= null){
            inputStream.close();
        }
    }
    public static void closeSafe(OutputStream outputStream) throws Exception {
        if (outputStream!= null){
            outputStream.close();
        }
    }
}
