package com.svn.app.wlt.api;

import com.svn.app.wlt.utility.Types;
import org.springframework.context.ApplicationContext;

public class ExchangeService {

    private IExchangeService service;

    public ExchangeService(ApplicationContext applicationContext, Types.Exchange exchange) {
        if(exchange.equals(Types.Exchange.BINANCE)){
            this.service = new BinanceService(applicationContext);
        }
    }

    public IExchangeService instance(){
        return this.service;
    }

}
