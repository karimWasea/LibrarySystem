package Librar.Library.Modes;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "role")
@Data
public class Role {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;

   @Column(name = "name")
   private String name;

   @ManyToMany(mappedBy = "roles")
   private Set<User> users;}

// No need for explicit getters and setters with @Data annotation
