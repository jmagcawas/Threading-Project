package com.svi.training;

public class Solitaire implements Runnable  {
	 // Display a message, preceded by
    // the name of the current thread
    static void threadMessage(String message) {
        String threadName =
            Thread.currentThread().getName();
        System.out.format("%s: %s%n",
                          threadName,
                          message);
    }

	@Override
	public void run() {
		for (int i = 0; i < UserInput.gamecount; i++) {
      	  UserInput.gameplayed++;
      	  GameManager game = new GameManager();
	          game.createManuever();
	          game.createFoundation();
	          game.lastFaceUp();
	          game.executeProcedure();
	          try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	          threadMessage("Thread terminated");
	      }
		
	}public static void main(String[] args)	throws InterruptedException {
		// TODO Auto-generated method stub
		UserInput input = new UserInput(); // get user input
        input.displayInput();
        
		long startTime = System.currentTimeMillis();
		Thread t = new Thread(new Solitaire());
		t.start();
		threadMessage("Waiting for Solitaire thread to finish");
		while (t.isAlive()) {
            threadMessage("Still waiting...");
            // Wait maximum of 1 second
            // for MessageLoop thread
            // to finish.
     
			t.join(1000);
		}
		System.out.println("Time elapsed: " + (System.currentTimeMillis() - startTime));
	}
}
