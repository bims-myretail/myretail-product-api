package com.myretail.product.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.myretail.product.dto.ProductDTO;

public class PriceRequestProcesser implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		ProductDTO product = (ProductDTO) exchange.getIn().getBody(ProductDTO.class);

		exchange.getIn().setBody(product.getCurrentPrice());

	}

}
