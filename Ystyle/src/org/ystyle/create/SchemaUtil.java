package org.ystyle.create;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.ystyle.create.DataTypeMappings;
import org.ystyle.create.Table;

/**
 * Utility class used internally within jLynx to view/build/maintain metadata.
 * Uses JDBC specs at runtime to discover database metadata.
 */
final class SchemaUtil {

  static final int MSSQL = 200;
  static final int MYSQL = 400;
  static final int ORACLE = 100;

  private static Connection conn;
  private static String entTypes[] = { "TABLE" };


  static int findDbVendor(DatabaseMetaData dm) throws SQLException {
    String dbName = dm.getDatabaseProductName();

    if ("MySQL".equalsIgnoreCase(dbName))
      return MYSQL;
    else if ("Microsoft SQL Server".equalsIgnoreCase(dbName))
      return MSSQL;
    else if ("Oracle".equalsIgnoreCase(dbName))
      return ORACLE;

    return 0;
  }

  static Map<String, String> getColumns(String table) throws SQLException {

    Map<String, String> result = new TreeMap<String, String>();
    DatabaseMetaData dmd = conn.getMetaData();
    ResultSet rs = dmd.getColumns(null, null, table.toUpperCase(), null);

    if (rs != null && !rs.next())
      throw new SQLException("No columns");

    do {

      String col = rs.getObject(4).toString().toLowerCase();
      String type = getDataType(rs.getInt(5));
      result.put(col, type);

    } while (rs.next());

    return result;

  }

  private static String getDataType(int type) {

    Integer jType = new Integer(type);
    String result = (String) DataTypeMappings.TYPE_MAPPINGS.get(jType);

    if (result == null)
      result = "Object";

    return result;

  }

  static Map<String, String> getPrimaryKeys(String table) {
    Map<String, String> result = new TreeMap<String, String>();
    try {
      // connect();
      DatabaseMetaData dmd = conn.getMetaData();

      ResultSet rs = dmd.getPrimaryKeys(null, null, table);
      while (rs.next()) {
        String col = rs.getString(4);
        result.put(col, "Primary Key");
      }
    } catch (SQLException e) {
          e.printStackTrace();
    }

    return result;

  }

  static Map<String, String> getTables(Connection cn, String schema,String tablename)
      throws SQLException {
    conn = cn;

    Map<String, String> result = new TreeMap<String, String>();
    if (cn == null) {
      conn = null;
      // connect();
      cn = conn;
    }
    DatabaseMetaData dmd = cn.getMetaData();
    ResultSet rs = null;
    rs = dmd.getTables(null, schema, tablename, entTypes);

    // removed schema 3.22.05
    while (rs.next()) {
      // logger.info("getTables :: " + rs.getString(3));
      String tbl = rs.getString(3);
      // result.put(tbl.toLowerCase(), rs.getString(4));// table or view
      result.put(tbl, rs.getString(4));
    }

    // MS SQL Server system tables
    result.remove("syssegments");
    result.remove("sysconstraints");
    return result;
  }
  
  public static Set<Table> getAllTables(Connection cn) throws SQLException{
	  Set<Table> set=new HashSet<Table>();
	  DatabaseMetaData dmd=cn.getMetaData();
	  ResultSet rs=dmd.getTables(null,null,null,entTypes);
	  while(rs.next()){
		  Table table=new Table();
		  table.setDatabase(rs.getString(1));
		  table.setT2(rs.getString(2));
		  table.setTableName(rs.getString(3));
		  table.setType(rs.getString(4));
		  table.setT5(rs.getString(5));
		  set.add(table);
	  }
	  return set;
  }
  
  

}