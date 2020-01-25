package com.example.nanomania_banckend.web;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.nanomania_banckend.models.VideoGame;
import com.example.nanomania_banckend.models.projections.VideoGameAllData;
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


	@GetMapping
	public Iterable<VideoGame>findAll(){
		return this.videoGameRepository.findAll();
	}
	
	
	@GetMapping("/list-game")
	public Page<VideoGameAllData>findAll(@PageableDefault Pageable page){
		return this.videoGameRepository
				   .findAll(page)
				   .map(pageGame -> projectionFactory.createProjection(VideoGameAllData.class, pageGame));
	}

	@GetMapping(value = "/{id:[0-9]+}")
	public ResponseEntity<VideoGame> findById(@PathVariable("id") int id){
		return this.videoGameRepository.findById(id)
				.map(game -> new ResponseEntity<>(game ,HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	//	@GetMapping
	//	public ResponseEntity<VideoGame> findById(@RequestParam("id") int id){
	//		return this.videoGameRepository.findById(id)
	//					.map(game -> new ResponseEntity<>(game ,HttpStatus.OK))
	//					.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	//	}
	@PostMapping
	public ResponseEntity<VideoGame> createVideoGame(
			@RequestBody VideoGame videoGame
			){
		videoGame = videoGameRepository.save(videoGame);
		return new ResponseEntity<VideoGame>(videoGame, HttpStatus.CREATED);
	}
}
