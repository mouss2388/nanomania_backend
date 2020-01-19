package com.example.nanomania_banckend.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.nanomania_banckend.models.Genre;
import com.example.nanomania_banckend.models.VideoGame;
import com.example.nanomania_banckend.repositories.GenreRepository;

@RestController
@RequestMapping("/genres")
@CrossOrigin
public class GenreController {
	
	@Autowired
	private GenreRepository genreRepository;
	
	@GetMapping()
	public Iterable<Genre> findAll(){
		return this.genreRepository.findAll();
	}
	
	@GetMapping(value = "/{id:[0-9]+}")
	public ResponseEntity<Genre> findById(@PathVariable("id") int id){
		return this.genreRepository.findById(id)
					.map(genre -> new ResponseEntity<>(genre ,HttpStatus.OK))
					.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

}
