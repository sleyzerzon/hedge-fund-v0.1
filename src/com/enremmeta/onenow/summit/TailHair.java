package com.enremmeta.onenow.summit;

import java.io.InputStream;

import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;

public class TailHair extends YakHair {

	public TailHair(MagpieSsh ssh, String file, OutputParser parser) {
		super(ssh);
		setName("TailHair" + hashCode());
		this.file = file;
		this.parser = parser;
	}

	private OutputParser parser;
	private String file;

	@Override
	public void run() {
		try {
			MagpieSsh ssh = getSsh();
			ssh.connect();
			Session session = ssh.getSession();
			final Command cmd = session.exec("tail -f " + file);
			InputStream is = cmd.getInputStream();
			while (true) {

				int avail = is.available();
				if (avail == 1) {
					int b = is.read();
					if (b == -1) {
						break;
					}

				}
				if (avail > 0) {
					byte[] bs = new byte[avail];
					is.read(bs);
					String s = new String(bs);
					parser.handle(s);
				}

			}
			Logger.log("Done!");
			// cdh.shellCommandForever("tail", parser);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
