package org.superbiz;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;

public class SimpleClient {

    public static void main(String[] args) {

        try {
            final Properties properties = new Properties();
            properties.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.RemoteInitialContextFactory");
            properties.setProperty(Context.PROVIDER_URL, "http://localhost:8080/tomee/ejb");
            final Context context = new InitialContext(properties);

            final SenderServiceRemote senderService = (SenderServiceRemote) context.lookup("SenderServiceRemote");
            System.out.println("Sending payload....");
            senderService.send("This is a test");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
