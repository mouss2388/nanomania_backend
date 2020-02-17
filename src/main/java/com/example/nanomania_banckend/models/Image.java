package com.example.nanomania_banckend.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity
public class Image {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String description;
	@JsonIgnore													
	private String storageId;
	@JsonIgnore													
	private String thumbStorageId;
	
	@ManyToOne @JsonIgnore										
	private VideoGame videoGame;

	public Image(int id, String description, String storageId, String thumbStorageId) {
		super();
		this.id = id;
		this.description = description;
		this.storageId = storageId;
		this.thumbStorageId = thumbStorageId;
	}
}
