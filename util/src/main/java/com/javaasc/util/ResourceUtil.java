package com.javaasc.util;

import java.io.File;
import java.net.URL;

public class ResourceUtil {

    public static File getResourceAsFile(String resourceName) throws Exception {
        ClassLoader classloader = ResourceUtil.class.getClassLoader();
        URL url = classloader.getResource(resourceName);
        if (url == null) {
            throw new JascException("resource " + resourceName + " not found");
        }

        return new File(url.getPath());
    }
}
