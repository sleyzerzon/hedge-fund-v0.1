package com.enremmeta.onenow.summit;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Controls the surface over Olaine. Also, related to Crow.
 * 
 * @see http://en.wikipedia.org/wiki/Olaine
 */
public class Yak {

	private Yak() {
		super();
	}

	public final EmrFacade getEmr() {
		return emr;
	}

	private EmrFacade emr;

	private JdbcFacade jdbc;

	public JdbcFacade getJdbc() {
		return jdbc;
	}

	private static Yak getInstance() {
		return yak;
	}

	private static final Yak yak = new Yak();

	public static void main(String[] argv) throws Exception {
		getInstance().go(argv[0], argv[1]);
	}

	private AwsPricing awsPricing;

	public AwsPricing getAwsPricing() {
		return awsPricing;
	}

	public void readPricing() throws MalformedURLException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String jsonp = IOUtils.toString(new URL(
				"https://a0.awsstatic.com/pricing/1.0.19/ec2/linux-od.min.js")
				.openStream());
		jsonp = jsonp.substring(jsonp.indexOf("callback(")
				+ "callback(".length());
		jsonp = jsonp.substring(0, jsonp.length() - 1);
		awsPricing = mapper.readValue(jsonp, AwsPricing.class);
	}

	private void go(String access, String secret) throws Exception {
		readPricing();
		
		emr = new EmrFacade(access, secret, "j-2XGI9OQZRY6QG");

		// emr.resize("m3.2xlarge", 3);

		HiveLogParser parser = new HiveLogParser();
		TailHair tailHair = new TailHair(new MagpieSsh(),
				"/mnt/var/log/apps/hive.log", parser);
		tailHair.start();

		HiveServer hs = new HiveServer(new MagpieSsh());
		hs.start();

		jdbc = new JdbcFacade();
		jdbc.connect();

		// hs.join();

		tailHair.join();
	}
}
