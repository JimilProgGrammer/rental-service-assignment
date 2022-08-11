package com.rental.setu.repositories;

import com.rental.setu.model.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Extend CrudRepository over Users Entity to
 * perform SQL operations on the users table
 *
 * @author jimil
 */
public interface UsersRepositoryImpl extends CrudRepository<Users, Integer> {

    /**
     * find user matching user_id
     *
     * @param userId identify uniquely the records in user table
     * @return matching record from users table; null otherwise
     */
    @Query("SELECT u FROM Users u WHERE u.user_id = ?1")
    public Users findByUser_id(Integer userId);

}
