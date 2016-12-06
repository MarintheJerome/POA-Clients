package castagnos.agent.client.controller;

import castagnos.agent.client.agent.AgentClient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.awt.event.ActionEvent;
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
    private ComboBox<?> listProduits;

    @FXML
    private TextField quantiteNegociation;

    @FXML
    private Button demander;

    @FXML
    private ComboBox<String> listeClient;

    private AgentClient agentClient;

    public void initialize(URL location, ResourceBundle resources) {
       // On récupère la liste des autres agents clients présents
       ArrayList<String> listAgents = agentClient.getOthers("Client");
       for(String nomAgent : listAgents){
           listeClient.getItems().add(nomAgent);
       }
    }

    @FXML
    public void demander() {

    }

    public void setAgent(AgentClient client){
        this.agentClient = client;
    }
}
