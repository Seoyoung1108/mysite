package mysite.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import mysite.repository.BoardRepository;
import mysite.repository.GuestbookRepository;
import mysite.vo.BoardVo;
import mysite.vo.UserVo;

@Service
public class BoardService {
	private BoardRepository boardRepository;
	
	public BoardService(BoardRepository boardRepository) {
		this.boardRepository=boardRepository;
	}

	public List<BoardVo> getContentsList(int currentPage){
		
		List<BoardVo> vo = boardRepository.findByPage(currentPage);
		
		return vo;
	}

	public void addContents(BoardVo vo) {
		boardRepository.insert(vo);		
	}

	public BoardVo getContents(Long id) {
		BoardVo vo = boardRepository.findById(id);
		BoardVo vo1 = new BoardVo();
		vo1.setId(id);
		vo1.setHit(vo.getHit()+1);
		boardRepository.hitUp(vo1);
		return vo;
	}

	public void updateContents(BoardVo vo) {
		boardRepository.modify(vo);
	}

	public void deleteContents(Long id) {
		boardRepository.delete(id);	
	}

	public void replyContents(BoardVo vo) {
		boardRepository.reply(vo);		
	}
	
	public int countContents() {
		return boardRepository.findAll();
	}

}
