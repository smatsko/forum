package telran.java51.forum.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(of = "id")
@Document(collection = "messages")
public class Message {
	@Id
	String id;
	@Setter
	String title;
	@Setter
	String content;
	@Setter
    String author;
	LocalDateTime dateCreated;
    Set<String> tags;
	long likes;
	List<Comment> comments;

	public Message() {
		dateCreated = LocalDateTime.now();
		comments = new ArrayList<>();
		tags = new HashSet<String>();
	}

	public Message(String title, String content, String author, Set<String> tags) {
		this();
		this.title = title;
		this.content = content;
		this.author = author;
		this.tags = tags;
	}
	
	
	public boolean addTag(String tag) {
		return tags.add(tag);
	}
	
	public boolean removeTag(String tag) {
		return tags.remove(tag);
	}
	
	public void addLike() {
		likes++;
	}
	
	public boolean addComment(Comment comment) {
		return comments.add(comment);
	}
	
	public boolean removeComment(Comment comment) {
		return comments.remove(comment);
	}


}
