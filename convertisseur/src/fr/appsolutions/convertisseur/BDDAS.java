package fr.appsolutions.convertisseur;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.ResultSet;

public class BDDAS {

	private String dbURL = "";
	private String user = "";
	private String password = "";
	private Connection dbConnect = null;
	private Statement dbStatement = null;

	public BDDAS(String url, String user, String password){
		this.dbURL = url;
		this.user = user;
		this.password = password;
	}
	
	
	public Boolean connect()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			this.dbConnect = java.sql.DriverManager.getConnection("jdbc:mysql:" + this.dbURL, this.user, this.password);
			this.dbStatement = this.dbConnect.createStatement();
			System.out.print("[OK]");
			return Boolean.valueOf(true);
		} catch (Exception ex) {
			System.out.print("[FAIL]");
			ex.printStackTrace();
		}
		return Boolean.valueOf(false);
	}

	public ArrayList<String> getPlanning(ArrayList<String> groupe)
	{
		try
		{
			ArrayList<String> planningOld = new ArrayList<String>();

			for (int i = 0; i < groupe.size(); i++) {
				ResultSet rs = (ResultSet)this.dbStatement.executeQuery("SELECT * FROM planning WHERE groupe='" + (String)groupe.get(i) + "'");
				while (rs.next()) {
					planningOld.add(rs.getString("planning"));
				}
			}

			System.out.print("[OK]");
			return planningOld;
		} catch (SQLException ex) {
			System.out.print("[FAIL]");
			ex.printStackTrace();
		}
		return null;
	}

	public void updatePlanning(ArrayList<String> groupe, ArrayList<String> planning)
	{
		try
		{
			for (int i = 0; i < groupe.size(); i++)
			{
				if (planning.get(i) == null) {
					System.out.println("Le fichier d'emploi du temps est vide pour: " + (String)groupe.get(i));
				}
				else {
					this.dbStatement.executeUpdate("UPDATE planning SET planning='" + (String)planning.get(i) + "' WHERE groupe='" + (String)groupe.get(i) + "'");
				}
			}
			System.out.print("[OK]");
		}
		catch (SQLException ex) {
			System.out.print("[FAIL]");
			ex.printStackTrace();
		}
	}



	public void close()
	{
		try
		{
			this.dbStatement.close();
			this.dbConnect.close();
			this.dbConnect.close();
			System.out.print("[OK]");
		} catch (SQLException ex) {
			System.out.print("[FAIL]");
			ex.printStackTrace();
		}
	}

}
