package Librar.Library.Modes.Dtos;

import Librar.Library.Modes.Role;
import Librar.Library.Modes.Token;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UserDTO {
    private String password;
    private Integer id;
    private String firstName;
    private String lastName;
    private String username;

}