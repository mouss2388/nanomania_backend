package com.example.nanomania_banckend.web;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.nanomania_banckend.models.Image;
import com.example.nanomania_banckend.models.VideoGame;
import com.example.nanomania_banckend.repositories.ImageRepository;
import com.example.nanomania_banckend.repositories.VideoGameRepository;
import com.mongodb.BasicDBObject;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.resizers.configurations.ScalingMode;

@Controller
@RequestMapping("/images")
public class ImageController {
	
	private static final int THUMB_WIDTH = 256;
	private static final int THUMB_HEIGHT = 256;
	@Autowired
	private GridFsTemplate gridFsTemplate;
	@Autowired
	private ImageRepository imageRepository;

	@ResponseBody
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Iterable<Image> listeImages(){
		return this.imageRepository.findAll();
		
	}
	
	@Autowired
	private VideoGameRepository videoGameRepository;
	
//	@PostMapping("/upload")
//	public ResponseEntity<Image> upload(@RequestParam("file") MultipartFile file,
//										@RequestParam("gameId") int gameId){
//		Optional<VideoGame> g = videoGameRepository.findById(gameId);
//		if (!g.isPresent())
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		try {
//			Image img = new Image(0,
//					file.getOriginalFilename(),
//					file.getOriginalFilename(),
//					file.getContentType());
//			// sauvegarde fichier et generation thumbnail
//			imageRepository.saveImageFile(img, file.getInputStream());
//			img.setVideoGame(g.get());
//			img = imageRepository.save(img);
//			return new ResponseEntity<Image>(img, HttpStatus.CREATED);
//		} catch (IOException e) {e.printStackTrace();}
//		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//	}
	
	
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Image> uploadFile(@RequestParam("file") MultipartFile file,
			@RequestParam("description") String description) throws IOException {
	
		ObjectId thumbId = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			Thumbnails.of(file.getInputStream())
					  .size(THUMB_WIDTH, THUMB_HEIGHT)
					  .scalingMode(ScalingMode.BICUBIC)
					  .outputFormat("jpg")
					  .toOutputStream(bos);
			thumbId = this.gridFsTemplate.store(new ByteArrayInputStream(bos.toByteArray()),
					"thumb_" + file.getOriginalFilename(),
					file.getContentType(),
					new BasicDBObject("description", description));
			
		} catch (IOException | ArrayIndexOutOfBoundsException e) {
			return new ResponseEntity<>( HttpStatus.NOT_IMPLEMENTED);
		}
		ObjectId imageId = this.gridFsTemplate.store(file.getInputStream(),
				file.getOriginalFilename(),
				file.getContentType(),
				new BasicDBObject("description", description));
		Image img = new Image(0, imageId.toString(),  thumbId.toString(), description);
		return new ResponseEntity<>(imageRepository.save(img), HttpStatus.CREATED);
	}
	
	
	
//	
//	@GetMapping("/{id:[0-9]+}/data")
//	public ResponseEntity<FileSystemResource> imageData(@PathVariable("id")int id) {
//		Optional<Image> op = imageRepository.findById(id);
//		if (!op.isPresent())
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		Optional<File> ofile = imageRepository.getImageFile(
//										op.get().getStorageId());
//		if (!ofile.isPresent())
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.parseMediaType(op.get().getContentType()));
//		headers.setContentLength(ofile.get().length());
//		headers.setContentDispositionFormData("attachment", op.get().getFileName());
//		ResponseEntity<FileSystemResource> re = 
//			new ResponseEntity<FileSystemResource>(
//				new FileSystemResource(ofile.get()),
//				headers,
//				HttpStatus.OK);
//		return re;
//	}
//	
//	@GetMapping("/{id:[0-9]+}/thumbdata")
//	public ResponseEntity<FileSystemResource> imageThumbData(@PathVariable("id")int id) {
//		Optional<Image> op = imageRepository.findById(id);
//		if (!op.isPresent())
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		Optional<File> ofile = imageRepository.getImageFile(
//										op.get().getThumbStorageId());
//		if (!ofile.isPresent())
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.IMAGE_JPEG);
//		headers.setContentLength(ofile.get().length());
//		headers.setContentDispositionFormData("attachment", op.get().getFileName());
//		ResponseEntity<FileSystemResource> re = 
//			new ResponseEntity<FileSystemResource>(
//				new FileSystemResource(ofile.get()),
//				headers,
//				HttpStatus.OK);
//		return re;
//	}	

}
