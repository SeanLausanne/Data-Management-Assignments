package assignment2.problem2.task2;

public class TransactionThread extends Thread{
	
	String transactionNumber;
	TransactionExecutor executeTransaction;
	
	public TransactionThread(String transactionNumber) {
		executeTransaction = new TransactionExecutor();
		this.transactionNumber = transactionNumber;
	}
	
	@Override
	public void run() {
		if (transactionNumber.equals("T1")) {
			executeTransaction.executeT1();
		}else if (transactionNumber.equals("T2")) {
			executeTransaction.executeT2();
		}else if (transactionNumber.equals("T3")) {
			executeTransaction.executeT3();
		}
	}
}
