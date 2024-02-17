package telran.java51.forum.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java51.forum.dao.ForumRepository;
import telran.java51.forum.dto.CommentDto;
import telran.java51.forum.dto.MessageDto;
import telran.java51.forum.dto.PeriodDto;
import telran.java51.forum.dto.exceptions.MessageNotFoundException;
import telran.java51.forum.model.Comment;
import telran.java51.forum.model.Message;

@Service
@RequiredArgsConstructor
public class ForumServiceImpl implements ForumService {

	final ForumRepository forumRepository;
	final ModelMapper modelMapper;

	@Override
	public MessageDto addMessage(String user, MessageDto MessageDto) {
		Message message = modelMapper.map(MessageDto, Message.class);
		message.setAuthor(user);
		return modelMapper.map(forumRepository.save(message), MessageDto.class);
	}

	@Override
	public MessageDto findMessage(String id) {
		Message message = forumRepository.findById(id).orElseThrow(MessageNotFoundException::new);
		return modelMapper.map(message, MessageDto.class);
	}

	@Override
	public MessageDto removeMessage(String id) {
		Message message = forumRepository.findById(id).orElseThrow(MessageNotFoundException::new);
		forumRepository.delete(message);
		return modelMapper.map(message, MessageDto.class);
	}

	@Override
	public MessageDto updateMessage(String id, MessageDto messageDto) {
		Message message = forumRepository.findById(id).orElseThrow(MessageNotFoundException::new);
		String content = messageDto.getContent();
		if (content != null) {
			message.setContent(content);
		}
		String title = messageDto.getTitle();
		if (title != null) {
			message.setTitle(title);
		}
		Set<String> tags = messageDto.getTags();
		if (tags != null) {
			tags.forEach(message::addTag);
		}
		forumRepository.save(message);

		return modelMapper.map(message, MessageDto.class);
	}

	@Override
	public MessageDto addComment(String id, String user, CommentDto commentDto) {
		Message message = forumRepository.findById(id).orElseThrow(MessageNotFoundException::new);
		Comment comment = new Comment(user, commentDto.getMessage());
		message.addComment(comment);
		forumRepository.save(message);
		return modelMapper.map(message, MessageDto.class);
	}

	@Override
	public void likeMessage(String id) {
		Message message = forumRepository.findById(id).orElseThrow(MessageNotFoundException::new);
		message.addLike();
		forumRepository.save(message);

	}

	@Override
	public Iterable<MessageDto> findMessageByAuthor(String user) {
		return forumRepository.findByAuthorIgnoreCase(user)
				.map(m -> modelMapper.map(m, MessageDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public Iterable<MessageDto> findMessagesByPeriod(PeriodDto periodDto) {
		return forumRepository.findByDateCreatedBetween(periodDto.getDateFrom(), periodDto.getDateTo()  )
				.map(m -> modelMapper.map(m, MessageDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public Iterable<MessageDto> findMessagesByTags(List<String> tags) {
		return forumRepository.findByTagsIgnoreCase(tags)
				.map(m -> modelMapper.map(m, MessageDto.class))
				.collect(Collectors.toList());
	}	

}
