package com.example.nanomania_banckend.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.nanomania_banckend.models.Image;

public interface   ImageRepository extends PagingAndSortingRepository<Image, Integer>,ImageRepositoryCustom {

}
