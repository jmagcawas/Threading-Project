package com.svi.training;

public class HelloThread implements Runnable {
	
	public void run() {
        System.out.println("Hello from a thread!");
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		(new Thread(new HelloThread())).start();

	}

}
