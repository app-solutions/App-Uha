package fr.appsolutions.appuha.autres;

public class Cours {
	
	private String id;
	private String start;
	private String stop;
	private String duree;
	private String nom;
	private String salle;
	private String enseignant;
	
	public Cours(String id, String start, String stop, String duree, String nom, String salle, String enseignant) {
		this.id = id;
		this.start = start;
		this.stop = stop;
		this.duree = duree;
		this.nom = nom;
		this.salle = salle;
		this.enseignant = enseignant;
	}

	public String getId() {
		return id;
	}

	public String getStart() {
		return start;
	}

	public String getStop() {
		return stop;
	}

	public String getDuree() {
		return duree;
	}

	public String getNom() {
		return nom;
	}


	public String getSalle() {
		return salle;
	}

	public String getEnseignant() {
		return enseignant;
	}
	
	
}
