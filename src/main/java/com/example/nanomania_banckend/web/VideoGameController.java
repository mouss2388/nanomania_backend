package com.example.nanomania_banckend.web;

import java.util.HashSet;
import java.util.List;
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
import com.example.nanomania_banckend.models.Image;
import com.example.nanomania_banckend.models.Platform;
import com.example.nanomania_banckend.models.VideoGame;
import com.example.nanomania_banckend.models.projections.VideoGameInfoComplet;
import com.example.nanomania_banckend.repositories.EditorRepository;
import com.example.nanomania_banckend.repositories.GenreRepository;
import com.example.nanomania_banckend.repositories.ImageRepository;
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
	@Autowired
	private ImageRepository imageRepository;


	@GetMapping("/list")
	public Iterable<VideoGame>findAll(){
		return this.videoGameRepository.findAll();
	}


	@GetMapping("/page-list")
	public Page<VideoGame>findAll(@PageableDefault(size = 10, page = 0) Pageable page){
		return this.videoGameRepository
				.findAll(page);
	}


	@GetMapping(value = "/{id:[0-9]+}")
	public ResponseEntity<VideoGameInfoComplet> findByIdPathVar(@PathVariable("id") int id){

		if(!this.videoGameRepository.existsById(id))
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		VideoGame game = this.videoGameRepository.findById(id).get();

		return new ResponseEntity<VideoGameInfoComplet>(projectionFactory.createProjection(VideoGameInfoComplet.class,
				game), HttpStatus.OK);
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
			@RequestParam("editorId") int editorId,
			@RequestParam("genresId") Optional<List<Integer>> genresId,
			@RequestParam("platformsId") List<Integer> platformsId
			){

		Optional<Editor> editorOpt = this.editorRepository.findById(editorId);

		if(!editorOpt.isPresent())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);


		if(game.getId()!= 0 || game.getName().trim().isEmpty())
			return new ResponseEntity<VideoGame>(HttpStatus.BAD_REQUEST);


		game.setEditor(editorOpt.get());
		Iterable<Genre>  newGenres = null;
		game.setGenres(new HashSet());
		game.setPlatforms(new HashSet());

		if(genresId.isPresent())
			newGenres = this.genreRepository.findAllById(genresId.get());

		Iterable<Platform>  newPlatforms = this.platformRepository.findAllById(platformsId);

		for (Platform platform : newPlatforms) 
			game.getPlatforms().add(platform);
		for (Genre genre : newGenres) 
			game.getGenres().add(genre);

		return new ResponseEntity<VideoGame>(this.videoGameRepository.save(game), HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<VideoGameInfoComplet> updateGame(
			@RequestBody VideoGame game,
			@RequestParam("editorId") Optional<Integer> editorId,
			@RequestParam("genresId") Optional<List<Integer>> genresId,
			@RequestParam("platformsId") Optional<List<Integer>> platformsId
			){
		if(!this.videoGameRepository.existsById(game.getId()))
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		if(editorId.isPresent())
			if(!this.editorRepository.existsById(editorId.get()))
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		if( game.getName().trim().isEmpty())
			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);

		Editor newEditor =  this.editorRepository.findById(editorId.get()).get();
		Iterable<Genre>  newGenres = new HashSet<>();
		Iterable<Platform> newPlatforms = new HashSet<>() ;
		if(genresId.isPresent()) 
			newGenres = this.genreRepository.findAllById(genresId.get());
		if(platformsId.isPresent())	 
			newPlatforms = this.platformRepository.findAllById(platformsId.get());


		VideoGame editedGame =game;

		editedGame.setImages(game.getImages());


		editedGame.setEditor(newEditor);

		editedGame.setGenres(new HashSet<>());
		editedGame.setPlatforms(new HashSet<>());


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
	public ResponseEntity<String> deleteVideoGame(@RequestParam("gameId")  int gameId){
		System.out.println("in delete");

		if (!this.videoGameRepository.existsById(gameId))
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		VideoGame game = this.videoGameRepository.findById(gameId).get();
		VideoGame copyGame = game;

		for (Image img : game.getImages()) {
			imageRepository.delete(img);
			imageRepository.deleteImageFile(img);
		}
		game.getImages().clear();

		this.videoGameRepository.delete(game);

		return new ResponseEntity<String>(copyGame.getName() + " supprimé",
				HttpStatus.OK);
	}
}
