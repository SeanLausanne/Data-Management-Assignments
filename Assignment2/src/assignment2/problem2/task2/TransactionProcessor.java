package assignment2.problem2.task2;

import java.io.File;
import java.io.IOException;

public class TransactionProcessor {
	

	public static void main(String[] args) {

		try {
			// overwrite old log file
			File log = new File("./transaction-log.txt");
			if (log.exists()) {
				log.delete();
			}
			try {
				log.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// ******switch between lock and no lock here**********
			
			// executeTransactionThreadsWithoutLock(); // run transaction threads without lock
			executeTransactionThreadsWithLock(); // run transaction threads with locks
		
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void executeTransactionThreadsWithoutLock() {
		TransactionThread t1Thread = new TransactionThread("T1");
		TransactionThread t2Thread = new TransactionThread("T2");
		TransactionThread t3Thread = new TransactionThread("T3");
		t1Thread.start();
		t2Thread.start();
		t3Thread.start();
	}
	
	public static void executeTransactionThreadsWithLock() {
		TransactionThreadWithLock t1ThreadWithLock = new TransactionThreadWithLock("T1");
		TransactionThreadWithLock t2ThreadWithLock = new TransactionThreadWithLock("T2");
		TransactionThreadWithLock t3ThreadWithLock = new TransactionThreadWithLock("T3");
		t1ThreadWithLock.start();
		t2ThreadWithLock.start();
		t3ThreadWithLock.start();
	}
}
