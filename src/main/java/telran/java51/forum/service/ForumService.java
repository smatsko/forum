package telran.java51.forum.service;


import telran.java51.forum.dto.MessageDto;


public interface ForumService {
	
	MessageDto addMessage(MessageDto messageDto);

	MessageDto findMessage(String id);

}
