package telran.java51.forum.model;

import java.time.LocalDateTime;

import lombok.Setter;

public class Comment {
	String user;
	String message;
	LocalDateTime dateCreated;    
	@Setter
	long likes;	
}
