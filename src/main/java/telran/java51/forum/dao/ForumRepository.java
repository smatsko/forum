package telran.java51.forum.dao;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.repository.CrudRepository;

import telran.java51.forum.model.Message;

public interface ForumRepository extends CrudRepository<Message, String> {

	Stream<Message> findByAuthorIgnoreCase(String user);
	Stream<Message> findByTagsInIgnoreCase(List<String> tags);
    Stream<Message> findByDateCreatedBetween(LocalDate from, LocalDate to);
	
}

	