/*
 * Copyright (c) 2004, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

import clientRMI.CpFiles;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.StandardMBean;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import javax.management.remote.rmi.RMIConnectorServer;
import javax.rmi.ssl.SslRMIClientSocketFactory;
import javax.rmi.ssl.SslRMIServerSocketFactory;
import root.IHandler;
import serverRMI.Handler;
import serverRMI.Invoker;
import java.util.Properties;


public class Server {

    public static void main(String[] args) {
        try {
            // start rmi registry
            System.out.println("\nStart rmi registry specify path and port is not required");            
            String path = null;
            String port = null;
            String host = null;
            String defaultHost = "localhost";
            String defaultPort = "9999";
            if (args.length==2){
                path = args[1];
                port = defaultPort;
                host = defaultHost;
            }else if(args.length==2){
                path = args[2];
                port = args[3];
                host = defaultHost;
            }else if(args.length==1){
                path = "rmiregistry";
                port = defaultPort;
                host = defaultHost;
            }else if (args.length==4){
                path = args[1];
                port = args[2];
                host = args[3];
            }
            System.out.println("\n take command: "+path+" "+port);            
            //TODO uncomment for jre7
            /*
            if (path!=null)
                Runtime.getRuntime().exec(getFile(path)+" "+port, getEnv(), new File(getDir(path)));
            else
                System.out.println("\nRmi server is not started");
            */            
            Runtime.getRuntime().exec(delQutos(path+" "+port));
 /*           String[] s = getEnv();
            for (int i = 0; i < s.length; i++) {
                String arg = s[i];
                System.out.print(arg+" \n");
            }
   */          
           
            // Instantiate the MBean server
            //
            System.out.println("\nCreate the MBean server");
            MBeanServer mbs = MBeanServerFactory.createMBeanServer();
             
            // register bean
            Handler handler = new Handler();
            StandardMBean mbean = new StandardMBean(handler,IHandler.class); // or use 
            mbs.registerMBean(mbean, new ObjectName("root:type=Handler"));

            // Environment map
            //
            System.out.println("\nInitialize the environment map");
            HashMap env = new HashMap();

            // Provide SSL-based RMI socket factories.
            //
            SslRMIClientSocketFactory csf = new SslRMIClientSocketFactory();
            SslRMIServerSocketFactory ssf = new SslRMIServerSocketFactory();
            env.put(RMIConnectorServer.RMI_CLIENT_SOCKET_FACTORY_ATTRIBUTE,csf);
            env.put(RMIConnectorServer.RMI_SERVER_SOCKET_FACTORY_ATTRIBUTE,ssf);

            // Provide the password file used by the connector server to
            // perform user authentication. The password file is a properties
            // based text file specifying username/password pairs. This
            // properties based password authenticator has been implemented
            // using the JMXAuthenticator interface and is passed to the
            // connector through the "jmx.remote.authenticator" property
            // in the map.
            //
            // This property is implementation-dependent and might not be
            // supported by all implementations of the JMX Remote API.
            //
            env.put("jmx.remote.x.password.file",
                    "config" + File.separator + "password.properties");

            // Provide the access level file used by the connector server to
            // perform user authorization. The access level file is a properties
            // based text file specifying username/access level pairs where
            // access level is either "readonly" or "readwrite" access to the
            // MBeanServer operations. This properties based access control
            // checker has been implemented using the MBeanServerForwarder
            // interface which wraps the real MBean server inside an access
            // controller MBean server which performs the access control checks
            // before forwarding the requests to the real MBean server.
            //
            // This property is implementation-dependent and might not be
            // supported by all implementations of the JMX Remote API.
            //
            env.put("jmx.remote.x.access.file",
                    "config" + File.separator + "access.properties");

            // Create an RMI connector server
            //
            System.out.println("\nCreate an RMI connector server: host is "+host+" port is "+port);
            JMXServiceURL url = new JMXServiceURL(
              "service:jmx:rmi:///jndi/rmi://"+host+":"+port+"/server");
            JMXConnectorServer cs =
                JMXConnectorServerFactory.newJMXConnectorServer(url, env, mbs);
            
            // Start the RMI connector server
            //
            System.out.println("\nStart the RMI connector server");
            cs.start();
            System.out.println("\nRMI connector server successfully started");
            System.out.println("\nWaiting for incoming connections...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void exec(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private static String getFile(String path){
        return path.substring(path.lastIndexOf("\\")+1, path.length());
    }
    private static String getDir(String path){
        if (path.startsWith("/"))
            return path.substring(0, path.lastIndexOf("/")); //for linux 
        return path.substring(0, path.lastIndexOf("\\"));
        
    }
    private static String[] getEnv() throws FileNotFoundException, IOException{            
//           System.out.println("saved prop in xml");
//                        Properties p = new Properties();
            //p.putAll(System.getenv());
            ////p.putAll(cs.getAttributes());
            //p.store(new FileOutputStream("input.xml"), "jdk 1.8");
            ////p.storeToXML(new FileOutputStream("input.xml"), "jdk 1.8");//do not use
           
//            p.load(new FileInputStream("prop6.xml"));//any is ok
//System.setProperties(p);
//                        System.setProperty("java.home","/usr/local/java/bin");//ok
            return System.getenv().toString().replace("{", "").replace("}", "").split(",");
    }
    private static String delQutos(String str){
        return str.replace("\"", "");
    }
}
