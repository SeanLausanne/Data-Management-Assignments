package assignment2.problem2.task2;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionExecutor {

	// execute transactions according to the table
	public void executeT1() {
		SQLConnector connector = new SQLConnector();
		try {
			Connection conn = connector.establishConnection();
			Transaction t = new Transaction();
			t.operations.add("select * from customers where customer_zip_code_prefix = 3167");
			t.operations.add("update customers set customer_id = \"updateValueT1\" where customer_zip_code_prefix = 3167");
			t.operations.add("sleep");
			t.operations.add("sleep");
			t.operations.add("sleep");
			t.operations.add("sleep");
			t.operations.add("commit");
			t.operations.add("sleep");
			t.excuteOperations(conn, "T1");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void executeT2() {
		SQLConnector connector = new SQLConnector();
		try {
			Connection conn= connector.establishConnection();
			Transaction t = new Transaction();
			t.operations.add("select * from customers where customer_zip_code_prefix = 3167");
			t.operations.add("sleep");
			t.operations.add("sleep");
			t.operations.add("update customers set customer_id = \"updateValueT2\" where customer_zip_code_prefix = 3167");
			t.operations.add("sleep");
			t.operations.add("sleep");
			t.operations.add("sleep");
			t.operations.add("commit");
			t.excuteOperations(conn, "T2");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void executeT3() {
		SQLConnector connector = new SQLConnector();
		try {
			Connection conn = connector.establishConnection();
			Transaction t = new Transaction();
			t.operations.add("sleep");
			t.operations.add("sleep");
			t.operations.add("select * from customers where customer_zip_code_prefix = 3167");
			t.operations.add("sleep");
			t.operations.add("update customers set customer_id = \"updateValueT3\" where customer_zip_code_prefix = 3167");
			t.operations.add("sleep");
			t.operations.add("sleep");
			t.operations.add("commit");
			t.excuteOperations(conn, "T3");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
