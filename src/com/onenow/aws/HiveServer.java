package com.onenow.aws;

import java.io.InputStream;

import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;

/**
 * To use {@link JdbcFacade JDBC}, we need HiveServer2 running.
 * 
 * @see JdbcFacade
 */
public class HiveServer extends YakHair {

	public HiveServer(MagpieSsh ssh) {
		super(ssh);
		setName("HiveServer" + hashCode());
		this.parser = new SimpleOutputParser("HiveServer2");
	}

	private OutputParser parser;
	
	@Override
	public void run() {
		try {
			MagpieSsh ssh = getSsh();
			ssh.connect();
			Session session = ssh.getSession();
			final Command cmd = session.exec("hive --service hiveserver2");
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
