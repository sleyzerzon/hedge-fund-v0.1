package com.enremmeta.onenow.summit;

import java.io.File;
import java.security.PublicKey;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;
import net.schmizz.sshj.transport.verification.HostKeyVerifier;
import net.schmizz.sshj.userauth.keyprovider.PKCS8KeyFile;
import net.schmizz.sshj.userauth.method.AuthPublickey;

public class MagpieSsh {

	public MagpieSsh(String keyFile) {
		super();
		ssh = new SSHClient();
		this.keyFile = keyFile;
	}
	
	private String keyFile;

	public static final Command exec(String keyFile, String cmdStr) throws Exception {
		MagpieSsh ssh = new MagpieSsh(keyFile);
		ssh.connect();
		Session session = ssh.getSession();
		
		final Command cmd = session.exec(cmdStr);
		return cmd;
	}

	private SSHClient ssh;

	private Session session;

	public void connect() throws Exception {
		ssh.loadKnownHosts();
		ssh.addHostKeyVerifier(new HostKeyVerifier() {
			// Danger :)
			public boolean verify(String hostname, int port, PublicKey key) {
				return true;
			}
		});
		ssh.connect("54.81.12.30");

		PKCS8KeyFile keyFile = new PKCS8KeyFile();
		keyFile.init(new File(this.keyFile));
		ssh.auth("hadoop", new AuthPublickey(keyFile));
		
		session = ssh.startSession();
	}

	public Session getSession() {
		return session;
	}

}
