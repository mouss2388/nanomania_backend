package com.example.nanomania_banckend.models;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @ToString
@Entity
public class Genre {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private  int id;
	private String label;
	
	@ManyToMany (mappedBy = "genres") @JsonIgnore
	private Set<VideoGame> videoGames;

}
