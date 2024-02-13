package telran.java51.forum.dao;


import org.springframework.data.repository.CrudRepository;

import telran.java51.forum.model.Message;

public interface ForumRepository extends CrudRepository<Message, String> {

/*
	Stream<Message> getAllBy();

	Stream<Message> findByNameIgnoreCase(String name);

	long countByNameInIgnoreCase(Set<String> names);

	@Query("{'scores.?0': {'$gt': ?1}}")
	Stream<Message> findByExamAndScoreGreaterThan(String exam, int score);
*/
}

	