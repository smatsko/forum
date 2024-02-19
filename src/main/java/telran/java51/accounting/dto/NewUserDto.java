package telran.java51.accounting.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewUserDto {
	String login;
	String password;
	String firstName;
	String lastName;
}
