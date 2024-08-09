package Librar.Security.Controller;

import Librar.Library.Modes.Role;
import Librar.Security.Servess.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        if (role.getName() == null || role.getName().isEmpty()) {
            return ResponseEntity.badRequest().build(); // Handle invalid role creation request
        }
        try {
            Role createdRole = roleService.createRole(role.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRole); // Return HTTP 201 Created
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Handle specific exception
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Handle general exceptions
        }
    }

    @GetMapping("/{name}")
    public ResponseEntity<Role> getRoleByName(@PathVariable String name) {
        return roleService.findRoleByName(name)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Global exception handler method for this controller
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred: " + e.getMessage());
    }
}
