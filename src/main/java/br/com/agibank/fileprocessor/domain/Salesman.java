package br.com.agibank.fileprocessor.domain;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "salesman")
public class Salesman implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -5194381441782922446L;

	@Id
	private String name;

	private String cpf;

	private Double salary;

	private String filename;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf, filename, name, salary);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Salesman other = (Salesman) obj;
		return Objects.equals(cpf, other.cpf) && Objects.equals(filename, other.filename)
				&& Objects.equals(name, other.name) && Objects.equals(salary, other.salary);
	}

	@Override
	public String toString() {
		return "Salesman [name=" + name + ", cpf=" + cpf + ", salary=" + salary + ", filename=" + filename + "]";
	}

}
