package fr.appsolutions.appuha;

public class Composer {
	public static final String TAG = Composer.class.getSimpleName();
	
	public String date;
	public String name;
	public String room;
	public String teacher;
	
	public Composer(String date, String name, String room, String teacher) {
		this.date = date;
		this.name = name;
		this.room = room;
		this.teacher = teacher;
	}
}