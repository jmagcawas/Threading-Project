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
	
	static void originTask() {
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
	}

	public static void main(String[] args)	throws InterruptedException {
		// TODO Auto-generated method stub
		UserInput input = new UserInput(); // get user input
		input.displayInput();

		//ForkJoinPool e = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
		ExecutorService e = Executors.newFixedThreadPool(2);
		//ExecutorService e = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		
		Runnable task1 = () -> {
			originTask();
		};

//		Runnable task2 = () -> {
//			originTask();
//		};
//
//		Runnable task3 = () -> {
//			originTask();
//		};

		e.submit(task1);
//		e.submit(task2);
//		e.submit(task3);

//		e.shutdown();
		shutdownAndAwaitTermination(e);
}
}
