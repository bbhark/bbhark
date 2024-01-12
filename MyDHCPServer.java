package org.dhcp4java.test;

import org.dhcp4java.DHCPCoreServer;
import org.dhcp4java.DHCPServerInitException;
import org.dhcp4java.DHCPServlet;

import java.util.Properties;

public class MyDHCPServer {
	public static void main(String[] args) {
		try {
			// 创建自定义的 DHCPServlet 实例
			DHCPServlet servlet = new MyDHCPServlet();

			// 设置服务器属性
			Properties props = new Properties();
			props.setProperty("serverAddress", "192.168.70.1:67"); // DHCP服务器的IP地址和端口号

			// 初始化并启动 DHCP 服务器
			DHCPCoreServer server = DHCPCoreServer.initServer(servlet, props);
			server.run();
		} catch (DHCPServerInitException e) {
			e.printStackTrace();
			System.err.println("无法初始化 DHCP 服务器: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("服务器运行时出错: " + e.getMessage());
		}
	}
}


