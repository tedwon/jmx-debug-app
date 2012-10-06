package com.tedwon.jmx;

import javax.management.AttributeChangeNotification;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * JMX MBean Debugger Class.
 * <p/>
 * java -jar jmx-debugger.jar %jmx_port%
 *
 * @author <a href=mailto:iamtedwon@gmail.com">Ted Won</a>
 * @version 0.1
 */
public class JMXDebugger {

    public static void main(String[] args) throws Exception {

        String jmxHost = "127.0.0.1";
        String jmxPort = "9999";

        if (args != null && args.length == 1) {
            jmxPort = args[0];
        }

        if (args != null && args.length == 2) {
            jmxHost = args[0];
            jmxPort = args[1];
        }

        // Create an RMI connector client and
        // connect it to the RMI connector server
        String URL = "service:jmx:rmi:///jndi/rmi://" + jmxHost + ":" + jmxPort + "/jmxrmi";
        System.out.println(URL);
        JMXServiceURL url =
                new JMXServiceURL(URL);
        JMXConnector jmxc = JMXConnectorFactory.connect(url, null);

        System.out.println("\nCreated an RMI connector client and " +
                "connect it to the RMI connector server successfully");


        // Create listener
        //
        ClientListener listener = new ClientListener();

        // Get an MBeanServerConnection
        //
        System.out.println("\nGet an MBeanServerConnection");
        MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
        waitForEnterPressed();

        // Get domains from MBeanServer
        //
        System.out.println("\nDomains:");
        String domains[] = mbsc.getDomains();
        Arrays.sort(domains);
        for (String domain : domains) {
            System.out.println("\tDomain = " + domain);
        }
        waitForEnterPressed();

        // Get MBeanServer's default domain
        //
        System.out.println("\nMBeanServer default domain = " + mbsc.getDefaultDomain());

        // Get MBean count
        //
        System.out.println("\nMBean count = " + mbsc.getMBeanCount());

        // Query MBean names
        //
        System.out.println("\nQuery MBeanServer MBeans:\n");
        Set<ObjectName> names =
                new TreeSet<ObjectName>(mbsc.queryNames(null, null));
//        Set<ObjectName> names =
//                new TreeSet<ObjectName>(mbsc.queryNames(new ObjectName("com.example:type=*"), null));
//        Set<ObjectInstance> names =
//                new TreeSet<ObjectInstance>(mbsc.queryMBeans(new ObjectName("com.example:type=*"), null));


        for (ObjectName name : names) {
            System.out.println("================================");
            System.out.println("MBeanInfo");
            System.out.println("\tObjectName= " + name);

            MBeanInfo mBeanInfo = mbsc.getMBeanInfo(name);
            MBeanAttributeInfo[] attributes = mBeanInfo.getAttributes();
            for (MBeanAttributeInfo attribute : attributes) {
                String attributeName = attribute.getName();
                String type = attribute.getType();
                String description = attribute.getDescription();

                System.out.println("MBeanAttributeInfo");
                System.out.println("\tName:" + attributeName);
                System.out.println("\tType:" + type);
                System.out.println("\tDescription:" + description);
            }

//            MBeanOperationInfo[] operations = mBeanInfo.getOperations();
//            for(MBeanOperationInfo operation : operations) {
//                operation.getReturnType();
//                operation.getSignature();
//                operation.getName();
//            }
        }


    }

    /**
     * Inner class that will handle the notifications.
     */
    public static class ClientListener implements NotificationListener {
        public void handleNotification(Notification notification,
                                       Object handback) {
            System.out.println("\nReceived notification:");
            System.out.println("\tClassName: " + notification.getClass().getName());
            System.out.println("\tSource: " + notification.getSource());
            System.out.println("\tType: " + notification.getType());
            System.out.println("\tMessage: " + notification.getMessage());
            if (notification instanceof AttributeChangeNotification) {
                AttributeChangeNotification acn =
                        (AttributeChangeNotification) notification;
                System.out.println("\tAttributeName: " + acn.getAttributeName());
                System.out.println("\tAttributeType: " + acn.getAttributeType());
                System.out.println("\tNewValue: " + acn.getNewValue());
                System.out.println("\tOldValue: " + acn.getOldValue());
            }
        }
    }

    private static void waitForEnterPressed() {
        try {
            System.out.println("\nPress <Enter> to continue...");
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
