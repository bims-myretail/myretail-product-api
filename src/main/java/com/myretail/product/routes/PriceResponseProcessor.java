package com.myretail.product.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.myretail.product.dto.PriceDTO;
import com.myretail.product.dto.ProductDTO;

public class PriceResponseProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		PriceDTO price = (PriceDTO) exchange.getIn().getBody(PriceDTO.class);
		
		ProductDTO product = new ProductDTO();
		
		product.setCurrentPrice(price);

		exchange.getIn().setBody(product);

	}

}
