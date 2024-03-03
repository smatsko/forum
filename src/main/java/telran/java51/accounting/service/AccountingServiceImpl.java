package telran.java51.accounting.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java51.accounting.dao.UserRepository;
import telran.java51.accounting.dto.NewUserDto;
import telran.java51.accounting.dto.RoleDto;
import telran.java51.accounting.dto.UserDto;
import telran.java51.accounting.dto.exceptions.UserConflict;
import telran.java51.accounting.model.RolesEnum;
import telran.java51.accounting.model.UserAcc;
import telran.java51.accounting.dto.exceptions.UserNotFoundException;

import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;

@Service
@RequiredArgsConstructor
public class AccountingServiceImpl implements AccountingService, CommandLineRunner{

	final UserRepository userRepository;
	final ModelMapper modelMapper;
	final PasswordEncoder passwordEncoder;

	
	@Override
	public UserDto registerUser(NewUserDto newUserDto) {
		if (!userRepository.findById(newUserDto.getLogin()).isEmpty()) {
			throw new UserConflict();
		}
		UserAcc userAcc = modelMapper.map(newUserDto, UserAcc.class);
		String password = BCrypt.hashpw(newUserDto.getPassword(), BCrypt.gensalt());
		userAcc.setPassword(password );
		return modelMapper.map(userRepository.save(userAcc), UserDto.class);
	}

	@Override
	public UserDto getUserByLogin(String login) {
		UserAcc userAcc = userRepository.findById(login).orElseThrow(UserNotFoundException::new);
		return modelMapper.map(userAcc, UserDto.class);
	}

	@Override
	public UserDto deleteUser(String login) {
		UserAcc userAcc = userRepository.findById(login).orElseThrow(UserNotFoundException::new);
		userRepository.delete(userAcc);
		return modelMapper.map(userAcc, UserDto.class);
	}

	@Override
	public UserDto updateUser( String login, UserDto userDto) {
		UserAcc userAcc = userRepository.findById(login).orElseThrow(UserNotFoundException::new);
		if (userDto.getFirstName() != null) userAcc.setFirstName(userDto.getFirstName());
		if (userDto.getLastName() != null) userAcc.setLastName(userDto.getFirstName());
		return modelMapper.map(userRepository.save(userAcc), UserDto.class);		
	}

	@Override
	public RoleDto addRole(String login, RolesEnum role) {
		UserAcc userAcc = userRepository.findById(login).orElseThrow(UserNotFoundException::new);
	    userAcc.addRole(role); 	
		return modelMapper.map(userRepository.save(userAcc), RoleDto.class);			
	}

	@Override
	public RoleDto delRole(String login, RolesEnum role) {
		UserAcc userAcc = userRepository.findById(login).orElseThrow(UserNotFoundException::new);
	    userAcc.delRole(role); 	
		return modelMapper.map(userRepository.save(userAcc), RoleDto.class);			
	}

	@Override
	public void changePassword(String login, String newPassword) {
		UserAcc userAcc = userRepository.findById(login).orElseThrow(UserNotFoundException::new);
		userAcc.setPassword(newPassword);
		userRepository.save(userAcc);
	}

	@Override
	public void run(String... args) throws Exception {
		if (!userRepository.existsById("admin")) {
			String password = passwordEncoder.encode("admin");
			UserAcc userAccount = new UserAcc("admin", password, "", "", null);
			userAccount.addRole(RolesEnum.MODERATOR);
			userAccount.addRole(RolesEnum.ADMINISTRATOR);
			userRepository.save(userAccount);
		}

	}
	
}
