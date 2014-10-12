package com.enremmeta.onenow.summit;

public class HiveLogParser implements OutputParser {

	static String PARSING_COMMAND_MARKER_1 = "Parsing command: ";
	static String SUBMIT_JOB_MARKER_1 = "Submitting tokens for job: ";

	public HiveLogParser(boolean active) {
		this.active = active;
	}

	private boolean active;

	private MagpieSsh ssh = new MagpieSsh();

	public void handle(String s) {
		Logger.log("Tail: " + s);
		String hql = null;
		if (s.indexOf(PARSING_COMMAND_MARKER_1) > -1) {
			hql = s.substring(s.indexOf(PARSING_COMMAND_MARKER_1)
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
					MagpieSsh.exec("echo I will kill job " + job + " | wall");
					MagpieSsh.exec("/home/hadoop/bin/hadoop job -kill " + job);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		//

		// System.out.println(s);
	}
}
