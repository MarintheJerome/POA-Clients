package castagnos.agent.client.vue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import castagnos.agent.client.agent.AgentClient;
import castagnos.agent.modele.CellStyle;
import castagnos.agent.client.controller.NegociationController;
import castagnos.agent.client.controller.PanierController;
import fr.miage.agents.api.message.recherche.Rechercher;
import fr.miage.agents.api.message.util.ResultatCategorie;
import fr.miage.agents.api.model.Categorie;
import fr.miage.agents.api.model.Produit;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Created by arnaud on 10/11/2016.
 */
public class VueClient extends Application{

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
	ComboBox listProduits, listClient;

	private static AgentClient agent;

	@FXML
	Slider distance;

	Stage stage;

	@FXML
	public void rechercher(){
		Rechercher recherche = new Rechercher();
		recherche.idProduit = Long.parseLong(this.reference.getText());
		/**
		 * Legume
		 * Produit Laitier
		 * Boisson
		 * Produit entretient
		 * Cosmétique
		 * High-tech
		 */
		recherche.nomCategorie = "Boisson";
		recherche.marque = this.marque.getText();
		recherche.prixMax = Float.parseFloat(this.prixMax.getText());
		recherche.prixMin = Float.parseFloat(this.prixMin.getText());
		try {
			this.agent.sending(recherche);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void afficheResRecherche(List<Produit> list) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("productList.fxml"));
		BorderPane page = (BorderPane) loader.load();
		PanierController controller = loader.getController();
		
	    Stage dialogStage = new Stage();
	    setStage(dialogStage);
	    dialogStage.setTitle("Resultat Recherche");
	    dialogStage.initModality(Modality.WINDOW_MODAL); 
	    Scene scene = new Scene(page);
	    dialogStage.setScene(scene);
	    System.out.println(controller);
	    controller.loadPanier((ArrayList<Produit>)list);
	    dialogStage.show();
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

	    AnchorPane page = loader.load();
		NegociationController controller = loader.getController();

	    Stage dialogStage = new Stage();
	    setStage(dialogStage);
	    dialogStage.setTitle("Simple Demande");
	    dialogStage.initModality(Modality.WINDOW_MODAL);
	    Scene scene = new Scene(page);
	    dialogStage.setScene(scene);
		controller.loadRessource(agent);
	    dialogStage.show();
	}
	
	@FXML
	public void voirPanier() throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("productList.fxml"));
		BorderPane page = (BorderPane) loader.load();
		PanierController controller = loader.getController();
		
	    Stage dialogStage = new Stage();
	    setStage(dialogStage);
	    dialogStage.setTitle("Panier");
	    dialogStage.initModality(Modality.WINDOW_MODAL); 
	    Scene scene = new Scene(page);
	    dialogStage.setScene(scene);
	    System.out.println(controller);
	    controller.loadPanier(agent.panier);
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
		return agent.getAgentName();
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

	public static void launchMain(AgentClient ac){
		agent = ac;
		launch();
	}
}
