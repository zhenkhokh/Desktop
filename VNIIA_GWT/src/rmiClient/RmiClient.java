package rmiClient;

import java.io.IOException;
import java.util.HashMap;
import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.ObjectName;
import javax.management.remote.JMXServiceURL;

import root.IHandler;

public class RmiClient{
	final String port="9999";
	private MBeanServerConnection mbsc;
	private IHandler proxy;
	public RmiClient(String ip) throws IOException, MalformedObjectNameException {
		System.out.println("domain is "+ip);
         HashMap env = new HashMap();
         String[] credentials = new String[] { "username" , "password" };
         env.put("jmx.remote.credentials", credentials);
         System.out.println("rmi env is set");
         JMXServiceURL url = new JMXServiceURL(
           "service:jmx:rmi:///jndi/rmi://"+ip+":"+port+"/server");
        	//	 "service:jmx:rmi:///jndi/rmi://"+"10.66.3.73:9999"+"/server");
         System.out.println("jmx url is got");
         JMXConnector jmxc = JMXConnectorFactory.connect(url, env);
         System.out.println("jmxConnector is set");
         mbsc = jmxc.getMBeanServerConnection();
         System.out.println("beanServerConnection is got");
         proxy = (IHandler) JMX.newMBeanProxy
				(mbsc, new ObjectName("root:type=Handler"), IHandler.class);
         System.out.println("proxy is got");
	}
	
	public void execute(String source,String params){
		proxy.setExeName(source);
		proxy.setParams(params);
		proxy.execute();
	}
}
