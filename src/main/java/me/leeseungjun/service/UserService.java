package me.leeseungjun.service;

import lombok.RequiredArgsConstructor;
import me.leeseungjun.domain.User;
import me.leeseungjun.dto.AddUserRequest;
import me.leeseungjun.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
//사용자 데이터를 데이터베이스에 저장하는 서비스 역할
// save 메서드는 AddUserRequest 객체를 받아 새로운 User 객체를 생성하고,
// 비밀번호를 안전하게 해시한 후 데이터베이스에 저장 저장된 사용자의 ID를 반환
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequest dto){
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                //패스워드 활성화
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .build()).getId();
    }
}
