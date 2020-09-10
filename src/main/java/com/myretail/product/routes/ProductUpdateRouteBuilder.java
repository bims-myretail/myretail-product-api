package com.myretail.product.routes;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.myretail.product.dto.PriceDTO;
import com.myretail.product.dto.ProductDTO;

@Component
public class ProductUpdateRouteBuilder extends RouteBuilder {

	@Value("${product.price.endpoint}")
	private String productPriceEndpoint;

	@Value("${product.attribute.endpoint}")
	private String productAttributeEndpoint;

	JacksonDataFormat productDataFormat = new JacksonDataFormat(ProductDTO.class);
	JacksonDataFormat priceDataFormat = new JacksonDataFormat(PriceDTO.class);
	JacksonDataFormat attributeDataFormat = new JacksonDataFormat(Map.class);

	@Override
	public void configure() throws Exception {

		from("direct:productUpdate").setHeader(Exchange.HTTP_METHOD, simple("POST"))
				.setHeader(Exchange.CONTENT_TYPE, simple("application/json")).multicast().parallelProcessing()
				.to("direct:priceupdate","direct:attributeupdate").aggregationStrategy(getAgreegationStrategy());

		String priceURI = productPriceEndpoint.concat("?bridgeEndpoint=true&throwExceptionOnFailure=true");
		String attributeURI = productAttributeEndpoint.concat("?bridgeEndpoint=true&throwExceptionOnFailure=true");

		from("direct:priceupdate").unmarshal(productDataFormat).process(new PriceRequestProcesser())
				.marshal(priceDataFormat).to(priceURI).unmarshal(priceDataFormat).process(new PriceResponseProcessor())
				.marshal(priceDataFormat);

		from("direct:attributeupdate").to(attributeURI).unmarshal(attributeDataFormat)
				.process(new AttributeResponseProcessor()).marshal(attributeDataFormat);

	}

	public AggregationStrategy getAgreegationStrategy() {
		return new ProductAgreegationStrategyBuilder();
	}

}
