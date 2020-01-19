package com.example.nanomania_banckend.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.nanomania_banckend.models.Platform;
import com.example.nanomania_banckend.repositories.PlatformRepository;

@RestController
@RequestMapping("/platforms")
@CrossOrigin
public class PlatformController {
	@Autowired
	private PlatformRepository platformRepository;

	@GetMapping()
	public Iterable<Platform> findAll(){
		return this .platformRepository.findAll();
	}
	
	@GetMapping(value = "/{id:[0-9]+}")
	public ResponseEntity<Platform> findById(@PathVariable("id") int id){
		return this.platformRepository.findById(id)
					.map(platform -> new ResponseEntity<>(platform ,HttpStatus.OK))
					.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
}
