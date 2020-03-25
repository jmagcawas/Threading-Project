package com.svi.training.demo;

 class Run extends Thread{

	@Override
	public void run() {
		// TODO Auto-generated method stub
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
public class Demo1 {
		
	public static void main(String[] args) {
		Run run1 = new Run();
		Run run2 = new Run();
		run1.start();
		run2.start();
//		Thread t1 = new Thread(new Run());
//		t1.start();

	}

}
