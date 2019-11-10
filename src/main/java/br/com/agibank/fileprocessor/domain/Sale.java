package br.com.agibank.fileprocessor.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sale")
public class Sale implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 4808987147860046655L;

	@Id
	private Long saleId;

	private List<Item> items;

	private String salesmanName;

	private String filename;

	public Long getSaleId() {
		return saleId;
	}

	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public String getSalesmanName() {
		return salesmanName;
	}

	public void setSalesmanName(String salesmanName) {
		this.salesmanName = salesmanName;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Override
	public int hashCode() {
		return Objects.hash(filename, items, saleId, salesmanName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sale other = (Sale) obj;
		return Objects.equals(filename, other.filename) && Objects.equals(items, other.items)
				&& Objects.equals(saleId, other.saleId) && Objects.equals(salesmanName, other.salesmanName);
	}

	@Override
	public String toString() {
		return "Sale [saleId=" + saleId + ", items=" + items + ", salesmanName=" + salesmanName + ", filename="
				+ filename + "]";
	}


}
