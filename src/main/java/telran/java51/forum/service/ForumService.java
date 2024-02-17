package telran.java51.forum.service;


import java.util.List;

import telran.java51.forum.dto.CommentDto;
import telran.java51.forum.dto.MessageDto;
import telran.java51.forum.dto.PeriodDto;


public interface ForumService {
	
	MessageDto addMessage(String user, MessageDto messageDto);

	MessageDto findMessage(String id);

	MessageDto removeMessage(String id);

	MessageDto updateMessage(String id, MessageDto messageDto);

	MessageDto addComment(String id, String user, CommentDto commentDto);

	void likeMessage(String id);

	Iterable<MessageDto> findMessageByAuthor(String user);

	Iterable<MessageDto> findMessagesByPeriod(PeriodDto periodDto);

	Iterable<MessageDto> findMessagesByTags(List<String> tags);

}
