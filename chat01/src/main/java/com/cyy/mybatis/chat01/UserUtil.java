package com.cyy.mybatis.chat01;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

/**
 * @author cyy<br />
 * Description: <br/>
 * @date: 2020/11/5 9:14<br/>
 */
@Slf4j
public class UserUtil {
    private static SqlSessionFactory sqlSessionFactory = build();

    public static SqlSessionFactory build() {
        try{
            return new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        }catch (IOException e) {
            log.info(e.getMessage(), e);
            throw new RuntimeException();
        }
    }

    @FunctionalInterface
    public interface SessionCall<O> {
        O call(SqlSession session) throws Exception;
    }

    @FunctionalInterface
    public interface MapperCall<T, O> {
        O call(T mapper) throws Exception;
    }

    public static <T, O> O callMapper(Class<T> tClass, MapperCall<T, O> mapper) throws Exception {
        return call(session -> mapper.call(session.getMapper(tClass)));
    }

    public static <O> O call(SessionCall<O> sessionCall) throws Exception {
        try(SqlSession session  = sqlSessionFactory.openSession(true);) {
            return sessionCall.call(session);
        }
    }
}
