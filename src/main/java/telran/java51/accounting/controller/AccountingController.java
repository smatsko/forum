package telran.java51.accounting.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java51.accounting.dto.NewUserDto;
import telran.java51.accounting.dto.RoleDto;
import telran.java51.accounting.dto.UserDto;
import telran.java51.accounting.service.AccountingService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountingController {
	
	final AccountingService accountingService;

	@PostMapping("/register")
	public UserDto regiserUser(@RequestBody NewUserDto newUserDto) {
		return accountingService.registerUser(newUserDto);
	}

	@GetMapping("/user/{login}")
	public UserDto getUserByLogin(@PathVariable("login") String login) {
		return accountingService.getUserByLogin(login);
	}

	@DeleteMapping("/user/{login}")
	public UserDto deleteUser(@PathVariable String login) {
	    return accountingService.deleteUser(login);	
	}

	@PutMapping("/user/{login}")
	public UserDto updateUser(@PathVariable("login") String login, @RequestBody UserDto userDto) {
		return accountingService.updateUser( login, userDto);		
	}

	@PutMapping("/user/{login}/role/{role}")
	public RoleDto addRole(@PathVariable("login") String login, @PathVariable("role") String role ) {
		return accountingService.addRole( login, role);		
	}

	@DeleteMapping("/user/{login}/role/{role}")
	public RoleDto delRole(@PathVariable("login") String login, @PathVariable("role") String role ) {
		return accountingService.delRole( login, role);		
	}

	
}
