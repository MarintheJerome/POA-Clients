package castagnos.agent.client.vue;

import castagnos.agent.client.agent.AgentClient;
import fr.miage.agents.api.message.demande.Recherche;
import javafx.application.Application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by arnaud on 10/11/2016.
 */
public class VueClient extends Application {

	@FXML
	Text ref, prixProduit, prixPanier;
	@FXML
	TextField recherche, quantite, reference, prixMax, prixMin, categorie, marque;
	@FXML
	Button rechercher, ajouter, annuler, negocier, commander;
	@FXML
	ImageView image;

	AgentClient agent;

	@FXML
	Slider distance;

	
	@FXML
	public void rechercher(){
		Recherche recherche = new Recherche();
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
		this.reference.clear();
		this.categorie.clear();
		this.marque.clear();
		this.prixMax.clear();
		this.prixMin.clear();
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
		return "Client";
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

	public void launcher(AgentClient ac){
		agent = ac;
		launch();
	}
}
