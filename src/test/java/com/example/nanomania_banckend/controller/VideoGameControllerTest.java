package com.example.nanomania_banckend.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
//les fonction mockito pour faux objet
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.nanomania_banckend.models.Editor;
import com.example.nanomania_banckend.models.VideoGame;
import com.example.nanomania_banckend.repositories.VideoGameRepository;
@SpringBootTest
@AutoConfigureMockMvc
public class VideoGameControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private VideoGameRepository videoGameRepository;

	private Page<VideoGame> getSampleVideoGamePage(){
		return new PageImpl<VideoGame>(new ArrayList<>( Arrays.asList( 
				new VideoGame(1, "Tomb raider",LocalDate.now(),new Editor(1,"Activision", "Activision@gmail.com",null),null, null,null),
				new VideoGame(2, "Batman",LocalDate.now(),new Editor(2,"RockSteady", "steady@gmail.com",null),null, null,null))),
				PageRequest.of(0, 10),2);
	}
	
	private ArrayList<VideoGame> getSampleVideoGameList(){
		return new ArrayList<>( Arrays.asList( 
				new VideoGame(1, "Mario Galaxy 1",LocalDate.now(),new Editor(1,"Nintendo", "nin@gmail.com",null),null, null,null),
				new VideoGame(2, "Batman",LocalDate.now(),new Editor(2,"RockSteady", "steady@gmail.com",null),null, null,null)
				) );
	}
	

	

	@Test
	@DisplayName("test de requette get sur la list")
	public void findAllList() throws Exception {

		when(videoGameRepository.findAll())
		.thenReturn(getSampleVideoGameList());

		mockMvc.perform(get("/video-games/list"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath("$.[0].id",is(1)))
		.andExpect(jsonPath("$.[0].name",is("Mario Galaxy 1")));	
		verify(videoGameRepository, times(1)).findAll();
	}
	
//	@Test
//	@DisplayName("test de requette get sur la list Empty")
//	public void findAllListEmpty() throws Exception {
//
//		when(videoGameRepository.findAll())
//		.thenReturn(new ArrayList<>());
//
//		mockMvc.perform(get("/video-games/list"))
//		.andExpect(status().isOk())
//		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//		.andExpect((ResultMatcher) jsonPath("$", new ArrayList<>().isEmpty()));	
//		verify(videoGameRepository, times(1)).findAll();
//	}
	
	@Test
	@DisplayName("test de requette get sur la page-list")
	public void findAll() throws Exception {

		when(videoGameRepository.findAll(any(Pageable.class)))
		.thenReturn(getSampleVideoGamePage());

		mockMvc.perform(get("/video-games/page-list"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath("$.content",hasSize(2)))
		.andExpect(jsonPath("$.size", is(10)))
		.andExpect(jsonPath("$.number", is(0)));
	}

	
	@Test
	@DisplayName("test de la requette get pour un videoGame d'id 1 par Path Variable")
	public void findByIdPathVar() throws Exception {
		when(videoGameRepository.existsById(1))
		.thenReturn(true);
		
		when(videoGameRepository.findById(1))
		.thenReturn(
				Optional.of(
				new VideoGame(1, "Resident evil 4",LocalDate.now(),
						new Editor(1,"Capcom", "capcom@gmail.com",null),
						null, null,null)));

		mockMvc.perform(get("/video-games/1"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath("$.id",is(1)))
		.andExpect(jsonPath("$.name",is("Resident evil 4")));	
		verify(videoGameRepository, times(1)).findById(1);
	}
	
	@Test
	@DisplayName("test de la requette get pour un videoGame d'id 1 par request Param")
	public void findById() throws Exception {
		when(videoGameRepository.findById(1))
		.thenReturn(
				Optional.of(
				new VideoGame(1, "Resident evil 4",LocalDate.now(),
						new Editor(1,"Capcom", "capcom@gmail.com",null),
						null, null,null)));
		
		mockMvc.perform(get("/video-games?id=1"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath("$.id",is(1)))
		.andExpect(jsonPath("$.name",is("Resident evil 4")));	
		verify(videoGameRepository, times(1)).findById(1);
	}
	
	
}
