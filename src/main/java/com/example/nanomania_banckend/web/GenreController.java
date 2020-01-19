package com.example.nanomania_banckend.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.nanomania_banckend.models.Genre;
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

}
