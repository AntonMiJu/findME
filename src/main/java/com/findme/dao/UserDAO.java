package com.findme.dao;

import com.findme.models.User;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class UserDAO extends GeneralDAO<User> {

}
