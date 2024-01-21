package code.shubham.commons.generators.id.implementations;

import code.shubham.commons.generators.id.IDGenerator;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.SecureRandom;
import java.util.Enumeration;

import static code.shubham.commons.generators.id.constants.Constants.NODE_ID_BIT_LENGTH;

public class NodeIdGenerator implements IDGenerator<Long> {

	private static final long maxNodeIdValue = (1L << NODE_ID_BIT_LENGTH) - 1;

	public Long generate() {
		long nodeId;
		try {
			final StringBuilder builder = new StringBuilder();
			final Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			while (networkInterfaces.hasMoreElements()) {
				final NetworkInterface networkInterface = networkInterfaces.nextElement();
				final byte[] mac = networkInterface.getHardwareAddress();
				if (mac != null)
					for (int i = 0; i < mac.length; ++i)
						builder.append(String.format("%02X", mac[i]));
			}
			nodeId = builder.toString().hashCode();
		}
		catch (final SocketException se) {
			// in case of exception get a random number limited by max node size
			nodeId = (new SecureRandom().nextInt());
		}
		nodeId &= maxNodeIdValue;
		return nodeId;
	}

}