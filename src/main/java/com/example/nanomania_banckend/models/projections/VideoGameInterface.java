package com.example.nanomania_banckend.models.projections;

import java.time.LocalDate;

import org.springframework.data.rest.core.config.Projection;

import com.example.nanomania_banckend.models.VideoGame;

@Projection(name = "VideoGameInterface", types = VideoGame.class)
public interface VideoGameInterface {
	int getId();
	String getName();
	LocalDate getDateRelease();
}
