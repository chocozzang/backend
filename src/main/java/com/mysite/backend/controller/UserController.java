package com.mysite.backend.controller;

import com.mysite.backend.exception.UserNotFoundException;
import com.mysite.backend.model.User;
import com.mysite.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequiredArgsConstructor // 생성자를 lombok에서 자동으로 만들어줌
                         // 이게 없다면 @Autowired public UserController() {} 이하의 생성자를 정의해야함
public class UserController {

    // 객체를 자동으로 생성하여 스프링에서 관리하게끔 함
    // field 생성자 주입
    private final UserRepository userRepository;

//    @Autowired
//    public UserController(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    // RequestBody -> JSON 객체 형태로 바로 받기 위함
    @PostMapping("/user")
    User newUser(@RequestBody User newUser) {
       return userRepository.save(newUser);
    }

    @GetMapping("/users")
    List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    User getUserById(@PathVariable("id") Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    // 요청에 대한 업데이트할 User 객체를 받고, 주소에서 id 값을 받아옴
    // 해당 id에 대한 User를 새로 받아온 newUser의 값으로 대치함
    // 값을 새로 대치한 User를 DB에 저장함
    // REST에서 Update -> PutMapping
    @PutMapping("/user/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> {  user.setUsername(newUser.getUsername());
                                user.setName(newUser.getName());
                                user.setEmail(newUser.getEmail());
                                return userRepository.save(user);
        }).orElseThrow(() -> new UserNotFoundException(id));
    }

    // REST에서 Delete -> DeleteMapping
    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable Long id) {
        if(!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        } else {
            userRepository.deleteById(id);
            return "유저 아이디 : " + id + "는 삭제되었습니다.";
        }
    }
}
