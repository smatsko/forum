package telran.java51.accounting.dao;

import org.springframework.data.repository.CrudRepository;
import telran.java51.accounting.model.UserAcc;

public interface UserRepository extends CrudRepository<UserAcc, String>{

}
