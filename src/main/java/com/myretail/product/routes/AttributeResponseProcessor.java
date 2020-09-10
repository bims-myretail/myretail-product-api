package com.myretail.product.routes;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.myretail.product.dto.ProductDTO;

public class AttributeResponseProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {


		ProductDTO product = new ProductDTO();

		product.setAttributes((Map<String, Object>) ((Map<String, Object>) exchange.getIn().getBody(Map.class)).get("attribute"));

		exchange.getIn().setBody(product);

	}

}
