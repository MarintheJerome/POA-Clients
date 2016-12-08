package castagnos.agent.client.controller;

import castagnos.agent.client.agent.AgentClient;
import castagnos.agent.client.common.Popup;
import fr.miage.agents.api.message.interClients.DemandeEchange;
import fr.miage.agents.api.model.Produit;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by jerome on 06/12/2016.
 */
public class NegociationController implements Initializable{

    @FXML
    private RadioButton radioSimple;

    @FXML
    private ToggleGroup demandeType;

    @FXML
    private RadioButton radioReponse;

    @FXML
    private RadioButton radioReponseNegative;

    @FXML
    private ComboBox<String> listProduits;

    @FXML
    private TextField quantiteNegociation;

    @FXML
    private Button demander;

    @FXML
    private ComboBox<String> listeClient;

    private AgentClient agentClient;

    public void initialize(URL location, ResourceBundle resources) {
        // Remplissage combobox produit. A virer une fois que tout sera bien récupéré
        listProduits.getItems().add("Sauce tomate");
        listProduits.getItems().add("Purée");
        listProduits.getItems().add("Sandwich trop bon");
        listProduits.getItems().add("Quenelle");
        listProduits.getItems().add("Kebab");

        // On force le textfield pour la quantité à contenir que des nombres
        quantiteNegociation.textProperty().addListener(new ChangeListener<String>() {

            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (!newValue.matches("\\d*")) {
                quantiteNegociation.setText(newValue.replaceAll("[^\\d]", ""));
            }
            }
        });
    }

    public void loadRessource(AgentClient ac){
        agentClient = ac;
        ArrayList<String> listAgents = agentClient.getOthers("Client");
        for(String nomAgent : listAgents){
            listeClient.getItems().add(nomAgent);
        }
    }

    @FXML
    public void demander() throws IOException {
        if(!checkErrors()){
            Produit produit = new Produit();
            produit.nomProduit = listProduits.getSelectionModel().getSelectedItem();
            DemandeEchange demande = new DemandeEchange(produit, Integer.parseInt(quantiteNegociation.getText()));
            agentClient.sendProduit(produit, "printer", Integer.parseInt(quantiteNegociation.getText()), AgentClient.TYPEC);
        }
    }

    private boolean checkErrors() {
        if(listeClient.getSelectionModel().isEmpty()){
            Popup.popUpError("Erreur", "Veuillez choisir un client");
            return true;
        }
        if(listProduits.getSelectionModel().isEmpty()){
            Popup.popUpError("Erreur", "Veuillez choisir un produit");
            return true;
        }
        if(quantiteNegociation.getText().equals("")){
            Popup.popUpError("Erreur", "Veuillez rentrer une quantité");
            return true;
        }
        return false;
    }
}
