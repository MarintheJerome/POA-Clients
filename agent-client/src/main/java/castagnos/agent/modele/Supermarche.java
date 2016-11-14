package castagnos.agent.modele;

import java.util.ArrayList;

public class Supermarche {

	private String nom;
	private double distance;
	private ArrayList<Article> produitsVentes = new ArrayList<Article>();
	
	public Supermarche(String nom, ArrayList<Article> produitsVentes) {
		super();
		this.nom = nom;
		this.distance = Math.random();
		this.produitsVentes = produitsVentes;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public ArrayList<Article> getProduitsVentes() {
		return produitsVentes;
	}

	public void setProduitsVentes(ArrayList<Article> produitsVentes) {
		this.produitsVentes = produitsVentes;
	}
}
