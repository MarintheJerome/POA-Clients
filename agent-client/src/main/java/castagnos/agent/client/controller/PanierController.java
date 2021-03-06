package castagnos.agent.client.controller;

import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class PanierController{

	@FXML
	ListView<Produit> listeProduits;
	
	@FXML
	Button quit, supp;
	
	public int context;
	
	public VueClient parentController;
	
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
		if(context == 0){
			this.listeProduits.setDisable(true);
		}
		if(this.context == 1){
			this.supp.setDisable(true);
			this.supp.setVisible(false);
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
		if(this.context != 0){
			try{
				Produit p = this.listeProduits.getSelectionModel().getSelectedItem();
				this.parentController.ref.setText(p.idProduit+"");
				this.parentController.reference.setText(p.nomProduit);
				this.parentController.categorie.setText(p.idCategorie.nomCategorie);
				this.parentController.marque.setText(p.marque);
				this.parentController.prixProduit.setText(" " +p.prixProduit+" €");
			}
			catch(NullPointerException e){
				
			}
		}
	}

	@FXML
	public void delete(){
		try{
			Produit p = this.listeProduits.getSelectionModel().getSelectedItem();
			this.listeProduits.getItems().remove(p);
			VueClient.agent.panier.remove(p);
			String prixPanier = this.parentController.prixPanier.getText();
			
			this.parentController.prixPanier.setText(""+(Float.parseFloat(prixPanier)-p.prixProduit));
		}
		catch(NullPointerException e){
			System.out.println("Test");
		}
	}
}
