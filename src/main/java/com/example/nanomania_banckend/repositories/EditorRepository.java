package com.example.nanomania_banckend.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.nanomania_banckend.models.Editor;

@RepositoryRestResource
public interface EditorRepository  extends PagingAndSortingRepository<Editor, Integer>{

}
