package com.example.nanomania_banckend.models.projections;

import java.util.Set;

import org.springframework.data.rest.core.config.Projection;

import com.example.nanomania_banckend.models.Editor;
import com.example.nanomania_banckend.models.Genre;
import com.example.nanomania_banckend.models.Platform;
import com.example.nanomania_banckend.models.VideoGame;

@Projection(name = "VideoGameInfoComplet", types = VideoGame.class)
public interface VideoGameInfoComplet extends VideoGameInterface {
	Editor getEditor();
	Set<Platform> getPlatforms();
	Set<Genre> getGenres();
}
