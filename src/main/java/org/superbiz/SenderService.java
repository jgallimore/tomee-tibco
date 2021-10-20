/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.superbiz;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;

@Stateless
public class SenderService implements SenderServiceRemote {

    @Resource(name = "TibcoConnectionFactory")
    protected ConnectionFactory cf;

    @Resource(name = "server")
    protected Topic topic;

    @Override
    public void send(final String payload) throws JMSException {
        process(cf, topic, payload, Session.AUTO_ACKNOWLEDGE);
    }

    protected void process(final ConnectionFactory cf, final Topic topic, final String payload, final int acknowledgeMode) throws JMSException {
        Connection connection = null;
        Session session = null;

        try {
            connection = cf.createConnection();
            connection.start();

            session = connection.createSession(false, acknowledgeMode);
            final MessageProducer producer = session.createProducer(null);

            producer.send(topic, session.createTextMessage(payload));
        } finally {
            if (session != null) session.close();
            if (connection != null) connection.close();
        }
    }


}
