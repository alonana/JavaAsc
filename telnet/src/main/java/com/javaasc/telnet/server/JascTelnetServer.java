package com.javaasc.telnet.server;

import com.javaasc.util.ResourceUtil;
import net.wimpi.telnetd.net.PortListener;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class JascTelnetServer {
    public void start() throws Exception {
        Properties properties = new Properties();
        File file = ResourceUtil.getResourceAsFile("telnet.properties");
        properties.load(new FileInputStream(file));
        PortListener listener = PortListener.createPortListener("std", properties);
        listener.start();
    }
}

