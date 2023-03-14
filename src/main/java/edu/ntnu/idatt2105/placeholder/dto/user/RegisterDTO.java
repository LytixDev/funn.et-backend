package edu.ntnu.idatt2105.placeholder.dto.user;

import lombok.NonNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for registration.
 * Used to transfer registration data between the backend and the application.
 * @author Callum G.
 * @version 1.0
 * @date 13.3.2023
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RegisterDTO {
    @NonNull
    private String username;
    
    @NonNull
    private String email;
    
    @NonNull
    private String firstName;
    
    @NonNull
    private String lastName;
    
    @NonNull
    private String password;
}
