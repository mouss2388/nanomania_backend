package com.example.nanomania_banckend.models.projections;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "EditorData", types = VideoGameAllData.class)
public interface EditorData {

	int getId();
	String getName();
	String getEmail();

}
