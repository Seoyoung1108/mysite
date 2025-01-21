package mysite.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import mysite.repository.UserRepository;
import mysite.vo.UserVo;

@Service
public class UserService {
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository=userRepository;
		this.passwordEncoder=passwordEncoder;
	}

	public void join(UserVo vo) {
		vo.setPassword(passwordEncoder.encode(vo.getPassword()));
		userRepository.insert(vo);
	}

	public UserVo getUser(String email, String password) {
		UserVo vo = userRepository.findByEmailAndPassword(email,password);
		return vo;
	}

	public UserVo getUser(String email) {
		UserVo userVo = userRepository.findByEmail(email, UserVo.class);
		
		if(userVo!=null) {
			userVo.setPassword("");
		}
		
		return userVo;
	}
	
	public UserVo getUser(Long id) {
		UserVo vo = userRepository.findById(id);
		return vo;
	}

	public void update(UserVo vo) {
		userRepository.update(vo);
	}
}
