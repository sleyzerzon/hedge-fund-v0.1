package com.enremmeta.onenow.summit;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;

public class OpenTsdbConnection implements Runnable {

	private OpenTsdbConnection() {
		super();
	}

	private InputStream is;
	private OutputStream os;

	public void connect(String host, int port) throws UnknownHostException,
			IOException {
		this.sock = new Socket(host, port);
		sock.setKeepAlive(true);
		this.is = sock.getInputStream();
		this.os = sock.getOutputStream();
		Thread t = new Thread(this);
		t.start();
	}

	private Socket sock;

	private static final OpenTsdbConnection instance = new OpenTsdbConnection();

	public static OpenTsdbConnection getInstance() {
		return instance;
	}

	public void send(String metric, long value)throws IOException {
		send(metric, value, null);
	}

	public void send(String metric, Number value, Map<String, String> tags)
			throws IOException {
		send(System.currentTimeMillis(), metric, value, tags);
	}

	public void send(long timestamp, String metric, Number value,
			String... tags) throws IOException {
		String msg = "put " + timestamp + " " + metric + " " + value;
		for (String tag : tags) {
			msg += " " + tag;
		}
		msg += "\n";
		os.write(msg.getBytes());
		os.flush();
	}

	public void send(long timestamp, String metric, Number value,
			Map<String, String> tags) throws IOException {
		String tagArr[] = new String[tags.keySet().size()];
		int i = 0;
		for (Map.Entry<String, String> entry : tags.entrySet()) {
			tagArr[i++] = entry.getKey() + "=" + entry.getValue();
		}
		send(timestamp, metric, value, tagArr);
	}

	public void run() {

		while (true) {
			int avail;
			try {
				avail = this.is.available();
				if (avail > 0) {
					byte[] buffer = new byte[1024];
					this.is.read(buffer);
					Logger.log("OpenTSDB: " + new String(buffer));
				}
			} catch (IOException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}

		}
	}
}
