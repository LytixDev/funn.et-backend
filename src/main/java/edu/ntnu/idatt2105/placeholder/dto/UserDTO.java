package edu.ntnu.idatt2105.placeholder.dto;

import edu.ntnu.idatt2105.placeholder.model.Role;

import lombok.NonNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for user.
 * Used to transfer user data between the backend and the application.
 * @author Callum G.
 * @version 1.0
 * @date 17.3.2023
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserDTO {
        
    @NonNull
    private String username;

    @NonNull
    private String email;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private Role role;
}
