package telran.java51.forum.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@EqualsAndHashCode(of = "id")
@Document(collection = "messages")
@NoArgsConstructor
public class Message {
	@Id
	String id;
	String title;
	String content;
	@Setter
    String author;
    @Setter
	LocalDateTime dateCreated;
    @Setter
    Set<String> tags = new HashSet<String>();
	@Setter
	long likes;
	@Setter
	Set<Comment> comment = new HashSet<Comment>();
}
