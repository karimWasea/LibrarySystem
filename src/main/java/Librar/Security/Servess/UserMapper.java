package Librar.Security.Servess;






















import Librar.Library.Modes.Dtos.UserDTO;

import Librar.Library.Modes.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setUsername(user.getUsername());
userDTO.setPassword(user.getPassword());
        return userDTO;
    }

    public User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUsername(userDTO.getUsername());
user.setPassword(userDTO.getPassword());
        return user;
    }
}
