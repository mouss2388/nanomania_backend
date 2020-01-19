package com.example.nanomania_banckend.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
}
