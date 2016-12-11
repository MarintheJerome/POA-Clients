package castagnos.agent.client.agent;

import java.util.ArrayList;
import fr.miage.agents.api.model.Produit;
import jade.core.Agent;

public abstract class MockSupermarket extends Agent{

    public ArrayList<Produit> stock = new ArrayList<Produit>();
    
    public String SELF = "";

    protected void setup(){}

    public ArrayList<String> getOthers(String type){
    	return null;
    }
}
