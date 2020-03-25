package com.svi.training.demo;

class Run1 implements Runnable{

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.println("count: " + i);
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

public class Demo2 {

	public static void main(String[] args) {
		Thread t1 = new Thread(new Run1());
		Thread t2 = new Thread(new Run1());
		t1.start();
		t2.start();
	}

}
