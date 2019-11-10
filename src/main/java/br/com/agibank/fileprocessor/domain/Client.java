package br.com.agibank.fileprocessor.domain;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "client")
public class Client implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -6145946609037940538L;

	@Id
	private String name;

	private String cnpj;

	private String businessArea;

	private String filename;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getBusinessArea() {
		return businessArea;
	}

	public void setBusinessArea(String businessArea) {
		this.businessArea = businessArea;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Override
	public int hashCode() {
		return Objects.hash(businessArea, cnpj, filename, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		return Objects.equals(businessArea, other.businessArea) && Objects.equals(cnpj, other.cnpj)
				&& Objects.equals(filename, other.filename) && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Client [name=" + name + ", cnpj=" + cnpj + ", businessArea=" + businessArea + ", filename=" + filename
				+ "]";
	}

}
