package castagnos.agent.client.vue;

import java.io.IOException;

import castagnos.agent.client.agent.AgentClient;

import fr.miage.agents.api.message.demande.Recherche;
import fr.miage.agents.api.message.reponse.ResultatCategorie;
import fr.miage.agents.api.model.Categorie;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Created by arnaud on 10/11/2016.
 */
public class VueClient extends Application {

	@FXML
	Text ref, prixProduit, prixPanier, kilometer;
	@FXML
	TextField recherche, quantite, reference, prixMax, prixMin, categorie, marque, quantiteNegociation;
	@FXML
	Button rechercher, ajouter, annuler, negocier, commander, demander;
	@FXML
	ImageView image;
	@FXML
	RadioButton radioSimple, radioReponse, radioReponseNegative;
	@FXML
	ComboBox listProduit, listClient;

	AgentClient agent;

	@FXML
	Slider distance;

	Stage stage;
	
	@FXML
	public void rechercher(){
		Recherche recherche = new Recherche();
		recherche.reference = this.reference.getText();
		recherche.categorie = this.demandeCategorie(this.categorie.getText().toString());
		recherche.marque = this.marque.getText();
		recherche.prixMax = Double.parseDouble(this.prixMax.getText());
		recherche.prixMin = Double.parseDouble(this.prixMin.getText());
		//TODO : Faire une fenêtre de selection de l'article
	}
	
	private Categorie demandeCategorie(String nomCategorie){
		//TODO: avoir la réponse contenant toutes les catégories pour remplacé le null
		ResultatCategorie res = null;
		for(Categorie c: res.categorieList){
			if(c.nomCategorie.equals(nomCategorie)){
				return c;
			}
		}
		return null;
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
		this.prixProduit.setText("");
	}
	
	@FXML
	public void negocier() throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Negociation.fxml"));
	    AnchorPane page = (AnchorPane) loader.load();
	    Stage dialogStage = new Stage();
	    setStage(dialogStage);
	    dialogStage.setTitle("Simple Demande");
	    dialogStage.initModality(Modality.WINDOW_MODAL);
	    Scene scene = new Scene(page);
	    dialogStage.setScene(scene);
	    dialogStage.show();
	}
	
	private void setStage(Stage stage){
		this.stage = stage;
	}
	
	@FXML
	public void demander(){
		Stage stage = (Stage) this.demander.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	public void commander(){
		//TODO : Passer la commande au supermarché
	}
	
	@FXML
	public void setDistance(){
		int km = (int) this.distance.getValue();
		if(km>1){
			this.kilometer.setText(km + " kms");
		}
		else{
			this.kilometer.setText(km + " km");
		}
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
		this.stage = primaryStage;

	}

	public void launcher(AgentClient ac){
		agent = ac;
		launch();
	}
}
