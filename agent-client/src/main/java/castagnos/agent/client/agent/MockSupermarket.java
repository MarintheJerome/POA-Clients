package castagnos.agent.client.agent;

import java.util.ArrayList;
import fr.miage.agents.api.model.Produit;

public interface MockSupermarket{
    public ArrayList<String> others = new ArrayList<String>();

    public ArrayList<Produit> stock = new ArrayList<Produit>();

    void setup();

    public ArrayList<String> getOthers(String type);
}
