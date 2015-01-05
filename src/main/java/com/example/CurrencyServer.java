/* KIARA - Middleware for efficient and QoS/Security-aware invocation of services and exchange of messages
 *
 * Copyright (C) 2014 Proyectos y Sistemas de Mantenimiento S.L. (eProsima)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library. If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * @file CurrencyServer.java
 * This file contains the code to start the server.
 *
 */

package com.example;

import org.fiware.kiara.Context;
import org.fiware.kiara.Kiara;
import org.fiware.kiara.server.Server;
import org.fiware.kiara.server.Service;
import org.fiware.kiara.serialization.Serializer;
import org.fiware.kiara.transport.ServerTransport;

/**
 * Class that acts as the main server entry point.
 *
 * @author Christof Marti
 *
 */
public class CurrencyServer {

    public static void main(String[] args) throws Exception {

        System.out.println("CurrencyExchangeServer");

        Context context = Kiara.createContext();
        Server server = context.createServer();

        // create and register yahoo converter service
        CurrencyExchangeServant yahooConverter = new
        CurrencyExchangeServantYahooImpl();
        Service yahooService = context.createService();
        yahooService.register(yahooConverter);
        server.addService(yahooService, "tcp://0.0.0.0:9090", "cdr");

        // create and register fake converter service
        CurrencyExchangeServant fakeConverter = new CurrencyExchangeServantFakeImpl();
        ServerTransport transport = context.createServerTransport("tcp://0.0.0.0:8080");
        Serializer serializer = context.createSerializer("cdr");
        Service fakeService = context.createService();
        fakeService.register(fakeConverter);
        server.addService(fakeService, transport, serializer);

        server.run();

    }

}
