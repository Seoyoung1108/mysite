package mysite.service;

import org.springframework.stereotype.Service;

import mysite.repository.UserRepository;
import mysite.vo.UserVo;

@Service
public class UserService {
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository=userRepository;
	}

	public void join(UserVo vo) {
		userRepository.insert(vo);
		
	}

	public UserVo getUser(String email, String password) {
		UserVo vo = userRepository.findByEmailAndPassword(email,password);
		return vo;
	}
}
