package org.superbiz;

import javax.ejb.Remote;
import javax.jms.JMSException;

@Remote
public interface SenderServiceRemote {
    void send(String payload) throws JMSException;
}
