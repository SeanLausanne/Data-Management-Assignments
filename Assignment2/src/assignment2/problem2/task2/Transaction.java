package assignment2.problem2.task2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Transaction {

	ArrayList<String> operations;
	File log;

	public Transaction() {
		this.operations = new ArrayList<>();
		log = new File("./transaction-log.txt");
	}
	
	public void excuteOperations(Connection conn, String transactionName) {
		try {
			Statement statement = conn.createStatement();

			for (String operation : operations) {
				// blank cell in the table
				if (operation.equals("sleep")) {
					try {
						TimeUnit.MILLISECONDS.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					continue;
				}

				else if (operation.equals("commit")) {
					conn.commit();
				}

				else if (operation.startsWith("select")) {
					statement.executeQuery(operation);
				}

				else if (operation.startsWith("update")) {
					statement.executeUpdate(operation);
				}

				else {
					System.out.println(transactionName + ": wrong operation");
					break;
				}

				try {
					// write log
					FileWriter fileWritter = new FileWriter(log.getName(), true);
					BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
					bufferWritter.write(transactionName + ": " + operation + "\n");
					bufferWritter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
