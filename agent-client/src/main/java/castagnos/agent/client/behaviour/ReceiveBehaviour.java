package castagnos.agent.client.behaviour;
import fr.miage.agents.api.message.Message;
import fr.miage.agents.api.message.interClients.DemandeEchange;
import fr.miage.agents.api.message.interClients.ReponseEchange;
import fr.miage.agents.api.model.Produit;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

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
                            //DemandeEchange demandeEchange = (DemandeEchange) message;
                            ACLMessage reply = aclMessage.createReply();

                            System.out.println("L'agent " + myAgent.getLocalName()+" a reçu une demande d'échange de "+aclMessage.getSender().getLocalName());
                            try {
                                //Produit produit = demandeEchange.produitDemande;
                                reply.setContentObject(new ReponseEchange(true, null, 12, 24));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            myAgent.send(reply);
                            break;
                        // à traiter
                        case ReponseEchange:
                            System.out.println("L'agent "+myAgent.getLocalName()+" a récupéré la réponse de l'échange");
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
                // On dort 1s pour pas que ça nique le cpu
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}