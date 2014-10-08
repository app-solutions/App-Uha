package fr.appsolutions.convertisseur;

public class Cours
{
	private String id;
	private String debut;
	private String fin;
	private String modif;
	private String duree;
	private String nom;
	private String salle;
	private String prof;

	public Cours(String id, String debut, String fin, String modif, String duree, String nom, String salle, String prof)
	{
		this.id = id;
		this.debut = debut;
		this.fin = fin;
		this.modif = modif;
		this.duree = duree;
		this.nom = nom;
		this.salle = salle;
		this.prof = prof;
	}

	public String getId()
	{
		return this.id;
	}

	public String getDebut()
	{
		return this.debut;
	}

	public String getFin()
	{
		return this.fin;
	}

	public String getModif()
	{
		return this.modif;
	}

	public String getDuree()
	{
		return this.duree;
	}

	public String getNom()
	{
		return this.nom;
	}

	public String getSalle()
	{
		return this.salle;
	}

	public String getProf()
	{
		return this.prof;
	}
}