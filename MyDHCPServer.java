package org.dhcp4java.test;

import org.dhcp4java.DHCPCoreServer;
import org.dhcp4java.DHCPServerInitException;
import org.dhcp4java.DHCPServlet;

import java.util.Properties;

public class MyDHCPServer {
	public static void main(String[] args) {
		try {
			// �����Զ���� DHCPServlet ʵ��
			DHCPServlet servlet = new MyDHCPServlet();

			// ���÷���������
			Properties props = new Properties();
			props.setProperty("serverAddress", "192.168.70.1:67"); // DHCP��������IP��ַ�Ͷ˿ں�

			// ��ʼ�������� DHCP ������
			DHCPCoreServer server = DHCPCoreServer.initServer(servlet, props);
			server.run();
		} catch (DHCPServerInitException e) {
			e.printStackTrace();
			System.err.println("�޷���ʼ�� DHCP ������: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("����������ʱ����: " + e.getMessage());
		}
	}
}


