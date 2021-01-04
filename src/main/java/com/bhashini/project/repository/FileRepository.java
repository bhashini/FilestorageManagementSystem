package com.bhashini.project.repository;

import org.springframework.stereotype.Repository;

import com.bhashini.project.jpa.File;


@Repository
public interface FileRepository extends BaseRepository<File, Integer> {
	
}
