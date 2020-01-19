package com.example.nanomania_banckend.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.nanomania_banckend.models.Editor;
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

}
