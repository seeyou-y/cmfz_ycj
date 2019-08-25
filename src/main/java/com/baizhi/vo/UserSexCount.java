package com.baizhi.vo;


import lombok.Data;

import java.io.Serializable;

@Data

public class UserSexCount implements Serializable {
    private String name;
    private Integer value;

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
