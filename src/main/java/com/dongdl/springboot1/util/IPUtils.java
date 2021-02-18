package com.dongdl.springboot1.util;

import sun.net.util.IPAddressUtil;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author dongdongliang13@hotmail.com
 * @date 2020/3/18 14:47 UTC+8
 * @description
 **/
public class IPUtils {

	/**
	 * 获取请求IP
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request) {
		//获取IP, 考虑代理
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.trim().length() == 0 || "unknow".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.trim().length() == 0 || "unknow".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.trim().length() == 0 || "unknow".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			//本地ip,根据网卡获取本机配置ip
			if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
				ip = getLocalIp();
			}
		}
		//多个代理时，第一个ip为真实ip
		if (ip.length() > 15 && ip.indexOf(",") > 0) {
			ip = ip.substring(0, ip.indexOf(","));
		}
		return ip;
	}

	public static String getLocalIp () {
		InetAddress inetAddress = null;
		try {
			inetAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return inetAddress == null ? "127.0.0.1" : inetAddress.getHostAddress();
	}

	public static class LocalIp4 {
		protected static final String LOCAL_ADDR = "0:0:0:0:0:0:0:1";
		protected static final String LOCAL_IP = "127.0.0.1";

		/**
		 * 本机ip
		 * @param ip
		 * @return
		 */
		public static boolean localIp(String ip) {
			return LOCAL_ADDR.equals(ip) || LOCAL_IP.equals(ip);
		}

		/**
		 * tcp/ip协议 内网ip
		 * @param ip
		 * @return
		 */
		public static boolean internalIp(String ip) {
			byte[] addr = IPAddressUtil.textToNumericFormatV4(ip);
			return internalIp(addr);
		}

		public static boolean internalIp(byte[] addr) {
			final byte b0 = addr[0];
			final byte b1 = addr[1];
			//10.x.x.x/8
			final byte SECTION_1 = 0x0A;
			//172.[16-31].x.x/12
			final byte SECTION_2 = (byte) 0xAC;
			final byte SECTION_3 = (byte) 0x10;
			final byte SECTION_4 = (byte) 0x1F;
			//192.168.x.x/16
			final byte SECTION_5 = (byte) 0xC0;
			final byte SECTION_6 = (byte) 0xA8;
			switch (b0) {
				case SECTION_1:
					return true;
				case SECTION_2:
					if (b1 >= SECTION_3 && b1 <= SECTION_4) {
						return true;
					}
				case SECTION_5:
					switch (b1) {
						case SECTION_6:
							return true;
					}
				default:
					return false;
			}
		}
	}

	/**
	 * @author dongdongliang13@hotmail.com
	 * @date 2020/3/26 14:45 UTC+8
	 * @description
	 * @param ip
	 * @return 是否是IP4地址
	 */
	public static boolean isIP4(String ip) {
		return ip.matches("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$") && IPAddressUtil.isIPv4LiteralAddress(ip);
	}
}
