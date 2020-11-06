package com.cyy.mybatis.chat01;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author cyy<br />
 * Description: <br/>
 * @date: 2020/11/5 17:39<br/>
 */
@Slf4j
public class UserMapperTest {

    @Test
    public void insert() throws Exception{//动态插入
        UserModel userModel1 = UserModel.builder().name("陈二").build();
        UserUtil.callMapper(UserMapper.class, mapper -> {
            mapper.insert(userModel1);
            return null;
        });
        log.info("插入结果：{}", this.getModelById(userModel1.getId()));
        log.info("----------------------");

        UserModel userModel2 = UserModel.builder().name("龙四").age(30).salary(54400.00).build();
        UserUtil.callMapper(UserMapper.class, mapper -> {
            mapper.insert(userModel2);
            return null;
        });
        log.info("插入结果：{}", this.getModelById(userModel2.getId()));
    }

    //批量插入
    @Test
    public void insertBatch() throws Exception {
        List<UserModel> userModelList = new ArrayList<>();
        for (int i = 0; i <= 5 ; i++) {
            userModelList.add(UserModel.builder().name("诸葛亮" + i).age(35 + i).salary(22224.00 * i).build());
            userModelList.add(UserModel.builder().name("曹操" + i).age(40+ i).salary(30000.00 * i).build());
        }
        UserUtil.callMapper(UserMapper.class, mapper -> {
            mapper.insertBatch(userModelList);
            return null;
        });
        List<UserModel> userModelList1 = UserUtil.callMapper(UserMapper.class, mapper -> mapper.getModelList(null));
        log.info("插入结果：{}", userModelList1);
    }

    //根据用户id删除数据
    @Test
    public void delete() throws Exception {
        Map<String, Object> map = new HashMap<>();
        //需要删除的用户id
        map.put("id", 24);
        Integer count = UserUtil.callMapper(UserMapper.class, mapper -> mapper.delete(map));
        log.info("删除行数：{}", count);
    }

    //动态更新
    @Test
    public void update() throws Exception{
        // 将userId=25的name改为周瑜
        Long userId = 25L;
        Integer count = UserUtil.callMapper(UserMapper.class, mapper -> mapper.update(UserModel.builder().id(userId).name("ready").build()));
        log.info("更新行数：{}", count);
        log.info("----------------------");
        //将userId=26的name改为：刘备， 薪水为：100000
        Long userId2 = 26L;
        count = UserUtil.callMapper(UserMapper.class, mapper -> mapper.update(UserModel.builder().id(userId2).name("刘备").salary(100000.00).build()));
        log.info("更行行数：{}", count);
    }

    //查询所有数据
    @Test
    public void getModelList1() throws Exception{
        List<UserModel> userModelList = UserUtil.callMapper(UserMapper.class, mapper -> mapper.getModelList(null));
        log.info("结果：{}", userModelList);
    }

    //查询多个用户id对应的数据
    @Test
    public void getModelListByIds() throws Exception{
        List<Integer> idList = Arrays.asList(25, 26, 27).stream().collect(Collectors.toList());
        Map<String, Object> map = new HashMap<>();
        map.put("idList", idList);
        List<UserModel> userModelList = UserUtil.callMapper(UserMapper.class, mapper -> mapper.getModelList(map));
        log.info("结果：{}", userModelList);
    }

    //多条件&指定返回的列
    @Test
    public void getModelList2() throws Exception {
        //查询姓名中包含诸葛亮以及薪资大于40000的用户id、姓名
        Map<String, Object> map = new HashMap<>();
        map.put("nameLike", "诸葛亮");
        map.put("salaryGte", 40000.00);
        //需要返回的列
        List<String> tableColumnList = new ArrayList<>();
        tableColumnList.add("id");
        tableColumnList.add("name");
        map.put("tableColumnList", tableColumnList);
        List<UserModel> userModelList = UserUtil.callMapper(UserMapper.class, mapper -> mapper.getModelList(map));
        log.info("结果：{}", userModelList);
    }

    //条件过滤&排序&分页查询数据&只返回用户id、salary
    @Test
    public void getPage() throws Exception {
        //查询姓名中包含诸葛亮以及薪资大于40000的用户id、姓名, 按照薪资倒序， 每5条取第1页
        Map<String, Object> map = new HashMap<>();
        map.put("nameLike", "诸葛亮");
        map.put("salaryGte", 40000.00);
        //加入排序
        map.put("sort", "salary desc");
        //加入分页参数
        int page = 1;
        int pageSize = 5;
        map.put("skip", (page -1) * pageSize);
        map.put("pageSize", pageSize);
        //需要返回的列
        List<String> tableColumnList = new ArrayList<>();
        tableColumnList.add("id");
        tableColumnList.add("salary");
        map.put("tableColumnList", tableColumnList);
        List<UserModel> userModelList = UserUtil.callMapper(UserMapper.class, mapper -> mapper.getModelList(map));
        log.info("结果：{}", userModelList);
    }








    //按用户id查询
    public UserModel getModelById(Long userId) throws Exception {
        //查询指定id的数据
        Map<String, Object> map = new HashMap<>();
        map.put("id",userId);
        return UserUtil.callMapper(UserMapper.class, mapper -> {
            List<UserModel> userModelList = mapper.getModelList(map);
            if(userModelList.size() == 1) {
                return userModelList.get(0);
            }
            return null;
        });

    }

}
