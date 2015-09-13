package com.lftechnology.java.training.alina.jdbc.dao.user;

import java.util.List;
import com.lftechnology.java.training.alina.jdbc.api.CrudService;
import com.lftechnology.java.training.alina.jdbc.domain.User;

public interface UserDao extends CrudService<User> {
    List<User> searchUser(String sql, String... searchContent);
}
