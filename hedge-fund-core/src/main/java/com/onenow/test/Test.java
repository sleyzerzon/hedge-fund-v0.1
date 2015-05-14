package com.onenow.test;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	private int count;
	private volatile int count2;

	public void incrCount() {
		count++;
		count2++;
	}

	public int getCount() {
		return count;
	}

	public int getCount2() {
		return count2;
	}

	private static Test test = new Test();

	public static void main(String[] argv) throws InterruptedException {
		Thread[] threads = new Thread[10000];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(new Runnable() {

				@Override
				public void run() {
					for (int i = 0; i < 10000; i++) {
						test.incrCount();
					}
				}
			});
		}
		for (int i = 0; i < threads.length; i++) {
			threads[i].start();

		}
		for (int i = 0; i < threads.length; i++) {
			threads[i].join();

		}
		System.out.println(test.getCount());

		System.out.println(test.getCount2());
	}
}
