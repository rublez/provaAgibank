package br.com.agibank.fileprocessor.builder;
import br.com.agibank.fileprocessor.domain.Salesman;

public class SalesmanBuilder {
	private Salesman entity;

	public SalesmanBuilder() {
		entity = new Salesman();
	}

	public SalesmanBuilder withName(String value) {
		entity.setName(value);
		return this;
	}

	public SalesmanBuilder withCpf(String value) {
		entity.setCpf(value);
		return this;
	}

	public SalesmanBuilder withSalary(Double value) {
		entity.setSalary(value);
		return this;
	}

	public SalesmanBuilder withFilename(String value) {
		entity.setFilename(value);
		return this;
	}

	public Salesman build() {
		return entity;
	}

}
