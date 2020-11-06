package com.cyy.mybatis.chat01;

import lombok.*;

/**
 * @author cyy<br />
 * Description: <br/>
 * @date: 2020/11/3 13:54<br/>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserModel {
    private long id;
    private String name;
    private Integer age;
    private Double salary;
}
