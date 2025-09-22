package com.priyank.JournalApplication.Respository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.priyank.JournalApplication.Entity.User;

public interface UserRepository extends MongoRepository<User,ObjectId> {
    User findByUserName(String userName);
    void deleteByUserName(String userName);
}
