package com.example.nanomania_banckend.models.projections;

import java.time.LocalDate;

import org.springframework.data.rest.core.config.Projection;

import com.example.nanomania_banckend.models.Editor;

@Projection(name = "VideoGame", types = VideoGameAllData.class)
public interface VideoGameAllData {
	int getId();
	String getName();
	LocalDate getDateRelease();
	Editor getEditorData();
}
