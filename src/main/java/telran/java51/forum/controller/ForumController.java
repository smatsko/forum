package telran.java51.forum.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java51.forum.dto.CommentDto;
import telran.java51.forum.dto.MessageDto;
import telran.java51.forum.dto.PeriodDto;
import telran.java51.forum.service.ForumService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forum")
public class ForumController {

	final ForumService forumService;

	
    @PostMapping("/post/{user}")
	public MessageDto addMessage(@PathVariable("user") String user, @RequestBody MessageDto messageDto) {
		return forumService.addMessage(user, messageDto);
	}

	
	@GetMapping("/post/{postId}")
	public MessageDto findMessage(@PathVariable("postId") String id) {
		return forumService.findMessage(id);
	}

	@DeleteMapping("/post/{postId}")
	public MessageDto removeMessage(@PathVariable String id) {
	    return forumService.removeMessage(id);	
	}

	
	@PutMapping("/post/{postId}")
	public MessageDto updateMessage(@PathVariable("postId") String id, @RequestBody MessageDto messageDto) {
	   return forumService.updateMessage(id, messageDto);
	}
	
	
	@PutMapping("/post/{postId}/comment/{user}")
	public MessageDto addComment(@PathVariable("postId") String id, @PathVariable("user") String user, @RequestBody CommentDto commentDto) {
		return forumService.addComment(id, user, commentDto);
	}

	
	@PutMapping("/post/{postId}/like")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void likeMessage(@PathVariable("postId") String id) {
	     forumService.likeMessage(id);
	}
	
	@GetMapping("/posts/author/{user}")
	public Iterable<MessageDto> findMessagesByAuthor(@PathVariable("user") String user) {
		return forumService.findMessageByAuthor(user);
	}
	
	
	@PostMapping("/posts/tags")
	public Iterable<MessageDto> findMessagesByTags(@RequestBody List<String> tags) {
		return forumService.findMessagesByTags(tags);
	}
	
	@PostMapping("/posts/period")
	public Iterable<MessageDto> findMessagesByPeriod(@RequestBody PeriodDto periodDto) {
	    return forumService.findMessagesByPeriod(periodDto);
	}

 

}
