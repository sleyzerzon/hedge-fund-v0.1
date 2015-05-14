package com.onenow.aws;

import java.util.ArrayList;
import java.util.List;

public class Statistics {

	private String hql;
	private List<String> hqls = new ArrayList<String>();
	private String job;
	private List<String> jobs = new ArrayList<String>();

	public static Statistics getInstance() {
		return inst;
	}

	private static Statistics inst = new Statistics();

	private Statistics() {
		// TODO Auto-generated constructor stub
	}

	public String getHql() {
		return hql;
	}

	public void setHql(String hql) {
		this.hql = hql;
	}

	public List<String> getHqls() {
		return hqls;
	}

	public void setHqls(List<String> hqls) {
		this.hqls = hqls;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public List<String> getJobs() {
		return jobs;
	}

	public void setJobs(List<String> jobs) {
		this.jobs = jobs;
	}

}
