package com.enremmeta.onenow.summit;

public class HiveLogParser implements OutputParser {

	public static final String HIVE_LOG_DATE_FORMAT = "2014-10-21 00:19:46,330";

	static String PARSING_COMMAND_MARKER_1 = "]: parse.ParseDriver (ParseDriver.java:parse(185)) - Parsing command: ";
	static String SUBMIT_JOB_MARKER_1 = "Submitting tokens for job: ";

	public HiveLogParser(String keyFile, boolean active) {
		super();
		this.active = active;
		this.keyFile = keyFile;
		this.ssh = new MagpieSsh(keyFile);
	}

	private String keyFile;

	private boolean active;

	private String state = "";

	private final MagpieSsh ssh;

	/**
	 * Example:
	 * 
	 */
	public void handle(String s) {
		s = state + s;
		this.state = "";

		String lines[] = s.split("\n");
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			if (line.length() < HIVE_LOG_DATE_FORMAT.length()) {
				// We are in some state...
			}
		}

		Logger.log("Tail: " + s);
		String hql = null;
		if (s.indexOf(PARSING_COMMAND_MARKER_1) > -1) {
			String threadName = hql = s.substring(s
					.indexOf(PARSING_COMMAND_MARKER_1)
					+ PARSING_COMMAND_MARKER_1.length());
			hql = hql.substring(0,
					hql.indexOf("INFO  [main]: parse.ParseDriver"));
			hql = hql.substring(0, hql.lastIndexOf("\n"));
			hql = hql.trim();
			Statistics.getInstance().setHql(hql);
			Statistics.getInstance().getHqls().add(hql);
			// https://forums.aws.amazon.com/thread.jspa?threadID=162865

		}
		String job = null;
		if (s.indexOf(SUBMIT_JOB_MARKER_1) > -1) {
			job = s.substring(s.indexOf("job_"), s.lastIndexOf("\n"));
			Statistics.getInstance().setJob(job);
			Statistics.getInstance().getJobs().add(job);
			// Do we know this job or not?
			// Let's fake this...
			if (!active || Statistics.getInstance().getJobs().size() == 1) {
				// Let this job run...

			} else {

				// Kill it
				try {
					MagpieSsh.exec(keyFile, "echo I will kill job " + job
							+ " | wall");
					MagpieSsh.exec(keyFile,
							"/home/hadoop/bin/hadoop job -kill " + job);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		//

		// System.out.println(s);
	}
}
