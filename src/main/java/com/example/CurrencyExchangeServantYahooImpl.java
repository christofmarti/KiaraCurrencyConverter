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

    public ExchangeRate lookup(String fromCurrency, String toCurrency)
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
    public float convert(String fromCurrency, String toCurrency, float amount) {
        ExchangeRate exchange = this.lookup(fromCurrency, toCurrency);
        if (exchange != null) {
            if (amount == 0) amount = 1;
            return (float)(exchange.getRate() * amount);
        }
        return (float) 0.0;
    }

}
