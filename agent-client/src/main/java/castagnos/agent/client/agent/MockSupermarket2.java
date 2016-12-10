package castagnos.agent.client.agent;

import java.io.IOException;
import java.util.ArrayList;

import castagnos.agent.client.behaviour.ReceiveBehaviour;
import castagnos.agent.client.behaviour.ReceiveBehaviourSupermarket;
import fr.miage.agents.api.message.interClients.DemandeEchange;
import fr.miage.agents.api.model.Categorie;
import fr.miage.agents.api.model.Produit;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.util.leap.Iterator;

public class MockSupermarket2 extends MockSupermarket{
	/**
     * Autre clients dans le réseau d'agent
     */
    public ArrayList<String> others = new ArrayList<String>();

    /**
     * Supermarché que l'on peut voir sur le réseau d'agent
     */
    private ArrayList<String> markets = new ArrayList<String>();

    public int nResponders = -1;

    /**
     * Messages
     */
    private ACLMessage message;

    /**
     * Quelques attributs utiles.
     */
    public final static String TYPEC = "Client";
    public final static String TYPEM = "Supermarché";
    public String SELF = "";

    private void creerProduit(){
    	this.creerBoisson();
    	this.creerHighTech();
    	this.creerLegume();
    }
    
    private void creerBoisson(){
    	Categorie cat = new Categorie();
		cat.idCategorie = 1;
		cat.nomCategorie = "Boisson";
    	Produit p1 = new Produit();
		p1.idProduit = 1;
		p1.idCategorie = cat;
		p1.marque = "CocaColaCompany";
		p1.nomProduit = "Fanta";
		p1.prixProduit = 2;
		Produit p2 = new Produit();
		p2.idProduit = 2;
		p2.idCategorie = cat;
		p2.marque = "PepsiCo";
		p2.nomProduit = "Orangina";
		p2.prixProduit = 1;
		int i = 0;
		while(i<10){
			stock.add(p1);
			stock.add(p2);
			i++;
		}
    }
    private void creerHighTech(){
    	Categorie cat = new Categorie();
		cat.idCategorie = 2;
		cat.nomCategorie = "HighTech";
    	Produit p1 = new Produit();
		p1.idProduit = 3;
		p1.idCategorie = cat;
		p1.marque = "HP";
		p1.nomProduit = "USB 8GB";
		p1.prixProduit = 16;
		Produit p2 = new Produit();
		p2.idProduit = 4;
		p2.idCategorie = cat;
		p2.marque = "Asus";
		p2.nomProduit = "ROG";
		p2.prixProduit = 1200;
		int i = 0;
		while(i<10){
			stock.add(p1);
			stock.add(p2);
			i++;
		}
    }
    private void creerLegume(){
    	Categorie cat = new Categorie();
		cat.idCategorie = 3;
		cat.nomCategorie = "Legume";
    	Produit p1 = new Produit();
		p1.idProduit = 5;
		p1.idCategorie = cat;
		p1.marque = "";
		p1.nomProduit = "Pomme";
		p1.prixProduit = 2;
		Produit p2 = new Produit();
		p2.idProduit = 6;
		p2.idCategorie = cat;
		p2.marque = "";
		p2.nomProduit = "Carrote";
		p2.prixProduit = 1;
		int i = 0;
		while(i<10){
			stock.add(p1);
			stock.add(p2);
			i++;
		}
    }

    protected void setup() {
    	this.creerProduit();
        Object[] args = getArguments();
        if(args != null){
            SELF = (String) args[0];
        }
        // Ajout dans le registre
        registerService(SELF);

        System.out.println("L'agent : "+SELF+" est opérationnel.");

        // Ajout du behavior de reception de message
        addBehaviour(new ReceiveBehaviourSupermarket(this));

        /*//Ouverture du client Main
        if(SELF.equals("MainClient")){
            VueClient.launchMain(this);
        }*/
    }

    /**
     * Méthode de retour des agents
     */
    public ArrayList<String> getOthers(String type) {
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType(type);
        dfd.addServices(sd);
        ArrayList<String> othersAgent = new ArrayList<String>();
        String info = "";
        try {
            for (DFAgentDescription desc : DFService.search(this, dfd)) {
                if(!desc.getName().getLocalName().equals(SELF)){
                    Iterator iter = desc.getAllServices();
                    while (iter.hasNext()) {
                        sd = (ServiceDescription) iter.next();
                        if(!sd.getName().equals(SELF)){
                            info += sd.getName()+" ";
                            othersAgent.add(sd.getName());
                        }
                    }
                }
            }
            if(!info.equals("")){
                System.out.print(SELF+" a trouvé les agents "+type+" : ");
                System.out.println(info);
            }
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        return othersAgent;
    }

    /**
     * Méthode d'ajout de l'agent dans le registre d'agent.
     */
    private void registerService(String name) {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(this.getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(TYPEM);
        sd.setName(name);
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }
}
