package telran.java51.security;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java51.forum.dao.ForumRepository;
import telran.java51.forum.model.Message;

@Service("customSecurity")
@RequiredArgsConstructor
public class CustomWebSecurity {
	final ForumRepository forumRepository;
 
 boolean checkPostAuthor(String postId, String userName) {
	 Message msg = forumRepository.findById(postId).orElse(null);
		return msg != null && userName.equals(msg.getAuthor());
	}
}
