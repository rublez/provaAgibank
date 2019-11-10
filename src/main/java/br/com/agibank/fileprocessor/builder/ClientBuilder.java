package br.com.agibank.fileprocessor.builder;

import br.com.agibank.fileprocessor.domain.Client;

public class ClientBuilder {
	private Client entity;

	public ClientBuilder() {
		entity = new Client();
	}

	public ClientBuilder withName(String value) {
		entity.setName(value);
		return this;
	}

	public ClientBuilder withCnpj(String value) {
		entity.setCnpj(value);
		return this;
	}

	public ClientBuilder withBusinessArea(String value) {
		entity.setBusinessArea(value);
		return this;
	}

	public ClientBuilder withFilename(String value) {
		entity.setFilename(value);
		return this;
	}

	public Client build() {
		return entity;
	}

}
