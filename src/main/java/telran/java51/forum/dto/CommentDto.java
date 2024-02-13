package telran.java51.forum.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
	@Setter
    String user;
    String message;
    @Setter
    LocalDateTime dateCreated;
    @Setter
    long likes;
	
}
