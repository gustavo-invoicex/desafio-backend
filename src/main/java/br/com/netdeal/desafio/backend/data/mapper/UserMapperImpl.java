package br.com.netdeal.desafio.backend.data.mapper;

import br.com.netdeal.desafio.backend.data.entity.User;
import br.com.netdeal.desafio.backend.data.model.PasswordSecurityLevel;
import br.com.netdeal.desafio.backend.data.model.UserDTO;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toUserDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getPassword(),
                user.getScore(),
                PasswordSecurityLevel.valueOf(user.getPasswordSecurityLevel()),
                user.getUsers().stream().map(this::toUserDTO).collect(Collectors.toList())
        );
    }

    @Override
    public User toUser(UserDTO userDTO) {
        return new User(
                userDTO.getName(),
                userDTO.getPassword(),
                userDTO.getScore(),
                userDTO.getPasswordSecurityLevel().name()
        );
    }
}
