package org.dhcp4java.test;

import org.dhcp4java.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class MyDHCPServlet extends DHCPServlet {
	private static final byte[] SPECIFIC_MAC_ADDRESS = new byte[]{(byte) 0x00, (byte) 0x0c, (byte) 0x29, (byte) 0xc2, (byte) 0xeb, (byte) 0x4c};
	private static final String SPECIFIC_IP_ADDRESS = "192.168.70.137";
	private static final int LEASE_TIME = 5; // 示例租约时间，单位为秒

	private static final int MAC_ADDRESS_LENGTH = 6;
	@Override
	public DatagramPacket serviceDatagram(DatagramPacket requestDatagram)  {
		DHCPPacket request = DHCPPacket.getPacket(requestDatagram);
		DHCPPacket response = null;

		try {
			if (request.isDhcp()) {
				byte messageType = request.getDHCPMessageType();
				if (messageType == DHCPConstants.DHCPDISCOVER) {
					// 检查MAC地址，分配IP
					if (Arrays.equals(request.getChaddr(), SPECIFIC_MAC_ADDRESS)) {
						InetAddress offeredAddress = null;

						InetAddress SERVER_IDENTIFIER = null; // 服务器标识符
						offeredAddress = InetAddress.getByName(SPECIFIC_IP_ADDRESS);
						SERVER_IDENTIFIER = InetAddress.getByName("192.168.70.1");

						response = DHCPResponseFactory.makeDHCPOffer(request, offeredAddress, LEASE_TIME, SERVER_IDENTIFIER, "Your Custom Message", null);
					}
				} else if (messageType == DHCPConstants.DHCPREQUEST) {
					byte[] requestMac = Arrays.copyOf(request.getChaddr(), MAC_ADDRESS_LENGTH);
					// 检查MAC地址，分配IP
					if (Arrays.equals(requestMac, SPECIFIC_MAC_ADDRESS)) {
						InetAddress offeredAddress = InetAddress.getByName(SPECIFIC_IP_ADDRESS);
						InetAddress serverIdentifier = InetAddress.getByName("192.168.70.1");

						response = DHCPResponseFactory.makeDHCPAck(request, offeredAddress, LEASE_TIME, serverIdentifier, "Your Custom Message", null);
					}
				}
			}
		} catch (UnknownHostException e) {

		}

		if (response != null) {
			byte[] responseData = response.serialize();
			DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length, requestDatagram.getAddress(), requestDatagram.getPort());
			return responsePacket;
		}

		return null;
	}
}


