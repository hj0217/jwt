package com.jwt.jwt.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Entity
@SequenceGenerator(name="user_seq", sequenceName = "user_seq")
public class User_entity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private Long id;
    private String username;
    private String password;
    private String roles; // USER,ADMIN // role이 2개 이상 있을 수도 있기 때문에...
    @CreationTimestamp
    private Timestamp createDate;


    // ENUM으로 안하고 ,로 해서 구분해서 ROLE을 입력 -> 그걸 파싱!!
    public List<String> getRoleList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>(); // null 방지 빈객체 반환
    }
}
