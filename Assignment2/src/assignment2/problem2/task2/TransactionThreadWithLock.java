package assignment2.problem2.task2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TransactionThreadWithLock extends Thread {

	String transactionNumber;
	TransactionExecutor executeTransaction;
	static Lock lock = new ReentrantLock();

	public TransactionThreadWithLock(String transactionNumber) {
		executeTransaction = new TransactionExecutor();
		this.transactionNumber = transactionNumber;
	}

	@Override
	public void run() {
		// apply lock
		lock.lock();
		try {
			if (transactionNumber.equals("T1")) {
				executeTransaction.executeT1();
			} else if (transactionNumber.equals("T2")) {
				executeTransaction.executeT2();
			} else if (transactionNumber.equals("T3")) {
				executeTransaction.executeT3();
			}
		} finally {
			lock.unlock();
		}
	}
}
