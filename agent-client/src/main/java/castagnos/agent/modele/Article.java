package castagnos.agent.modele;

public class Article {

	private String nom;
	private String marque;
	private String description;
	private String categorie;
	private double prix;
	
	public Article(String nom, String marque, String description, String categorie) {
		super();
		this.nom = nom;
		this.marque = marque;
		this.description = description;
		this.categorie = categorie;
	}
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getMarque() {
		return marque;
	}
	
	public void setMarque(String marque) {
		this.marque = marque;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCategorie() {
		return categorie;
	}
	
	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}
	
	
}
