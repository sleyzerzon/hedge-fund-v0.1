package com.onenow.summit;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;

// http://stackoverflow.com/questions/11261162/ssh-port-fowarding-with-sshj
public class TunnelPortManager {

	final int MAX_PORT = 65536;

	Set<Integer> portsHandedOut = new HashSet();

	ServerSocket leaseNewPort(Integer startFrom) {
		for (int port = startFrom; port < MAX_PORT; port++) {
			if (isLeased(port)) {
				continue;
			}

			ServerSocket socket = tryBind(port);
			if (socket != null) {
				portsHandedOut.add(port);
				Logger.log("handing out port ${port} for local binding");
				return socket;
			}
		}
		throw new IllegalStateException(
				"Could not find a single free port in the range [${startFrom}-${MAX_PORT}]...");
	}

	synchronized void returnPort(ServerSocket socket) {
		portsHandedOut.remove(socket.getLocalPort());
	}

	private boolean isLeased(int port) {
		return portsHandedOut.contains(port);
	}

	protected ServerSocket tryBind(int localPort) {
		try {
			ServerSocket ss = new ServerSocket();
			ss.setReuseAddress(true);
			ss.bind(new InetSocketAddress("localhost", localPort));
			return ss;
		} catch (IOException e) {
			return null;
		}
	}

}
