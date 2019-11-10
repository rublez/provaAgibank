package br.com.agibank.fileprocessor.builder;

import java.util.List;

import br.com.agibank.fileprocessor.domain.Item;
import br.com.agibank.fileprocessor.domain.Sale;

public class SaleBuilder {
	private Sale entity;

	public SaleBuilder() {
		entity = new Sale();
	}

	public SaleBuilder withSaleId(Long value) {
		entity.setSaleId(value);
		return this;
	}

	public SaleBuilder withItems(List<Item> values) {
		entity.setItems(values);
		return this;
	}

	public SaleBuilder withSalesmanName(String value) {
		entity.setSalesmanName(value);
		return this;
	}

	public SaleBuilder withFilename(String value) {
		entity.setFilename(value);
		return this;
	}

	public Sale build() {
		return entity;
	}

}
