package telran.java51.accounting.service;

import telran.java51.accounting.dto.NewUserDto;
import telran.java51.accounting.dto.RoleDto;
import telran.java51.accounting.dto.UserDto;
import telran.java51.accounting.model.RolesEnum;

public interface AccountingService {

	UserDto registerUser(NewUserDto newUserDto);

	UserDto getUserByLogin(String login);

	UserDto deleteUser(String login);

	UserDto updateUser(String login, UserDto userDto);

	RoleDto addRole(String login, RolesEnum role);

	RoleDto delRole(String login, RolesEnum role);

	void changePassword(String login, String newPassword);
}
