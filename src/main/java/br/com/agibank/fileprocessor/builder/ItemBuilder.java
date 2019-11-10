package br.com.agibank.fileprocessor.builder;

import br.com.agibank.fileprocessor.domain.Item;

public class ItemBuilder {
	private Item entity;

	public ItemBuilder() {
		this.entity = new Item();
	}

	public ItemBuilder withItemId(Long value) {
		this.entity.setItemId(value);
		return this;
	}

	public ItemBuilder withItemQty(Integer value) {
		this.entity.setItemQty(value);
		return this;
	}

	public ItemBuilder withPrice(Double value) {
		this.entity.setPrice(value);
		return this;
	}

	public Item build() {
		return this.entity;
	}
	
}
