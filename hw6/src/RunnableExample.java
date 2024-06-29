public class RunnableExample {
    public static void main(String[] args) {
        // Create a new instance of the Runnable interface and implement its run method
        Runnable myRunnable1 = new MyRunnable("Thread-1", "Hello");
        Runnable myRunnable2 = new MyRunnable("Thread-2", "World");
        Runnable myRunnable3 = new MyRunnable("Thread-3", "Java");

        // Create a Thread object and pass the Runnable instance to its constructor
        Thread thread1 = new Thread(myRunnable1);
        Thread thread2 = new Thread(myRunnable2);
        Thread thread3 = new Thread(myRunnable3);

        // Start the threads
        thread1.start();
        thread2.start();
        thread3.start();

        // Main thread continues execution while the new thread runs asynchronously
        System.out.println("Main thread continues execution...");
    }
}

class MyRunnable implements Runnable {
    private String threadName;
    private String word;

    public MyRunnable(String threadName, String word) {
        this.threadName = threadName;
        this.word = word;
    }

    @Override
    public void run() {
        // Define the task to be executed
        System.out.println("Thread " + threadName + " says: " + word);
    }
}
