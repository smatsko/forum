package telran.java51.forum.controller;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java51.forum.dto.CommentDto;
import telran.java51.forum.dto.MessageDto;
import telran.java51.forum.dto.PeriodDto;
import telran.java51.forum.dto.TagsDto;
import telran.java51.forum.service.ForumService;

@RestController
@RequiredArgsConstructor
public class ForumController {

	final ForumService forumService;

	
    @PostMapping("/forum/post/{user}")
	public MessageDto addMessage(@PathVariable("user") String user, @RequestBody MessageDto messageDto) {
		messageDto.setAuthor(user);
		messageDto.setDateCreated(LocalDateTime.now());
		return forumService.addMessage(messageDto);
	}

	
	@GetMapping("/forum/post/{postId}")
	public MessageDto findMessage(@PathVariable("postId") String id) {
		return forumService.findMessage(id);
	}

	@PutMapping("/forum/post/{postId}/like")
	public void likeMessage(@PathVariable("postId") String id) {
	//	return forumService.kikeMessage(id);
	}
	
	@GetMapping("/forum/post/author/{user}")
	public List<MessageDto> findMessagesByAuthor(@PathVariable("user") String user) {
		//return forumService.findMessageByAuthor(user);
		return null;
	}
	
	@PutMapping("/forum/post/{postId}/{user}")
	public MessageDto addComment(@PathVariable("postId") String id, @PathVariable("user") String user, @RequestBody CommentDto commentDto) {
		//return forumService.addComment(commentDto);
		return null;
	}

	
	@DeleteMapping("/forum/post/{postId}")
	public MessageDto removeMessage(@PathVariable String id) {
	//	return forumService.removeMessage(id);
		return null;	
	}
	
	@PostMapping("/forum/post/tags")
	public List<MessageDto> findMessagesByTags(@RequestBody TagsDto tagsDto) {
		//return forumService.findMessagesByTags(tagsDto);
		return null;
	}
	
	@PostMapping("/forum/post/period")
	public List<MessageDto> findMessagesByPeriod(@RequestBody PeriodDto periodDto) {
		//return forumService.findMessagesByPeriod(periodDto);
		return null;
	}

    //@PutMapping("/forum/post/{postId}")
	//public MessageDto updateMessage(@PathVariable("postId") String id, @RequestBody MessageDto messageDto) {
		//return forumService.updateMessage(messageDto);
    //	return null;
	
    //}


}
