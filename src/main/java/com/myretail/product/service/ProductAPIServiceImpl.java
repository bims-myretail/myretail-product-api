package com.myretail.product.service;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretail.product.dto.ProductDTO;

@Service
public class ProductAPIServiceImpl implements ProductAPIService {

	@Autowired
	CamelContext context;

	@Override
	public Object findProductDetail(String id) {
		ProducerTemplate template = context.createProducerTemplate();

		Object future = template.requestBody("direct:productsearch", id);

		return future;
	}

	@Override
	public Object updateProduct(ProductDTO product) {

		ProducerTemplate template = context.createProducerTemplate();

		ObjectMapper mapper = new ObjectMapper();

		Object future = null;
		try {
			future = template.requestBody("direct:productUpdate", mapper.writeValueAsString(product));
		} catch (CamelExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return future;
	}

}
