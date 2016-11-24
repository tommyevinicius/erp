package br.com.siga.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConexaoDataSource {

	private static final String DATA_SOURCE = "java:/sigaDatasource";

	public static Connection getConnectionDataSource() throws NamingException, SQLException {
		Context context = new InitialContext();
		DataSource dataSource = (DataSource) context.lookup(DATA_SOURCE);
		Connection conn = dataSource.getConnection();
		return conn;
	}

	public static void closeConnectionDataSource(Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
		if (rs != null)
			rs.close();
		if (ps != null)
			ps.close();
		if (conn != null)
			conn.close();
	}

}
