package castagnos.agent.client.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import castagnos.agent.client.vue.VueClient;
import castagnos.agent.modele.CellStyle;
import fr.miage.agents.api.model.Categorie;
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

public class PanierController{

	@FXML
	ListView<Produit> listeProduits;
	
	@FXML
	Button quit;
	
	public int context;

	
	public void loadPanier(ArrayList<Produit> list){
		Categorie c = new Categorie();
		c.nomCategorie = "boisson";
		Produit p = new Produit();
		p.idProduit = 1;
		p.descriptionProduit = "test";
		p.idCategorie = c;
		p.marque = "Coca";
		p.nomProduit = "Coca";
		p.prixProduit = 1;
        ObservableList<Produit> panier = FXCollections.observableArrayList(list);
        panier.add(p);
		this.listeProduits.setItems(panier);
		this.listeProduits.setCellFactory(new Callback<ListView<Produit>, javafx.scene.control.ListCell<Produit>>()
        {
            public ListCell<Produit> call(ListView<Produit> listeProduits)
            {
                return new CellStyle();
            }
        });
		if(context == 0){
			this.listeProduits.setDisable(true);
		}
    }
	
	@FXML
	public void quit(){	
		if(context == 1){
			Produit p = this.listeProduits.getSelectionModel().getSelectedItem();
			VueClient.agent.panier.add(p);
		}
		Stage stage = (Stage) this.quit.getScene().getWindow();
		stage.close();
	}

}
