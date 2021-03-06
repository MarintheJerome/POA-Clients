package castagnos.agent.client.behaviour;
import fr.miage.agents.api.message.Message;
import fr.miage.agents.api.message.interClients.DemandeEchange;
import fr.miage.agents.api.message.interClients.ReponseEchange;
import fr.miage.agents.api.message.recherche.ResultatRecherche;
import fr.miage.agents.api.message.relationclientsupermarche.ResultatAchat;
import fr.miage.agents.api.model.Produit;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.IOException;
import java.util.HashMap;

import castagnos.agent.client.agent.AgentClient;

/**
* Created by jerome on 13/11/2016.
*/
public class ReceiveBehaviour extends CyclicBehaviour {

    public ReceiveBehaviour(Agent agent) {
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
                    switch(message.type){
                        // cas à traiter
                        case DemandeEchange:
                            DemandeEchange demandeEchange = (DemandeEchange) message;
                            ACLMessage reply = aclMessage.createReply();

                            System.out.println("L'agent "+myAgent.getLocalName()+" a reçu une demande d'échange de "+aclMessage.getSender().getLocalName());
                            System.out.println("L'agent "+myAgent.getLocalName()+" veut "+demandeEchange.quantiteDemande+" "+demandeEchange.produitDemande.nomProduit);
                            try {
                                Produit produit = demandeEchange.produitDemande;;
                                double prix = produit.prixProduit * demandeEchange.quantiteDemande;

                                double random = Math.random();
                                if(random > 0.5){
                                    for(int i =0;i<demandeEchange.quantiteDemande;i++){
                                        ((AgentClient) myAgent).panier.add(demandeEchange.produitDemande);
                                    }
                                    System.out.println("L'agent "+myAgent.getLocalName()+" accepte l'échange");
                                    reply.setContentObject(new ReponseEchange(true, produit, prix, null, 0));
                                }
                                else{
                                    System.out.println("L'agent "+myAgent.getLocalName()+" veut une compensation. Il veut une carotte");
                                    Produit compensation = new Produit();
                                    compensation.nomProduit = "Carotte";
                                    compensation.prixProduit = 1;
                                    for(int i =0;i<demandeEchange.quantiteDemande;i++){
                                        ((AgentClient) myAgent).panier.add(demandeEchange.produitDemande);
                                    }
                                    reply.setContentObject(new ReponseEchange(true, produit, prix, compensation, 1  ));
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            myAgent.send(reply);
                            break;
                        // à traiter
                        case ReponseEchange:
                            ReponseEchange reponseEchange = (ReponseEchange) message;
                            if(reponseEchange.compensation == null) {
                                System.out.println("L'agent " + myAgent.getLocalName() + " a récupéré la réponse de l'échange et est très content ! :)");
                            }else{
                                System.out.println("L'agent" + myAgent.getLocalName() + " accepte la compensation. L'échange a donc eu lieu");
                                ((AgentClient) myAgent).panier.add(reponseEchange.compensation);
                            }
                            break;
                        case ResultatRecherche:
                        	ResultatRecherche res = (ResultatRecherche) message;
                        	Produit addPanier = null;
                        	for(Produit p : res.produitList){
                        		if(addPanier == null){
                        			addPanier = p;
                        		}
                        		else if(p.prixProduit<addPanier.prixProduit){
                        			addPanier = p;
                        		}
                        	}
                        	AgentClient agent = (AgentClient) myAgent;
                        	agent.panier.add(addPanier);
                        	System.out.println("L'agent "+myAgent.getLocalName()+" ajoute le produit "+ addPanier.idProduit);
                        	break;
                        case ResultatAchatClient:
                        	ResultatAchat resAchat = (ResultatAchat) message;
                        	System.out.println("L'agent "+myAgent.getLocalName()+" a finit ses achats et à acheté "+resAchat.courses.size() + " produits");
                            ((AgentClient) myAgent).panier.clear();
                            break;
                        default:
                            break;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                this.block();
            }
            try {
                // On dort 1s pour pas utiliser trop de CPU
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}