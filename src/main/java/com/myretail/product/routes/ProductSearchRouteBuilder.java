package com.myretail.product.routes;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.myretail.product.dto.PriceDTO;

@Component
public class ProductSearchRouteBuilder extends RouteBuilder {

	@Value("${product.price.endpoint}")
	private String productPriceEndpoint;

	@Value("${product.attribute.endpoint}")
	private String productAttributeEndpoint;

	JacksonDataFormat priceDataFormat = new JacksonDataFormat(PriceDTO.class);
	JacksonDataFormat attributeDataFormat = new JacksonDataFormat(Map.class);

	@Override
	public void configure() throws Exception {

		from("direct:productsearch").process(new Processor() {
			@Override
			public void process(Exchange exchange) throws Exception {
				String productID = (String) exchange.getIn().getBody(String.class);
				exchange.getIn().setHeader(Exchange.HTTP_QUERY, "productID=" + productID);
			}
		}).setHeader(Exchange.HTTP_METHOD, simple("GET")).setHeader(Exchange.CONTENT_TYPE, simple("application/json"))
				.multicast().parallelProcessing().to("direct:attribute", "direct:price")
				.aggregationStrategy(getAgreegationStrategy());

		String attributeURI = productAttributeEndpoint.concat("?bridgeEndpoint=true&throwExceptionOnFailure=true");
		String priceURI = productPriceEndpoint.concat("?bridgeEndpoint=true&throwExceptionOnFailure=true");

		from("direct:attribute").to(attributeURI).unmarshal(attributeDataFormat)
				.process(new AttributeResponseProcessor()).marshal(attributeDataFormat);

		from("direct:price").to(priceURI).unmarshal(priceDataFormat).process(new PriceResponseProcessor())
				.marshal(priceDataFormat);		
		

	}

	public AggregationStrategy getAgreegationStrategy() {
		return new ProductAgreegationStrategyBuilder();
	}

}
