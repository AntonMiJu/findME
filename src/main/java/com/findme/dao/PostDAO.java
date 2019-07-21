package com.findme.dao;

import com.findme.models.Post;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class PostDAO extends GeneralDAO<Post> {

}
