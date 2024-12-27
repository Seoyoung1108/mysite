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

	public void login(UserVo vo) {
		// TODO Auto-generated method stub
		
	}
}
