package com.myretail.product.routes;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretail.product.dto.ProductDTO;

public class ProductAgreegationStrategyBuilder implements AggregationStrategy {

	private ProductDTO product = new ProductDTO();
	ObjectMapper mapper = new ObjectMapper();

	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {

		readNewExchange(newExchange);
		if (oldExchange != null) {
			oldExchange.getIn().setBody(product);
			return oldExchange;
		} else {
			return newExchange;
		}

	}

	private void readNewExchange(Exchange newExchange) {
		String body = newExchange.getIn().getBody(String.class);

		try {
			if (body != null) {
				ProductDTO response = mapper.readValue(body, ProductDTO.class);
				if (response.getCurrentPrice() != null) {
					product.setCurrentPrice(response.getCurrentPrice());
					product.setProductID(response.getCurrentPrice().getProductID());
				}
				if (response.getAttributes() != null) {
					product.setAttributes(response.getAttributes());
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
