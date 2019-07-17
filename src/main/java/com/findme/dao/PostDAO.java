package com.findme.dao;

import com.findme.models.Post;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class PostDAO extends GeneralDAO<Post> {

}
