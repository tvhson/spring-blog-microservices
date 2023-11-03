package com.tvhson.userservice.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    @NotEmpty
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 4, message = "Username must be at least 4 characters")
    private String userName;

    @NotEmpty
    @Size(min = 3, max = 10, message = "Password must be between 3 and 10 characters")
    private String password;

    private String about;

    @JsonIgnore
    public String getPassword() {
        return this.password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }
}
