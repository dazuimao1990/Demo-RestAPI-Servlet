package com.topica.demojspservlet.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Todo implements Serializable {

	private Long id;

	private String name;

	private String description;

	public String getDescription() {
		return this.description;

	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName

	() {
		return this.name

		;
	}

	public void setName

	(String name

	) {
		this.name

				= name

		;
	}

	public Long getId

	() {
		return this.id

		;
	}

	public void setId

	(Long id

	) {
		this.id

				= id

		;
	}

}