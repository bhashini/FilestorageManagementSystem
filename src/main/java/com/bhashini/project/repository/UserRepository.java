package com.bhashini.project.repository;

import org.springframework.stereotype.Repository;
import com.bhashini.project.jpa.User;

@Repository
public interface UserRepository extends BaseRepository<User, String> {

}
