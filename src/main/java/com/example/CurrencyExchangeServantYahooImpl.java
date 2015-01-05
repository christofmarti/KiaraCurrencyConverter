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
 * @file CurrencyExchangeServantYahooImpl.java
 * This file contains the servant implementation.
 *
 */

package com.example;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Class where the actual implementation of the procedures is located.
 *
 * @author Christof Marti.
 *
 */
class CurrencyExchangeServantYahooImpl extends CurrencyExchangeServant {

    /*
     * !
     * 
     * @brief This method implements the proxy part of the protocol for the
     * operation lookup. It has to be implemented by the child classes.
     */

    public ExchangeRate lookup(/* in */java.lang.String from, /* in */
            java.lang.String to) 
    {
        //TODO: Implement lookup to yahoo service
        // get info from yahoo with the following command:
        // curl -X GET -H "Content-type: application/json" 
        //  -H "Accept: application/json" "http://download.finance.yahoo.com/d/
        //  quotes.csv?s=EURCHF=X&f=snl1d1t1ab&e=.cs"
        return null;
    }

    /*
     * !
     * 
     * @brief This method implements the proxy part of the protocol for the
     * operation convert. It has to be implemented by the child classes.
     */

    public float convert(/* in */java.lang.String from, /* in */
            java.lang.String to, /* in */float value) {

        ExchangeRate exchange = this.lookup(from, to);
        if (exchange != null) {
            if (value == 0) value = 1;
            return (float)(exchange.getRate() * value);
        }
        return (float) 0.0;
    }

}
