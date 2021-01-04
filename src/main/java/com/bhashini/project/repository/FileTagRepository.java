package com.bhashini.project.repository;

import org.springframework.stereotype.Repository;

import com.bhashini.project.jpa.FileTag;
import com.bhashini.project.jpa.FileTagPK;



@Repository
public interface FileTagRepository extends BaseRepository<FileTag, FileTagPK> {

		
}
