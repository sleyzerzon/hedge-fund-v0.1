package com.onenow.summit;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import net.schmizz.sshj.connection.channel.direct.Session.Command;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onenow.database.CustomerAccount;

public class Yak {

	public static final String HIVE_LOG_FILE = "/mnt/var/log/apps/hive.log";

	private Yak() {
		super();
	}

	public final AwsFacade getEmr() {
		return emr;
	}

	private AwsFacade emr;

	private JdbcFacade jdbc;

	public JdbcFacade getJdbc() {
		return jdbc;
	}

	public static Yak getInstance() {
		return yak;
	}

	private static final Yak yak = new Yak();

	public static void main(String[] argv) throws Exception {
		getInstance().go();
	}

	private AwsPricing awsPricing;

	public AwsPricing getAwsPricing() {
		return awsPricing;
	}

	public static AwsPricing readPricing() throws MalformedURLException,
			IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		String jsonp = IOUtils.toString(new URL(
				ConstantsSummit.AWS_ON_DEMAND_PRICING_URL).openStream());
		jsonp = jsonp.substring(jsonp.indexOf("callback(")
				+ "callback(".length());
		jsonp = jsonp.substring(0, jsonp.length() - 1);
		return mapper.readValue(jsonp, AwsPricing.class);
	}

	private void go() throws Exception {
		awsPricing = readPricing();
		YakConfig config = Utils.readConfig();

		CustomerAccount acc = config.getAccounts().get(0);

		emr = new EmrFacade(acc.getCloud().getAccess(), acc.getCloud().getSecret());
		String keyFile = acc.getCloud().getKeyPath();

		// emr.resize("m3.2xlarge", 3);

		// Parse old stuff.
		HiveLogParser parser0 = new HiveLogParser(keyFile, false);
		final Command cmd = MagpieSsh.exec(keyFile, "cat " + HIVE_LOG_FILE
				+ ".*");
		InputStream is = cmd.getInputStream();
		while (true) {
			int avail = is.available();

			if (avail > 0) {
				byte[] bs = new byte[avail];
				if (bs.length == 1 && bs[0] == -1) {
					break;
				}
				is.read(bs);
				String s = new String(bs);
				parser0.handle(s);
			}

		}
		Logger.log("Done!");

		HiveLogParser parser1 = new HiveLogParser(keyFile, true);
		TailHair tailHair = new TailHair(new MagpieSsh(keyFile), HIVE_LOG_FILE,
				parser1);
		tailHair.start();

		HiveServer hs = new HiveServer(new MagpieSsh(keyFile));
		// hs.start();

		jdbc = new JdbcFacade();
		// jdbc.connect();

		// hs.join();

		tailHair.join();
	}
}
