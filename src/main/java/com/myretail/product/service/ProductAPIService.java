package com.myretail.product.service;

import com.myretail.product.dto.ProductDTO;

public interface ProductAPIService {

	public Object findProductDetail(String id);

	public Object updateProduct(ProductDTO product);
}
