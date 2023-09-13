package br.com.netdeal.desafio.backend.service.user;

import br.com.netdeal.desafio.backend.data.entity.User;
import br.com.netdeal.desafio.backend.data.mapper.UserMapper;
import br.com.netdeal.desafio.backend.data.model.UserDTO;
import br.com.netdeal.desafio.backend.repository.UserRepository;
import br.com.netdeal.desafio.backend.service.exception.UserNotFoundException;
import br.com.netdeal.desafio.backend.service.validator.ValidatorPasswordFacade;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    private final ValidatorPasswordFacade validatorPasswordFacade;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, UserMapper mapper, ValidatorPasswordFacade validatorPasswordFacade, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.validatorPasswordFacade = validatorPasswordFacade;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        userDTO.setScore(validatorPasswordFacade.obtainScore(userDTO.getPassword()));
        userDTO.setPasswordSecurityLevel(validatorPasswordFacade.obtainPasswordSecurityLevel(userDTO.getPassword()));
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = repository.save(mapper.toUser(userDTO));
        return mapper.toUserDTO(user);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) throws UserNotFoundException {
        User user = repository.findById(userDTO.getId()).orElse(null);
        if (user == null) throw new UserNotFoundException("User not found");
        userDTO.setId(user.getId());
        repository.save(mapper.toUser(userDTO));
        return mapper.toUserDTO(user);
    }

    @Override
    public void removeUser(Long userId) throws UserNotFoundException {
        User user = repository.findById(userId).orElse(null);
        if (user == null) throw new UserNotFoundException("User not found");

        repository.deleteById(user.getId());
    }

    @Override
    public List<UserDTO> listAllUsers() {
        return repository.findAll().stream().map(mapper::toUserDTO).collect(Collectors.toList());
    }
}
