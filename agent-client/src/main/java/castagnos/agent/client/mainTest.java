package castagnos.agent.client;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

/**
 * Created by jerome on 14/11/2016.
 */
public class mainTest {
    public static void main(String[] args){
        jade.core.Runtime runtime = jade.core.Runtime.instance();
        ProfileImpl p =new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, "localhost");
        p.setParameter(Profile.GUI, "true");
        ContainerController cc =runtime.createMainContainer(p);
        AgentController receiver;
        AgentController sender;
        try {
            receiver = cc.createNewAgent("receiver", "castagnos.agent.client.agent.AgentClient", null);
            receiver.start();
            sender = cc.createNewAgent("sender", "castagnos.agent.client.agent.AgentClient", null);
            sender.start();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
