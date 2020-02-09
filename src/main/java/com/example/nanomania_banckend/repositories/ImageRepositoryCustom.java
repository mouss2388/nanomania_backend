package com.example.nanomania_banckend.repositories;

import java.io.File;
import java.io.InputStream;
import java.util.Optional;

import com.example.nanomania_banckend.models.Image;

public interface ImageRepositoryCustom {
	boolean saveImageFile(Image image, InputStream file);
	Optional<File> getImageFile(String storageId);
	boolean deleteImageFile(Image image);

}
