
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class GenTestingSQL {
	private static float sampleVolume;
	private static Connection Con;
	public static void main(String[] args) {
		//read the csv mapping file
		//String filePath=args[0];
		//Connect to Oracle
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");  
			Con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","hr","hr"); 
			String [] strsrc={"HR","employees","employee_id"};
			System.out.print(validateInput(strsrc));
			Con.close();  
		}
		catch(Exception e) { System.out.println(e);}  

		/*
		Map<String, ArrayList<String>> peopleByForename = new HashMap<String, ArrayList<String>>();    
		ArrayList<String> test=new ArrayList<String>();
		test.add("asshole");
		test.add("bitch");
		peopleByForename.put("Be", test);
		ArrayList<String> bob=(ArrayList<String>) peopleByForename.get("Be");
		System.out.print("\n");
        System.out.print(bob.get(0));
        System.out.print("\n");
        System.out.print(bob.get(1));
		 */
	}

	public static String validateInput(String[] SrcTgtMapping ) {
		//method to return the if the input is valid or not
		String result = null;
		try {
			String sqltext="select "+SrcTgtMapping[2]+" from "+SrcTgtMapping[1]+" where rownum=1";
			System.out.print(sqltext);
			Statement stmt=Con.createStatement();
			ResultSet rs=stmt.executeQuery(sqltext);
			while(rs.next())
				result=rs.getString(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}

	public static String getUqKeyWhereClause(String[] TgtTableInfo) {
		//method to get the value and return the where clause
		String result = null;
		try {
			String sqltext="select c.table_name,tc.column_name,data_type "
							+"from "
							+"all_cons_columns c,"
							+"all_constraints s,"
							+"all_tab_columns tc"
							+"where c.owner=s.owner "
							+"and c.table_name=s.table_name "
							+"and c.constraint_name=s.constraint_name"
							+"and s.constraint_type='U'"
							+"and c.owner=tc.owner"
							+"and c.column_name=tc.COLUMN_NAME"
							+"and c.table_name=tc.table_name"
							+"and c.table_name=? and c.owner=?";
			System.out.print(sqltext);
			Statement stmt=Con.createStatement();
			ResultSet rs=stmt.executeQuery(sqltext);
			while(rs.next())
				result=rs.getString(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static String getRandUpdateVal(String SrcFieldInfo) {
		//method to generate a random value base on the datatype return a string for the update statement
		return null;
	}

	public static String genType12UpdateSQL(String SrcFieldInfo) {
		return null;
	}

	public static String genType12SelectSQL(String TgtTableInfo) {
		return null;
	}


}
