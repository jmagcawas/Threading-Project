package com.svi.training;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class Solitaire{
	
	
	// Display a message, preceded by
	// the name of the current thread
	static void threadMessage(String message) {
		String threadName =
				Thread.currentThread().getName();
		System.out.format("%s: %s%n",
				threadName,
				message);
	}
//	@Override
//	public void run() {
//		for (int i = 0; i < UserInput.gamecount; i++) {
//			UserInput.gameplayed++;
//			GameManager game = new GameManager();
//			game.createManuever();
//			game.createFoundation();
//			game.lastFaceUp();
//			game.executeProcedure();
//			try {
//				Thread.sleep(4000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			threadMessage("Thread terminated");
//		}
//
//	}
	static void shutdownAndAwaitTermination(ExecutorService e) {
		   e.shutdown(); // Disable new tasks from being submitted
		   try {
		     // Wait a while for existing tasks to terminate
		     if (!e.awaitTermination(60, TimeUnit.SECONDS)) {
		       e.shutdownNow(); // Cancel currently executing tasks
		       // Wait a while for tasks to respond to being cancelled
		       if (!e.awaitTermination(60, TimeUnit.SECONDS))
		           System.err.println("Pool did not terminate");
		     }
		   } catch (InterruptedException ie) {
		     // (Re-)Cancel if current thread also interrupted
		     e.shutdownNow();
		     // Preserve interrupt status
		     Thread.currentThread().interrupt();
		   }
		   System.out.println("Thread shutdown");
		 }

public static void main(String[] args)	throws InterruptedException {
		// TODO Auto-generated method stub
	UserInput input = new UserInput(); // get user input
	input.displayInput();
	
	//ForkJoinPool e = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
	ExecutorService e = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	//ExecutorService e = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	
	 Runnable task1 = () -> {
		 long startTime = System.currentTimeMillis();
		 for (int i = 0; i < UserInput.gamecount; i++) {
			 UserInput.gameplayed++;
			 GameManager game = new GameManager();
			 game.createManuever();
			 game.createFoundation();
			 game.lastFaceUp();
			 game.executeProcedure();
			 System.out.println("Executing Task1 inside : " + Thread.currentThread().getName());
			 
		 }
		 System.out.println("Time elapsed multi thread: " + (System.currentTimeMillis() - startTime));
		 try {
			 TimeUnit.SECONDS.sleep(1);
		 } catch (InterruptedException ex) {
			 throw new IllegalStateException(ex);
		 }
		 
//	
//			System.out.printf("******************************************\n");
//			System.out.printf("Main: Parallelism: %d\n", e.getParallelism());
//			System.out.printf("Main: Active Threads: %d\n", e.getActiveThreadCount());
//			System.out.printf("Main: Task Count: %d\n", e.getQueuedTaskCount());
//			System.out.printf("Main: Steal Count: %d\n", e.getStealCount());
//			System.out.printf("******************************************\n");
     };
     
	 Runnable task2 = () -> {
		 long startTime = System.currentTimeMillis();
		 for (int i = 0; i < UserInput.gamecount; i++) {
			 UserInput.gameplayed++;
			 GameManager game = new GameManager();
			 game.createManuever();
			 game.createFoundation();
			 game.lastFaceUp();
			 game.executeProcedure();
			 System.out.println("Executing Task1 inside : " + Thread.currentThread().getName());
		 }
		 System.out.println("Time elapsed multi thread: " + (System.currentTimeMillis() - startTime));
		 try {
			 TimeUnit.SECONDS.sleep(1);
		 } catch (InterruptedException ex) {
			 throw new IllegalStateException(ex);
		 }
		 
	 };
	 
	 Runnable task3 = () -> {
		 long startTime = System.currentTimeMillis();
		 for (int i = 0; i < UserInput.gamecount; i++) {
			 UserInput.gameplayed++;
			 GameManager game = new GameManager();
			 game.createManuever();
			 game.createFoundation();
			 game.lastFaceUp();
			 game.executeProcedure();
			 System.out.println("Executing Task1 inside : " + Thread.currentThread().getName());
			 
		 }
		 System.out.println("Time elapsed multi thread: " + (System.currentTimeMillis() - startTime));
		 try {
			 TimeUnit.SECONDS.sleep(1);
		 } catch (InterruptedException ex) {
			 throw new IllegalStateException(ex);
		 }
		 
	 };
	 
	 
	 
     e.submit(task1);
     e.submit(task2);
     e.submit(task3);
//		threadMessage("Waiting for Solitaire thread to finish");
//		Thread t = Thread.currentThread();
//		while (t.isAlive()) {
//			threadMessage("Still waiting...");
//			// Wait maximum of 1 second
//			// for MessageLoop thread
//			// to finish.
//	
//			t.join(1000);
//			
//		}
//	shutdownAndAwaitTermination(e);
     e.shutdown();
//     shutdownAndAwaitTermination(e);
}
}
