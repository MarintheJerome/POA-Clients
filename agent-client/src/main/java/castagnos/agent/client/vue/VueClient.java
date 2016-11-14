package castagnos.agent.client.vue;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class VueClient extends Application{

	@FXML
	Text ref, prixProduit, prixPanier;
	@FXML
	TextField recherche, quantite, article;
	@FXML
	Button rechercher, ajouter, annuler, negocier, commander;
	@FXML
	ImageView image;
	
	@FXML
	public void rechercher(){
		//TODO : Faire une fenêtre de selection de l'article
	}
	
	@FXML
	public void ajouter(){
		//TODO : Faire l'ajout au panier du client
	}
	
	@FXML
	public void annuler(){
		this.recherche.clear();
		this.quantite.clear();
		this.article.clear();
		this.ref.setText("");
		this.prixProduit.setText("");
	}
	
	@FXML
	public void negocier(){
		//TODO : Faire une fenêtre de négociation avec une autre client
	}
	
	@FXML
	public void commander(){
		//TODO : Passer la commande au supermarché
	}
	
	private String getNomClient(){
		//TODO : méthode de recherche du nom du client
		return "Client 1";
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader preloader = new FXMLLoader(getClass().getClassLoader().getResource("Client.fxml"));
		final Node node = preloader.load();
		final BorderPane root = new BorderPane(node); 
		final Scene scene = new Scene(root); 
		primaryStage.setTitle(getNomClient()); 
		primaryStage.setScene(scene); 
		primaryStage.show();
		
	}

	public static void main(String[] args) {
		Application.launch(VueClient.class,args);
	}
}
