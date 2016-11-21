package castagnos.agent.modele;

import java.util.ArrayList;

public class Commande {
	
	private ArrayList<Article> commandeProduits;
	
	public Commande() {
		this.commandeProduits = new ArrayList<Article>();
	}

	public Commande(ArrayList<Article> commandeProduits) {
		super();
		this.commandeProduits = commandeProduits;
	}
	
	public void addArticle(Article anArticle) {
		this.commandeProduits.add(anArticle);
	}
	
	public void removeArticle(Article anArticle) {
		this.commandeProduits.remove(anArticle);
	}

	public ArrayList<Article> getCommandeProduits() {
		return commandeProduits;
	}

	public void setCommandeProduits(ArrayList<Article> commandeProduits) {
		this.commandeProduits = commandeProduits;
	}
}
