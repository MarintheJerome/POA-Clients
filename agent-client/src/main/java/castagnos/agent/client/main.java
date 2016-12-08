package castagnos.agent.client;

import castagnos.agent.client.agent.AgentClient;
import castagnos.agent.client.agent.MockSupermarket;
import fr.miage.agents.api.message.recherche.Rechercher;
import fr.miage.agents.api.message.relationclientsupermarche.Achat;
import fr.miage.agents.api.model.Produit;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

/**
 * Created by jerome on 14/11/2016.
 */
public class main {
    public static void main(String[] args){
        jade.core.Runtime runtime = jade.core.Runtime.instance();
        ProfileImpl p =new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, "localhost");
        p.setParameter(Profile.GUI, "true");
        ContainerController cc =runtime.createMainContainer(p);
        AgentController receiver;
        AgentController sender;
        AgentController printer;
        try {
        	MockSupermarket sm1 = new MockSupermarket();
            sm1.SELF = "SuperU";
            AgentController supermarket1 = cc.acceptNewAgent("SuperU", sm1);
            supermarket1.start();
            
            MockSupermarket sm2 = new MockSupermarket();
            sm2.SELF = "Auchan";
            AgentController supermarket2 = cc.acceptNewAgent("Supermarket2", sm2);
            supermarket2.start();
        	
            Object valeurs[] = {"MainClient"};
            receiver = cc.createNewAgent("MainClient", "castagnos.agent.client.agent.AgentClient", valeurs);
            receiver.start();
            Object valeurs2[] = {"sender"};
            sender = cc.createNewAgent("sender", "castagnos.agent.client.agent.AgentClient", valeurs2);
            sender.start();
            Object valeurs3[] = {"printer"};
            printer = cc.createNewAgent("printer", "castagnos.agent.client.agent.AgentClient", valeurs3);
            printer.start();
            AgentClient ac = new AgentClient();
            ac.SELF = "ninja";
            AgentController ninja = cc.acceptNewAgent("Ninja", ac);
            ninja.start();
            
            ac.sendRecherche(new Rechercher());
            ac.sendAchat();
            
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
