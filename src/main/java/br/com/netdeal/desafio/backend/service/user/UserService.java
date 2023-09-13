package br.com.netdeal.desafio.backend.service.user;

import br.com.netdeal.desafio.backend.data.model.UserDTO;
import br.com.netdeal.desafio.backend.service.exception.UserNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public UserDTO saveUser(UserDTO userDTO);

    public UserDTO updateUser(UserDTO userDTO) throws UserNotFoundException;

    public void removeUser(Long userId) throws UserNotFoundException;

    public List<UserDTO> listAllUsers();
}
