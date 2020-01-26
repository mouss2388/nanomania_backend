package com.example.nanomania_banckend.web;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.nanomania_banckend.models.Editor;
import com.example.nanomania_banckend.models.Genre;
import com.example.nanomania_banckend.models.VideoGame;
import com.example.nanomania_banckend.models.projections.VideoGameInfoComplet;
import com.example.nanomania_banckend.repositories.EditorRepository;
import com.example.nanomania_banckend.repositories.VideoGameRepository;

@RestController
@RequestMapping("/video-games")
@CrossOrigin
public class VideoGameController {

	private final ProjectionFactory projectionFactory;

	@Autowired
	public VideoGameController(ProjectionFactory projectionFactory) {
		this.projectionFactory = projectionFactory;
	}

	@Autowired
	private VideoGameRepository videoGameRepository;
	@Autowired
	private EditorRepository editorRepository;


	@GetMapping("/list")
	public Iterable<VideoGame>findAll(){
		return this.videoGameRepository.findAll();
	}


	@GetMapping("/list-game")
	public Page<VideoGame>findAll(@PageableDefault Pageable page){
		return this.videoGameRepository
				.findAll(page);
	}

	/**
	 * ASK HOW GET USE PROJECTION && RESPONSE ENTITY
	 * @param id
	 * @return
	 */

	@GetMapping(value = "/{id:[0-9]+}")
	public Optional<VideoGameInfoComplet> findByIdPathVar(@PathVariable("id") int id){
		return this.videoGameRepository.findById(id)
				.map(game -> projectionFactory.createProjection(VideoGameInfoComplet.class,game));
	}

	@GetMapping
	public ResponseEntity<VideoGame> findById(@RequestParam("id") int id){
		return this.videoGameRepository.findById(id)
				.map(game -> new ResponseEntity<>(game ,HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping
	public ResponseEntity<VideoGame> createVideoGame(
			@RequestBody VideoGame videoGame
			){
		videoGame = videoGameRepository.save(videoGame);
		return new ResponseEntity<VideoGame>(videoGame, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<VideoGame> updateGame(
			@RequestBody VideoGame game,
			@RequestParam("editorId") int editorId,
			@RequestParam("genreId") Set<Genre> genres,
			@RequestParam("platformId") int platformId){

		if(game.getId() == 0 || editorId == 0) 
			return new ResponseEntity<VideoGame>(HttpStatus.BAD_REQUEST);

		Optional<Editor> hisEditor =  this.editorRepository.findById(editorId);


		if(!hisEditor.isPresent() )
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		
		game.setEditor(hisEditor.get());
		game = videoGameRepository.save(game);
		
		return new ResponseEntity<VideoGame>(HttpStatus.ACCEPTED);
	}
}
