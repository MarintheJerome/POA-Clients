package castagnos.agent.client.behaviour;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import castagnos.agent.client.agent.MockSupermarket;
import fr.miage.agents.api.message.Message;
import fr.miage.agents.api.message.interClients.ReponseEchange;
import fr.miage.agents.api.message.recherche.Rechercher;
import fr.miage.agents.api.message.recherche.ResultatRecherche;
import fr.miage.agents.api.message.relationclientsupermarche.Achat;
import fr.miage.agents.api.message.relationclientsupermarche.ResultatAchat;
import fr.miage.agents.api.model.Categorie;
import fr.miage.agents.api.model.Produit;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class ReceiveBehaviourSupermarket  extends CyclicBehaviour {
	public ReceiveBehaviourSupermarket(Agent agent) {
        super(agent);
    }

    // Méthode qui determine le comportement de l'agent
    public void action() {
        while (true) {
            // On fait de l'attente active sur le réception du message.
            // Toutes les 1s, l'agent vérifie s'il n'a pas reçu de message
            ACLMessage aclMessage = myAgent.receive();
            if (aclMessage != null) {
                try {
                    // On chope le message qui a été envoyé
                    Message message = ((Message)aclMessage.getContentObject());

                    // On switch sur ce type et on fait un traitement différent pour chaque
                    ACLMessage reply = aclMessage.createReply();
                    switch(message.type){
                    	case Recherche:
                    		Rechercher recherche = (Rechercher) message;
                    		ResultatRecherche resRecherche = new ResultatRecherche();
                    		ArrayList<Produit> listProduit = new ArrayList<Produit>();
                    		Categorie cat = new Categorie();
                    		cat.idCategorie = 1;
                    		cat.nomCategorie = "Boisson";
                    		Produit p1 = new Produit();
                    		p1.idProduit = 1;
                    		p1.idCategorie = cat;
                    		p1.marque = "CocaColaCompany";
                    		p1.nomProduit = "CocaCola";
                    		p1.prixProduit = 2;
                    		listProduit.add(p1);
                    		Produit p2 = new Produit();
                    		p2.idProduit = 2;
                    		p2.idCategorie = cat;
                    		p2.marque = "PepsiCo";
                    		p2.nomProduit = "Pepsi";
                    		p2.prixProduit = 1;
                    		listProduit.add(p2);
                    		resRecherche.produitList = listProduit;
                    		reply.setContentObject(resRecherche);
                    		break;
                    	case InitierAchat:
                    		Achat achat = (Achat) message;
                    		Map<Integer,Integer> liste = achat.listeCourses;
                    		MockSupermarket agent = (MockSupermarket) myAgent;
                    		Map<Boolean,Integer> realProduit = new HashMap<Boolean,Integer>();
                    		realProduit.put(true,1);
                    		Map<Produit,Map<Boolean,Integer>> realAchat = new HashMap<Produit,Map<Boolean,Integer>>();
                    		realAchat.put(agent.stock.get(1), realProduit);
                    		ResultatAchat resAchat = new ResultatAchat();                    		
                    		resAchat.courses = realAchat;
                    		reply.setContentObject(resAchat);
                    		break;
                    }
                    myAgent.send(reply);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                this.block();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
