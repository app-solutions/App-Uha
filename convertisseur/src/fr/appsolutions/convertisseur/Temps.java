package fr.appsolutions.convertisseur;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Temps
{
	private Date dat;
	private SimpleDateFormat formatageDate;
	private DateFormat fullDateFormat;
	private String a;
	private Calendar lunS1;
	private Calendar marS1;
	private Calendar merS1;
	private Calendar jeuS1;
	private Calendar venS1;
	private Calendar samS1;
	private Calendar lunS2;
	private Calendar marS2;
	private Calendar merS2;
	private Calendar jeuS2;
	private Calendar venS2;
	private Calendar samS2;
	private Calendar lunS3;
	private Calendar marS3;
	private Calendar merS3;
	private Calendar jeuS3;
	private Calendar venS3;
	private Calendar samS3;
	private Calendar lunS4;
	private Calendar marS4;
	private Calendar merS4;
	private Calendar jeuS4;
	private Calendar venS4;
	private Calendar samS4;
	private String lundiS1StringName = "";
	private String mardiS1StringName = "";
	private String mercrediS1StringName = "";
	private String jeudiS1StringName = "";
	private String vendrediS1StringName = "";
	private String samediS1StringName = "";

	private String lundiS2StringName = "";
	private String mardiS2StringName = "";
	private String mercrediS2StringName = "";
	private String jeudiS2StringName = "";
	private String vendrediS2StringName = "";
	private String samediS2StringName = "";

	private String lundiS3StringName = "";
	private String mardiS3StringName = "";
	private String mercrediS3StringName = "";
	private String jeudiS3StringName = "";
	private String vendrediS3StringName = "";
	private String samediS3StringName = "";

	private String lundiS4StringName = "";
	private String mardiS4StringName = "";
	private String mercrediS4StringName = "";
	private String jeudiS4StringName = "";
	private String vendrediS4StringName = "";
	private String samediS4StringName = "";


	public Temps()
	{
		this.dat = new Date();
		this.formatageDate = new SimpleDateFormat("dd-MM-yyyy");

		this.lunS1 = Calendar.getInstance();
		this.marS1 = Calendar.getInstance();
		this.merS1 = Calendar.getInstance();
		this.jeuS1 = Calendar.getInstance();
		this.venS1 = Calendar.getInstance();
		this.samS1 = Calendar.getInstance();

		this.lunS2 = Calendar.getInstance();
		this.marS2 = Calendar.getInstance();
		this.merS2 = Calendar.getInstance();
		this.jeuS2 = Calendar.getInstance();
		this.venS2 = Calendar.getInstance();
		this.samS2 = Calendar.getInstance();

		this.lunS3 = Calendar.getInstance();
		this.marS3 = Calendar.getInstance();
		this.merS3 = Calendar.getInstance();
		this.jeuS3 = Calendar.getInstance();
		this.venS3 = Calendar.getInstance();
		this.samS3 = Calendar.getInstance();

		this.lunS4 = Calendar.getInstance();
		this.marS4 = Calendar.getInstance();
		this.merS4 = Calendar.getInstance();
		this.jeuS4 = Calendar.getInstance();
		this.venS4 = Calendar.getInstance();
		this.samS4 = Calendar.getInstance();

		this.fullDateFormat = DateFormat.getDateTimeInstance(0, 0);
		this.a = this.fullDateFormat.format(this.dat);

		if (this.a.contains("lundi")) {
			this.lunS1.add(5, 0);
			this.marS1.add(5, 1);
			this.merS1.add(5, 2);
			this.jeuS1.add(5, 3);
			this.venS1.add(5, 4);
			this.samS1.add(5, 5);

			this.lunS2.add(5, 7);
			this.marS2.add(5, 8);
			this.merS2.add(5, 9);
			this.jeuS2.add(5, 10);
			this.venS2.add(5, 11);
			this.samS2.add(5, 12);

			this.lunS3.add(5, 14);
			this.marS3.add(5, 15);
			this.merS3.add(5, 16);
			this.jeuS3.add(5, 17);
			this.venS3.add(5, 18);
			this.samS3.add(5, 19);

			this.lunS4.add(5, 21);
			this.marS4.add(5, 22);
			this.merS4.add(5, 23);
			this.jeuS4.add(5, 24);
			this.venS4.add(5, 25);
			this.samS4.add(5, 26);
		}
		else if (this.a.contains("mardi")) {
			this.lunS1.add(5, -1);
			this.marS1.add(5, 0);
			this.merS1.add(5, 1);
			this.jeuS1.add(5, 2);
			this.venS1.add(5, 3);
			this.samS1.add(5, 4);

			this.lunS2.add(5, 6);
			this.marS2.add(5, 7);
			this.merS2.add(5, 8);
			this.jeuS2.add(5, 9);
			this.venS2.add(5, 10);
			this.samS2.add(5, 11);

			this.lunS3.add(5, 13);
			this.marS3.add(5, 14);
			this.merS3.add(5, 15);
			this.jeuS3.add(5, 16);
			this.venS3.add(5, 17);
			this.samS3.add(5, 18);

			this.lunS4.add(5, 20);
			this.marS4.add(5, 21);
			this.merS4.add(5, 22);
			this.jeuS4.add(5, 23);
			this.venS4.add(5, 24);
			this.samS4.add(5, 25);
		}
		else if (this.a.contains("mercredi")) {
			this.lunS1.add(5, -2);
			this.marS1.add(5, -1);
			this.merS1.add(5, 0);
			this.jeuS1.add(5, 1);
			this.venS1.add(5, 2);
			this.samS1.add(5, 3);

			this.lunS2.add(5, 5);
			this.marS2.add(5, 6);
			this.merS2.add(5, 7);
			this.jeuS2.add(5, 8);
			this.venS2.add(5, 9);
			this.samS2.add(5, 10);

			this.lunS3.add(5, 12);
			this.marS3.add(5, 13);
			this.merS3.add(5, 14);
			this.jeuS3.add(5, 15);
			this.venS3.add(5, 16);
			this.samS3.add(5, 17);

			this.lunS4.add(5, 19);
			this.marS4.add(5, 20);
			this.merS4.add(5, 21);
			this.jeuS4.add(5, 22);
			this.venS4.add(5, 23);
			this.samS4.add(5, 24);
		}
		else if (this.a.contains("jeudi")) {
			this.lunS1.add(5, -3);
			this.marS1.add(5, -2);
			this.merS1.add(5, -1);
			this.jeuS1.add(5, 0);
			this.venS1.add(5, 1);
			this.samS1.add(5, 2);

			this.lunS2.add(5, 4);
			this.marS2.add(5, 5);
			this.merS2.add(5, 6);
			this.jeuS2.add(5, 7);
			this.venS2.add(5, 8);
			this.samS2.add(5, 9);

			this.lunS3.add(5, 11);
			this.marS3.add(5, 12);
			this.merS3.add(5, 13);
			this.jeuS3.add(5, 14);
			this.venS3.add(5, 15);
			this.samS3.add(5, 16);

			this.lunS4.add(5, 18);
			this.marS4.add(5, 19);
			this.merS4.add(5, 20);
			this.jeuS4.add(5, 21);
			this.venS4.add(5, 22);
			this.samS4.add(5, 23);
		}
		else if (this.a.contains("vendredi")) {
			this.lunS1.add(5, -4);
			this.marS1.add(5, -3);
			this.merS1.add(5, -2);
			this.jeuS1.add(5, -1);
			this.venS1.add(5, 0);
			this.samS1.add(5, 1);

			this.lunS2.add(5, 3);
			this.marS2.add(5, 4);
			this.merS2.add(5, 5);
			this.jeuS2.add(5, 6);
			this.venS2.add(5, 7);
			this.samS2.add(5, 8);

			this.lunS3.add(5, 10);
			this.marS3.add(5, 11);
			this.merS3.add(5, 12);
			this.jeuS3.add(5, 13);
			this.venS3.add(5, 14);
			this.samS3.add(5, 15);

			this.lunS4.add(5, 17);
			this.marS4.add(5, 18);
			this.merS4.add(5, 19);
			this.jeuS4.add(5, 20);
			this.venS4.add(5, 21);
			this.samS4.add(5, 22);
		}
		else if ((this.a.contains("samedi")) && ((this.a.contains("13 h")) || (this.a.contains("14 h")) || (this.a.contains("15 h")) || (this.a.contains("16 h")) || (this.a.contains("17 h")) || (this.a.contains("18 h")) || (this.a.contains("19 h")) || (this.a.contains("20 h")) || (this.a.contains("21 h")) || (this.a.contains("22 h")) || (this.a.contains("23 h")))) {
			this.lunS1.add(5, 2);
			this.marS1.add(5, 3);
			this.merS1.add(5, 4);
			this.jeuS1.add(5, 5);
			this.venS1.add(5, 6);
			this.samS1.add(5, 7);

			this.lunS2.add(5, 9);
			this.marS2.add(5, 10);
			this.merS2.add(5, 11);
			this.jeuS2.add(5, 12);
			this.venS2.add(5, 13);
			this.samS2.add(5, 14);

			this.lunS3.add(5, 16);
			this.marS3.add(5, 17);
			this.merS3.add(5, 18);
			this.jeuS3.add(5, 19);
			this.venS3.add(5, 20);
			this.samS3.add(5, 21);

			this.lunS4.add(5, 23);
			this.marS4.add(5, 24);
			this.merS4.add(5, 25);
			this.jeuS4.add(5, 26);
			this.venS4.add(5, 27);
			this.samS4.add(5, 28);
		}
		else if (this.a.contains("samedi")) {
			this.lunS1.add(5, -5);
			this.marS1.add(5, -4);
			this.merS1.add(5, -3);
			this.jeuS1.add(5, -2);
			this.venS1.add(5, -1);
			this.samS1.add(5, 0);

			this.lunS2.add(5, 2);
			this.marS2.add(5, 3);
			this.merS2.add(5, 4);
			this.jeuS2.add(5, 5);
			this.venS2.add(5, 6);
			this.samS2.add(5, 7);

			this.lunS3.add(5, 9);
			this.marS3.add(5, 10);
			this.merS3.add(5, 11);
			this.jeuS3.add(5, 12);
			this.venS3.add(5, 13);
			this.samS3.add(5, 14);

			this.lunS4.add(5, 16);
			this.marS4.add(5, 17);
			this.merS4.add(5, 18);
			this.jeuS4.add(5, 19);
			this.venS4.add(5, 20);
			this.samS4.add(5, 21);
		}
		else if (this.a.contains("dimanche")) {
			this.lunS1.add(5, 1);
			this.marS1.add(5, 2);
			this.merS1.add(5, 3);
			this.jeuS1.add(5, 4);
			this.venS1.add(5, 5);
			this.samS1.add(5, 6);

			this.lunS2.add(5, 8);
			this.marS2.add(5, 9);
			this.merS2.add(5, 10);
			this.jeuS2.add(5, 11);
			this.venS2.add(5, 12);
			this.samS2.add(5, 13);

			this.lunS3.add(5, 15);
			this.marS3.add(5, 16);
			this.merS3.add(5, 17);
			this.jeuS3.add(5, 18);
			this.venS3.add(5, 19);
			this.samS3.add(5, 20);

			this.lunS4.add(5, 22);
			this.marS4.add(5, 23);
			this.merS4.add(5, 24);
			this.jeuS4.add(5, 25);
			this.venS4.add(5, 26);
			this.samS4.add(5, 27);
		}


		this.lundiS1StringName = ("Lundi " + change(getLundiS1()));
		this.mardiS1StringName = ("Mardi " + change(getMardiS1()));
		this.mercrediS1StringName = ("Mercredi " + change(getMercrediS1()));
		this.jeudiS1StringName = ("Jeudi " + change(getJeudiS1()));
		this.vendrediS1StringName = ("Vendredi " + change(getVendrediS1()));
		this.samediS1StringName = ("Samedi " + change(getSamediS1()));

		this.lundiS2StringName = ("Lundi " + change(getLundiS2()));
		this.mardiS2StringName = ("Mardi " + change(getMardiS2()));
		this.mercrediS2StringName = ("Mercredi " + change(getMercrediS2()));
		this.jeudiS2StringName = ("Jeudi " + change(getJeudiS2()));
		this.vendrediS2StringName = ("Vendredi " + change(getVendrediS2()));
		this.samediS2StringName = ("Samedi " + change(getSamediS2()));

		this.lundiS3StringName = ("Lundi " + change(getLundiS3()));
		this.mardiS3StringName = ("Mardi " + change(getMardiS3()));
		this.mercrediS3StringName = ("Mercredi " + change(getMercrediS3()));
		this.jeudiS3StringName = ("Jeudi " + change(getJeudiS3()));
		this.vendrediS3StringName = ("Vendredi " + change(getVendrediS3()));
		this.samediS3StringName = ("Samedi " + change(getSamediS3()));

		this.lundiS4StringName = ("Lundi " + change(getLundiS4()));
		this.mardiS4StringName = ("Mardi " + change(getMardiS4()));
		this.mercrediS4StringName = ("Mercredi " + change(getMercrediS4()));
		this.jeudiS4StringName = ("Jeudi " + change(getJeudiS4()));
		this.vendrediS4StringName = ("Vendredi " + change(getVendrediS4()));
		this.samediS4StringName = ("Samedi " + change(getSamediS4()));
	}


	public String getLundiS1()
	{
		return this.formatageDate.format(this.lunS1.getTime());
	}

	public String getMardiS1()
	{
		return this.formatageDate.format(this.marS1.getTime());
	}

	public String getMercrediS1()
	{
		return this.formatageDate.format(this.merS1.getTime());
	}

	public String getJeudiS1()
	{
		return this.formatageDate.format(this.jeuS1.getTime());
	}

	public String getVendrediS1()
	{
		return this.formatageDate.format(this.venS1.getTime());
	}

	public String getSamediS1()
	{
		return this.formatageDate.format(this.samS1.getTime());
	}


	public String getLundiS2()
	{
		return this.formatageDate.format(this.lunS2.getTime());
	}

	public String getMardiS2()
	{
		return this.formatageDate.format(this.marS2.getTime());
	}

	public String getMercrediS2()
	{
		return this.formatageDate.format(this.merS2.getTime());
	}

	public String getJeudiS2()
	{
		return this.formatageDate.format(this.jeuS2.getTime());
	}

	public String getVendrediS2()
	{
		return this.formatageDate.format(this.venS2.getTime());
	}

	public String getSamediS2()
	{
		return this.formatageDate.format(this.samS2.getTime());
	}


	public String getLundiS3()
	{
		return this.formatageDate.format(this.lunS3.getTime());
	}

	public String getMardiS3()
	{
		return this.formatageDate.format(this.marS3.getTime());
	}

	public String getMercrediS3()
	{
		return this.formatageDate.format(this.merS3.getTime());
	}

	public String getJeudiS3()
	{
		return this.formatageDate.format(this.jeuS3.getTime());
	}

	public String getVendrediS3()
	{
		return this.formatageDate.format(this.venS3.getTime());
	}

	public String getSamediS3()
	{
		return this.formatageDate.format(this.samS3.getTime());
	}


	public String getLundiS4()
	{
		return this.formatageDate.format(this.lunS4.getTime());
	}

	public String getMardiS4()
	{
		return this.formatageDate.format(this.marS4.getTime());
	}

	public String getMercrediS4()
	{
		return this.formatageDate.format(this.merS4.getTime());
	}

	public String getJeudiS4()
	{
		return this.formatageDate.format(this.jeuS4.getTime());
	}

	public String getVendrediS4()
	{
		return this.formatageDate.format(this.venS4.getTime());
	}

	public String getSamediS4()
	{
		return this.formatageDate.format(this.samS4.getTime());
	}



	public String getLundiS1StringName()
	{
		return this.lundiS1StringName;
	}

	public String getMardiS1StringName() {
		return this.mardiS1StringName;
	}

	public String getMercrediS1StringName() {
		return this.mercrediS1StringName;
	}

	public String getJeudiS1StringName() {
		return this.jeudiS1StringName;
	}

	public String getVendrediS1StringName() {
		return this.vendrediS1StringName;
	}

	public String getSamediS1StringName() {
		return this.samediS1StringName;
	}

	public String getLundiS2StringName()
	{
		return this.lundiS2StringName;
	}

	public String getMardiS2StringName() {
		return this.mardiS2StringName;
	}

	public String getMercrediS2StringName() {
		return this.mercrediS2StringName;
	}

	public String getJeudiS2StringName() {
		return this.jeudiS2StringName;
	}

	public String getVendrediS2StringName() {
		return this.vendrediS2StringName;
	}

	public String getSamediS2StringName() {
		return this.samediS2StringName;
	}

	public String getLundiS3StringName()
	{
		return this.lundiS3StringName;
	}

	public String getMardiS3StringName() {
		return this.mardiS3StringName;
	}

	public String getMercrediS3StringName() {
		return this.mercrediS3StringName;
	}

	public String getJeudiS3StringName() {
		return this.jeudiS3StringName;
	}

	public String getVendrediS3StringName() {
		return this.vendrediS3StringName;
	}

	public String getSamediS3StringName() {
		return this.samediS3StringName;
	}

	public String getLundiS4StringName()
	{
		return this.lundiS4StringName;
	}

	public String getMardiS4StringName() {
		return this.mardiS4StringName;
	}

	public String getMercrediS4StringName() {
		return this.mercrediS4StringName;
	}

	public String getJeudiS4StringName() {
		return this.jeudiS4StringName;
	}

	public String getVendrediS4StringName() {
		return this.vendrediS4StringName;
	}

	public String getSamediS4StringName() {
		return this.samediS4StringName;
	}




	public String getCurrentDate()
	{
		return this.a;
	}



	private String change(String date)
	{
		char[] temp = date.toCharArray();

		String jour = "";
		String mois = "";
		String annee = "";


		for (int i = 0; i < 2; i++) {
			jour = jour + temp[i];
		}


		for (int i = 3; i < 5; i++) {
			mois = mois + temp[i];
		}


		for (int i = 6; i < temp.length; i++) {
			annee = annee + temp[i];
		}

		if (mois.equals("01")) {
			mois = "janvier";
		}
		else if (mois.equals("02")) {
			mois = "février";
		}
		else if (mois.equals("03")) {
			mois = "mars";
		}
		else if (mois.equals("04")) {
			mois = "avril";
		}
		else if (mois.equals("05")) {
			mois = "mai";
		}
		else if (mois.equals("06")) {
			mois = "juin";
		}
		else if (mois.equals("07")) {
			mois = "juillet";
		}
		else if (mois.equals("08")) {
			mois = "aout";
		}
		else if (mois.equals("09")) {
			mois = "septembre";
		}
		else if (mois.equals("10")) {
			mois = "octobre";
		}
		else if (mois.equals("11")) {
			mois = "novembre";
		}
		else if (mois.equals("12")) {
			mois = "decembre";
		}

		return jour + " " + mois + " " + annee;
	}
}