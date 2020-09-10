package com.myretail.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myretail.product.dto.ProductDTO;
import com.myretail.product.service.ProductAPIService;

@RestController
@RequestMapping("myretail")
public class MyretailProductApiController {

	@Autowired
	private ProductAPIService productAPIService;

	@RequestMapping(method = RequestMethod.GET, path = "/product/{id}")
	public Object findProductDetail(@PathVariable("id") String id) {
		
		return productAPIService.findProductDetail(id);

	}

	@RequestMapping(method = { RequestMethod.PUT, RequestMethod.POST }, path = "/product/{id}")
	public Object updateProduct(@RequestBody ProductDTO product) {
		
		return productAPIService.updateProduct(product);

	}

}
