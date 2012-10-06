package com.tedwon.jmx;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * Sample JMX App Class.
 *
 * -Dcom.sun.management.jmxremote.port=9999 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false
 *
 * @author <a href=mailto:iamtedwon@gmail.com">Ted Won</a>
 * @version 0.1
 */
public class SampleJMXMain {

    public static void main(String[] args) throws Exception {

        // Get the Platform MBean Server
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

        // Construct the ObjectName for the Hello MBean we will register
        ObjectName mbeanName = new ObjectName("com.example:type=Hello");

        // Create the Hello World MBean
        Hello mbean = new Hello();

        // Register the Hello World MBean
        mbs.registerMBean(mbean, mbeanName);

        // Wait forever
        System.out.println("Waiting for incoming requests...");
        Thread.sleep(Long.MAX_VALUE);

    }
}
