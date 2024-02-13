package telran.java51.forum.dto;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
	String messageId;
	String title;
	String content;
	@Setter
    String author;
	@Setter
    LocalDateTime dateCreated;
	Set<String> tags;
	long likes;
}
