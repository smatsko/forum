package telran.java51.accounting.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java51.accounting.dao.UserRepository;
import telran.java51.accounting.dto.NewUserDto;
import telran.java51.accounting.dto.RoleDto;
import telran.java51.accounting.dto.UserDto;
import telran.java51.accounting.dto.exceptions.UserAlreadyReported;
import telran.java51.accounting.model.User;
import telran.java51.accounting.dto.exceptions.UserNotFoundException;

import org.modelmapper.ModelMapper;

@Service
@RequiredArgsConstructor
public class AccountingServiceImpl implements AccountingService {

	final UserRepository userRepository;
	final ModelMapper modelMapper;

	@Override
	public UserDto registerUser(NewUserDto newUserDto) {
		if (!userRepository.findById(newUserDto.getLogin()).isEmpty()) {
			throw new UserAlreadyReported();
		}
		User user = modelMapper.map(newUserDto, User.class);
		return modelMapper.map(userRepository.save(user), UserDto.class);
	}

	@Override
	public UserDto getUserByLogin(String login) {
		User user = userRepository.findById(login).orElseThrow(UserNotFoundException::new);
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto deleteUser(String login) {
		User user = userRepository.findById(login).orElseThrow(UserNotFoundException::new);
		userRepository.delete(user);
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto updateUser( String login, UserDto userDto) {
		User user = userRepository.findById(login).orElseThrow(UserNotFoundException::new);
		if (userDto.getFirstName() != null) user.setFirstName(userDto.getFirstName());
		if (userDto.getLastName() != null) user.setLastName(userDto.getFirstName());
		return modelMapper.map(userRepository.save(user), UserDto.class);		
	}

	@Override
	public RoleDto addRole(String login, String role) {
		User user = userRepository.findById(login).orElseThrow(UserNotFoundException::new);
	    user.addRole(role); 	
		return modelMapper.map(userRepository.save(user), RoleDto.class);			
	}

	@Override
	public RoleDto delRole(String login, String role) {
		User user = userRepository.findById(login).orElseThrow(UserNotFoundException::new);
	    user.delRole(role); 	
		return modelMapper.map(userRepository.save(user), RoleDto.class);			
	}

}
