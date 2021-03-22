package com.xiyan.logback;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * @author bright
 *
 */
@SuppressWarnings("deprecation")
public class IPLogConfig extends ClassicConverter {

    private static final Logger logger = LoggerFactory.getLogger(IPLogConfig.class);
    private static String webPort;
	
    static {
        try {
            List<MBeanServer> serverList = MBeanServerFactory.findMBeanServer(null);
            for (MBeanServer server : serverList) {
                Set<ObjectName> names = new HashSet<ObjectName>();
                names.addAll(server.queryNames(new ObjectName("Catalina:type=Connector,*"), null));
                Iterator<ObjectName> it = names.iterator();
                while (it.hasNext()) {
                    ObjectName oName = (ObjectName) it.next();
                    String pValue = (String) server.getAttribute(oName, "protocol");
                    if (StringUtils.equals("HTTP/1.1", pValue)) {
                        webPort = ObjectUtils.toString(server.getAttribute(oName, "port"));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("获取port失败,影响logback的文件拼接", e);
            webPort = null;
        }
    }
    
    @Override
    public String convert(ILoggingEvent event) {
        try {
        	if(webPort == null) {
        		return InetAddress.getLocalHost().getHostAddress();
        	}
        	return InetAddress.getLocalHost().getHostAddress() + ":" + webPort;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }
}
