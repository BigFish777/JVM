package com.xphu.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

/**
 * @Author @我是一颗小土豆
 * @Date 2020/12/26 23:02
 */
public class StackOverflowErrorDemo1 {
    public static void main(String[] args) throws JsonProcessingException {
//        Dept dept = new Dept();
//        Emp emp1 = new Emp("张三");
//        emp1.setDept(dept);
//        Emp emp2 = new Emp("李四");
//        emp2.setDept(dept);
//
//        dept.setName("开发部");
//        dept.setEmps(Arrays.asList(emp1,emp2));
//
        ObjectMapper objectMapper = new ObjectMapper();
        // 转换为格式化的json
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        // 如果json中有新增的字段并且是实体类类中不存在的，不报错
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
//        System.out.println(objectMapper.writeValueAsString(dept));
//        {"name":"开发部","emps":[{"name":"张三"},{"name":"李四"}]}
        String str = "{\"name\":\"开发部\",\"emps\":[{\"name\":\"张三\"},{\"name\":\"李四\"}]}";
        Dept emp = objectMapper.readValue(str, Dept.class);
        System.out.println(emp);
    }
}

//员工表
@NoArgsConstructor
@AllArgsConstructor
@Data
class Emp {
    private String name;
    @JsonIgnore // 忽略转换 如果不加的话 就会对象循环调用出现栈内存溢出 java.lang.StackOverflowError
    private Dept dept;

    public Emp(String name) {
        this.name = name;
    }
}

//部门表
@NoArgsConstructor
@AllArgsConstructor
@Data
class Dept {
    private String name;
    private List<Emp> emps;
}