package com.example.nanomania_banckend.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.nanomania_banckend.models.Editor;
import com.example.nanomania_banckend.models.Platform;
import com.example.nanomania_banckend.repositories.EditorRepository;

@RestController
@RequestMapping("/editors")
@CrossOrigin
public class EditorController {
	@Autowired
	private EditorRepository editorRepository;
	
	@GetMapping()
	public Iterable<Editor>findAll(){
		return this.editorRepository.findAll();
	}
	
	@GetMapping(value = "/{id:[0-9]+}")
	public ResponseEntity<Editor> findById(@PathVariable("id") int id){
		return this.editorRepository.findById(id)
					.map(editor -> new ResponseEntity<>(editor ,HttpStatus.OK))
					.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	

}
