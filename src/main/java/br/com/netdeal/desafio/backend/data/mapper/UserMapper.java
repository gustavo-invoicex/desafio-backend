package br.com.netdeal.desafio.backend.data.mapper;

import br.com.netdeal.desafio.backend.data.entity.User;
import br.com.netdeal.desafio.backend.data.model.UserDTO;

public interface UserMapper {
    public UserDTO toUserDTO(User user);

    public User toUser(UserDTO userDTO);
}
