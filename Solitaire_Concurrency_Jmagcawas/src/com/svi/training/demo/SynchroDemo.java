package com.svi.training.demo;

import java.util.Scanner;

class Processor extends Thread{
	private boolean running = true;
	public void run(){
		while(running) {
			System.out.println("Hello");
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	public void shutdown() {
		running = false;
	}
}

public class SynchroDemo {

	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		Processor proc = new Processor();
		proc.start();
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
		proc.shutdown();
		scan.close();

	}

}
