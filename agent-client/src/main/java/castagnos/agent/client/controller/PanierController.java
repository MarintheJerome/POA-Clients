package castagnos.agent.client.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import castagnos.agent.client.agent.AgentClient;
import fr.miage.agents.api.model.Produit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class PanierController  implements Initializable{

	@FXML
	ListView<Produit> listeProduits;
	
	@FXML
	Button quit;
	
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}
	
	public void loadPanier(ArrayList<Produit> list){
        ObservableList<Produit> panier = FXCollections.observableArrayList(list);
		this.listeProduits.setItems(panier);
    }
	
	@FXML
	public void quit(){	
		Stage stage = (Stage) this.quit.getScene().getWindow();
		stage.close();
	}

}
