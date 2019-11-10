package br.com.agibank.fileprocessor.domain;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.annotation.Id;

public class Item implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7696200034533828023L;

	@Id
	private Long itemId;

	private Integer itemQty;

	private Double price;

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Integer getItemQty() {
		return itemQty;
	}

	public void setItemQty(Integer itemQty) {
		this.itemQty = itemQty;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		return Objects.hash(itemId, itemQty, price);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		return Objects.equals(itemId, other.itemId) && Objects.equals(itemQty, other.itemQty)
				&& Objects.equals(price, other.price);
	}

	@Override
	public String toString() {
		return "Item [itemId=" + itemId + ", itemQty=" + itemQty + ", price=" + price + "]";
	}

}
