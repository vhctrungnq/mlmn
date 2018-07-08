package inventory.utils.log;

import inventory.core.IN_Global;

import java.sql.Connection;




public class IN_DbAppender {

	public static void log(IN_DBLogParam record) {
		if (record == null) {
			return;
		}

		Connection con = null;
		String[] sqls = null;
		try {
			sqls = ((IN_DBLogParam) record).getDMLs();
			con = IN_Global.DATA_SOURCE.getConnection();
			con.setAutoCommit(false);
			processSqlStatement(con, sqls);
			con.commit();
			con.setAutoCommit(true);
		} catch (Exception e) {
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception exe) {
				}
			}
		}
	}

	protected static void processSqlStatement(Connection con, String[] sqls) {
		if (sqls == null || sqls.length == 0) {
			return;
		}

		for (String sql : sqls) {
			if (sql != null && sql.trim().length() > 0) {
				if (sql.trim().startsWith("{")) {
					callProcedure(con, sql);
				} else {
					executeUpdate(con, sql);
				}
			}
		}
	}

	protected static void executeUpdate(Connection con, String sql) {
		try {
			con.createStatement().executeUpdate(sql);
		} catch (Exception e) {
		}
	}

	protected static void callProcedure(Connection con, String sql) {
		try {
			con.prepareCall(sql).execute();
		} catch (Exception e) {
		}
	}

}
