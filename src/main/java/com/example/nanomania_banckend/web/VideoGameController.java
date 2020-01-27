package com.example.nanomania_banckend.web;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.example.nanomania_banckend.models.Platform;
import com.example.nanomania_banckend.models.VideoGame;
import com.example.nanomania_banckend.models.projections.VideoGameInfoComplet;
import com.example.nanomania_banckend.repositories.EditorRepository;
import com.example.nanomania_banckend.repositories.GenreRepository;
import com.example.nanomania_banckend.repositories.PlatformRepository;
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
	@Autowired
	private GenreRepository genreRepository;
	@Autowired
	private PlatformRepository platformRepository;


	@GetMapping("/list")
	public Iterable<VideoGame>findAll(){
		return this.videoGameRepository.findAll();
	}


	@GetMapping("/list-game")
	public Page<VideoGame>findAll(@PageableDefault(size = 10, page = 0) Pageable page){
		return this.videoGameRepository
				.findAll(page);
	}


	@GetMapping(value = "/{id:[0-9]+}")
	public ResponseEntity<VideoGameInfoComplet> findByIdPathVar(@PathVariable("id") int id){
		Optional<VideoGame> gameOriginal = this.videoGameRepository.findById(id);
		if(! gameOriginal.isPresent())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<VideoGameInfoComplet>(projectionFactory.createProjection(VideoGameInfoComplet.class,
				gameOriginal), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<VideoGameInfoComplet> findById(@RequestParam("id") int id){
		Optional<VideoGame> gameOriginal = this.videoGameRepository.findById(id);
		if(! gameOriginal.isPresent())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<VideoGameInfoComplet>(projectionFactory.createProjection(VideoGameInfoComplet.class,
				gameOriginal), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<VideoGame> createVideoGame(
			@RequestBody VideoGame game,
			@RequestParam("genresId") List<Integer> genresId,
			@RequestParam("platformsId") List<Integer> platformsId
			){
		
		Iterable<Genre>  newGenres = this.genreRepository.findAllById(genresId);
		Iterable<Platform>  newPlatforms = this.platformRepository.findAllById(platformsId);
		
		for (Platform platform : newPlatforms) 
			game.getPlatforms().add(platform);
		for (Genre genre : newGenres) 
			game.getGenres().add(genre);

		//game = videoGameRepository.save(game);
		return new ResponseEntity<VideoGame>(game, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<VideoGameInfoComplet> updateGame(
			@RequestBody VideoGame game,
			@RequestParam("editorId") int editorId,
			@RequestParam("genresId") List<Integer> genresId,
			@RequestParam("platformsId") List<Integer> platformsId
			){

		Optional<VideoGame> originalGame =  this.videoGameRepository.findById(game.getId());

		if (!originalGame.isPresent()) 
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		if(editorId == 0) 
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);



		Optional<Editor> newEditor =  this.editorRepository.findById(editorId);
		Iterable<Genre>  newGenres = this.genreRepository.findAllById(genresId);
		Iterable<Platform>  newPlatforms = this.platformRepository.findAllById(platformsId);



		/**
		 * Check if newGenres et new Platforms are empty then BAD_REQUEST
		 */
		if(!newEditor.isPresent())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


		VideoGame editedGame = originalGame.get();

		editedGame.setEditor(newEditor.get());
		//On vide les genres et les plateformes
		editedGame.getGenres().clear();
		editedGame.getPlatforms().clear();
		//Ajoute les nouveaux genres et plateformes
		for (Genre genre : newGenres) 
			editedGame.getGenres().add(genre);

		for (Platform platform : newPlatforms) 
			editedGame.getPlatforms().add(platform);

		editedGame = videoGameRepository.save(editedGame);

		return new ResponseEntity<VideoGameInfoComplet>(
				projectionFactory.createProjection(VideoGameInfoComplet.class, editedGame)
				,HttpStatus.ACCEPTED);
	}


	@DeleteMapping
	public ResponseEntity<Map<String, Object>> deleteVideoGame(@RequestParam("gameId")  int gameId){
		Optional<VideoGame> game = this.videoGameRepository.findById(gameId);
		VideoGame copyGame = game.get();
		if (!game.isPresent())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		this.videoGameRepository.delete(game.get());
		return new ResponseEntity<Map<String,Object>>(
				Collections.singletonMap("id_deleted", copyGame.getId()),
				HttpStatus.OK);
	}
}
