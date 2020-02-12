package com.example.nanomania_banckend.models;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.nanomania_banckend.repositories.VideoGameRepository;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class VideoGameTest {
	
	@Autowired
	private VideoGameRepository videoGameRepository;
	
	private VideoGame game1 ;
	
	
	@BeforeEach
	public void beforeTest() {
	
		LocalDate date = LocalDate.now();
		game1 = new VideoGame(1, "Tomb raider",date, new Editor(1,"Activision", "Activision@gmail.com",null)
				,null, null,null);					
	}
	
	@AfterEach
	public void afterTest() {
		game1=null;
	}
	
	@Test
	@DisplayName("Test basique du contenu d'un jeu vidéo")
	public void testSimpleReading() {
		VideoGame game = this.game1;
		Editor editor = game.getEditor();
		assertAll(
				() -> assertEquals(1, game.getId()),
				() -> assertEquals("Tomb raider", game.getName()),
				() -> assertEquals(editor, game.getEditor())
				);
	}

}
