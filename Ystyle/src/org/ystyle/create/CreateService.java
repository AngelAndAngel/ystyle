package org.ystyle.create;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.ystyle.create.SchemaUtil;
import org.ystyle.create.StringUtils;
import org.ystyle.create.Table;
import org.ystyle.db.Session;
import org.ystyle.db.SessionFactory;

public class CreateService {

	static int tableCount = 0;

	static int progress = 0;

	static StringBuffer e = new StringBuffer("");


	
	public static Set<Table> getAllTables(){
		Session session=SessionFactory.getSession();
		Connection cn=session.getConnection();
		Set<Table> set=null;
		try {
			
			set = SchemaUtil.getAllTables(cn);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			SessionFactory.closeSession(session);
		}
		return set;
	}

	public static void generateCode(String packageName, String schema,String tablename,
			String srcdir, String superClass, Connection conn) throws Exception {
		e = new StringBuffer();

		if (superClass == null)
			superClass = "";
		else
			superClass = " extends " + superClass;

		String suffix = "";

		StringTokenizer st = new StringTokenizer(packageName, ".");
		String path = "";
		while (st.hasMoreTokens())
			path += File.separator + st.nextToken();

		path += File.separator;

		File srcDir = new File(srcdir + File.separator + "src" + File.separator
				+ path);

		File jspDir = new File(srcdir + File.separator + "html");
		srcDir.mkdirs();
		jspDir.mkdirs();

		/*
		 * out.append("\n\nHTML files will be generated in: ").append(
		 * jspDir.getCanonicalPath()); out.append("\nPOJO source directory:
		 * ").append(srcDir.getCanonicalPath()) .append("\n");
		 * out.append("\nJDBC Driver:
		 * ").append(conn.getMetaData().getDriverName()); out.append("\nJDBC
		 * URL: ").append(conn.getMetaData().getURL());
		 */

		System.out.println(conn.getMetaData().getURL());
		String javaCode;
		StringBuffer jspCode;

		String accessors;
		String flds;

		Set<String> tables = SchemaUtil.getTables(conn, schema,tablename).keySet();
		tableCount = tables.size();
		progress = 0;
		int count = 0;
		for (String table : tables) {
			count++;
			progress++;
			boolean create = true;
			Map<String, String> fields = SchemaUtil.getColumns(table);
			String className = StringUtils.mixedCase(table);// + suffix;

			javaCode = "";
			jspCode = new StringBuffer();
			jspCode.append("<fieldset>\n<legend>�Զ������?").append(table).append(
					" Form </legend>" + "\n<form action='#' method='post'>\n");

			if (packageName != null && !"".equals(packageName)) {
				javaCode += "package " + packageName + ";\n\n";
			}

			jspCode
					.append("<p>\n\t<input type='submit' value='Submit'/>\n</p>");

			e.append("  <entity class='").append(packageName).append(".")
					.append(className).append("' name='").append(
							table.toLowerCase()).append("' />\n");

			StringBuffer j5 = new StringBuffer(javaCode);
			j5.append("//@Entity(name=\"").append(table).append("\")\n");
			j5.append("public class ").append(className).append(suffix).append(
					" implements java.io.Serializable {\n");
			javaCode += "public class " + className + superClass
					+ " implements java.io.Serializable {\n";

			accessors = "";
			flds = "";
			String flds2 = "";
			Map<String, String> pk = SchemaUtil.getPrimaryKeys(table);

			if (pk.size() == 0) {

				String wrng = "Warning: ��� <<" + table + ">> û��һ�����!";
				System.out.println(wrng);

			}

			String id = "";
			for (String field : SchemaUtil.getColumns(table).keySet()) {
				for (Object pkValue : pk.keySet()) {
					if (pkValue instanceof String
							&& field.equalsIgnoreCase((String) pkValue)) {
						id = "\n\n    // @Id";
						break;
					} else
						id = "";
				}
				accessors += StringUtils.createAccessor(field, fields
						.get(field));// name,type

				flds += "\n\n    private " + fields.get(field) + " " + field
						+ ";";

				flds2 += id + "\n    private " + fields.get(field) + " "
						+ field + ";";

				jspCode.append("\n<div style='margin: 10px'>\n\t<label for='")
						.append(field).append("'>").append(field.toUpperCase())
						.append("</label><br>");

				jspCode.append("\n\t<input size='30' type='text' name='")
						.append(field).append("' />\n</div>");

			}

			jspCode.append("\n</form>\n</fieldset>\n");

			javaCode += flds + accessors + "\n\n}";
			j5.append(flds2).append(accessors).append("\n\n}");

			String fileName = srcDir.getCanonicalPath() + File.separator
					+ className + ".java";

			if (create) {

				File file;// = new File(fileName);
				if (true) {// (file.canRead() == false && file.createNewFile())
					// {
					FileOutputStream fout;
					fileName = srcDir.getCanonicalPath() + File.separator
							+ className + suffix + ".java";
					file = new File(fileName);
					fout = new FileOutputStream(file);
					fout.write(new String(j5).getBytes());
					fout.flush();
					fout.close();
					System.out.println("���� POJO... " + fileName);

				}

				// write a jsp file now
				File file2 = new File(jspDir.getCanonicalPath()
						+ File.separator + table.toLowerCase() + ".html");
				System.out.println("���� HTML... " + file2.getCanonicalPath());
				FileOutputStream fout2 = new FileOutputStream(file2);
				fout2.write(new String(jspCode).getBytes());
				fout2.flush();
				fout2.close();

			}

		}

		conn.close();
		conn = null;

	}

}
