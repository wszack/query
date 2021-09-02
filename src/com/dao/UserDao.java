package com.dao;

/**
 * @Auther: zzc
 * @Date: 2021/8/28-08-28-13:29
 * @Description: com.dao
 * @version: 1.0
 */

import com.domain.User;

import java.util.List;
import java.util.Map;

/**
 * 用户操作的dao
 */
public interface UserDao {
    public List<User> findAll();
    public User findUserByUsernameAndPassword(String username,String password);

    void add(User user);

    void delete(int parseInt);

    /**
     *
     * @param parseInt
     * @return
     */
    User findById(int parseInt);

    void update(User user);

    /**
     * 查询总记录数
     * @return
     * @param condition
     */
    int findTotalCount(Map<String, String[]> condition);

    /**
     * 分页查询每页记录
     * @param start
     * @param rows
     * @param condition
     * @return
     */
    List<User> findByPage(int start, int rows, Map<String, String[]> condition);
}
