package org.superbiz;

import javax.annotation.PostConstruct;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.logging.Logger;

/**
 * This message driven bean receives messages from Tibco and echos them to the console
 */
public class TibcoReceiver implements MessageListener {

    private final Logger logger = Logger.getLogger(TibcoReceiver.class.getName());

    @PostConstruct
    public void start() {
        logger.info("*** Tibco MDB started ***");
    }

    @Override
    public void onMessage(final Message message) {
        if (! TextMessage.class.isInstance(message)) {
            return; // ignore anything that is not a text message
        }

        try {
            final TextMessage textMessage = TextMessage.class.cast(message);
            logger.info("Received message: " + textMessage.getText());
        } catch (JMSException e) {
            logger.severe("Exception occurred: " + e.getMessage());
        }
    }
}
