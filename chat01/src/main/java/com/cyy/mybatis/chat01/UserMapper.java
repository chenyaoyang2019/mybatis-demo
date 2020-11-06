package com.cyy.mybatis.chat01;

import java.util.List;
import java.util.Map;

/**
 * @author cyy<br />
 * Description: <br/>
 * @date: 2020/11/3 13:57<br/>
 */
public interface UserMapper {
    /**
     * 插入用户信息
     * @param userModel
     */
    void insert(UserModel userModel);

    /**
     * 批量插入用户信息
     * @param userModelList
     */
    void insertBatch(List<UserModel> userModelList);

    /**
     * 更新用户信息
     * @param userModel
     * @return
     */
    int update(UserModel userModel);

    /**
     * 通过map来更新用户信息
     * @param map
     * @return
     */
    int updateByMap(Map<String, Object> map);

    /**
     * 通过map来删除用户信息
     * @param map
     * @return
     */
    int delete(Map<String, Object> map);

    /**
     * 查询用户列表
     * @param map
     * @return
     */
    List<UserModel> getModelList(Map<String, Object> map);
}
