package com.example.nanomania_banckend.models;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @ToString
@Entity
public class VideoGame {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private LocalDate dateRelease;
	
	@ManyToOne @JsonIgnore
	private Editor editor;

	@ManyToMany @JsonIgnore
	private Set<Genre> genres;
	@ManyToMany @JsonIgnore
	private Set<Platform> platforms;
	@OneToMany(mappedBy = "videoGame")
	private Set<Image> images;
	
	
	@PreRemove
	public void beforeDelete() {
		if (genres != null) {
			for (Genre genre : genres) 
				genre.setVideoGames(null);
			genres.clear();
		}
		if(platforms != null) {
			for (Platform platform : platforms) 
				platform.setVideoGames(null);
			platforms.clear();
		}	
	}
	
	

}
