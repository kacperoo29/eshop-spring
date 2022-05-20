package com.kbaje.eshop.services.repositories;

import com.kbaje.eshop.models.AppUser;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<AppUser> {

    @Query("SELECT u FROM AppUser u WHERE u.email = ?1")
    AppUser findByEmail(String email);

    @Query("SELECT u FROM AppUser u WHERE u.username = ?1")
    AppUser findByUsername(String username);
    
}
