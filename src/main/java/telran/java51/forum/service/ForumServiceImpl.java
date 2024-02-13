package telran.java51.forum.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java51.forum.dao.ForumRepository;
import telran.java51.forum.dto.MessageDto;
import telran.java51.forum.dto.exceptions.MessageNotFoundException;
import telran.java51.forum.model.Message;

@Service
@RequiredArgsConstructor
public class ForumServiceImpl implements ForumService {

	
	final ForumRepository forumRepository;
	final ModelMapper modelMapper;

	@Override
	public MessageDto addMessage(MessageDto MessageDto) {
		Message message = modelMapper.map(MessageDto, Message.class);
		return modelMapper.map(forumRepository.save(message), MessageDto.class);
	}

	@Override
	public MessageDto findMessage(String id) {
		Message message = forumRepository.findById(id).orElseThrow(MessageNotFoundException::new);
		return modelMapper.map( message, MessageDto.class);
	}
	
}
