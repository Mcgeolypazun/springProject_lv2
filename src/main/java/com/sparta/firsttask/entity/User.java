//package com.sparta.firsttask.entity;
//
//import com.sparta.firsttask.dto.ProfileRequestDto;
//import jakarta.persistence.*;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import java.util.List;
//
//@Entity
//@Getter
//@NoArgsConstructor
//@Table(name = "USERS")
//public class User extends Timestamped {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false, unique = true)
//    private String username;
//
//    @Column(nullable = false)
//    private String password;
//
//    @Column(nullable = false, unique = true)
//    private String email;
//
//    @Column(nullable = false)
//    private String intro;
//
//    @Column(nullable = false)
//    @Enumerated(value = EnumType.STRING)
//    private UserRole role;
//
//    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
//    private List<Post> postList;
//    @Builder
//    private User(Long id, String username, String password, String email, String intro, UserRole role) {
//        this.id = id;
//        this.username = username;
//        this.password = password;
//        this.email = email;
//        this.intro = intro;
//        this.role = role;
//    }
//
//}
