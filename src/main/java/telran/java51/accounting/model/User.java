package telran.java51.accounting.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(of = "login")
@Document(collection = "users")
public class User {
	@Id
	String login;
	@Setter
	String password;
	@Setter
	String firstName;
	@Setter
	String lastName;
	@Setter
	Set<RolesEnum> roles;

	public User() {
		roles = new HashSet<RolesEnum>();
		roles.add(RolesEnum.USER);
	}

	public User(String login, String password, String firstName, String lastName, Set<RolesEnum> roles) {
		this();
		this.login = login;
		this.login = password;
		this.firstName = firstName;
		this.lastName = lastName;
		if (roles != null) this.roles.addAll(roles);
	}

	public void addRole(RolesEnum role) {
		roles.add(role);
	}

	public void delRole(RolesEnum role) {
		roles.remove(role);
	}

}
