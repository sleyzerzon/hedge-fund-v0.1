package com.onenow.summit;

public class YakHair extends Thread implements Runnable {

	private MagpieSsh ssh;

	public YakHair(MagpieSsh ssh) {
		super();
		this.ssh = ssh;
	}

	protected MagpieSsh getSsh() {
		return this.ssh;
	}
}
