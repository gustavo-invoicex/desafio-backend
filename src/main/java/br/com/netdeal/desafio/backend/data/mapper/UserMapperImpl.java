package br.com.netdeal.desafio.backend.data.mapper;

import br.com.netdeal.desafio.backend.data.entity.User;
import br.com.netdeal.desafio.backend.data.model.PasswordSecurityLevel;
import br.com.netdeal.desafio.backend.data.model.UserDTO;
import java.util.ArrayList;
import java.util.Objects;
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
                user.getUsers() != null ?
                        user.getUsers().stream()
                                .filter(Objects::nonNull)
                                .map(this::toUserDTO)
                                .collect(Collectors.toList()) : new ArrayList<>(),
                user.getChiefUserId()
        );
    }

    @Override
    public User toUser(UserDTO userDTO) {
        return new User(
                userDTO.getName(),
                userDTO.getPassword(),
                userDTO.getScore(),
                userDTO.getPasswordSecurityLevel().name(),
                userDTO.getUserId()
        );
    }
}
