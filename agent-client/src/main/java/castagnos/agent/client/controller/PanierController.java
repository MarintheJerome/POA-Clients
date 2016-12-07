package castagnos.agent.client.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import castagnos.agent.modele.CellStyle;
import fr.miage.agents.api.model.Produit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

public class PanierController  implements Initializable{

	@FXML
	ListView<Produit> listeProduits;
	
	@FXML
	Button quit;
	
	public int context;
	
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}
	
	public void loadPanier(ArrayList<Produit> list){
        ObservableList<Produit> panier = FXCollections.observableArrayList(list);
		this.listeProduits.setItems(panier);
		this.listeProduits.setCellFactory(new Callback<ListView<Produit>, javafx.scene.control.ListCell<Produit>>()
        {
            public ListCell<Produit> call(ListView<Produit> listeProduits)
            {
                return new CellStyle();
            }
        });
    }
	
	@FXML
	public void quit(){	
		Stage stage = (Stage) this.quit.getScene().getWindow();
		stage.close();
	}

}
