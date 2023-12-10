package com.bloodbank.bloodbank.dto.login;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    @NotNull
    @NotBlank(message = "Значение 'Имя' не должно быть пустым.")
    private String name;
    @NotNull
    @NotBlank(message = "Значение 'Фамилия' не должно быть пустым.")
    private String surname;
    @Email
    @NotNull
    @NotBlank(message = "Значение 'e-mail' не должно быть пустым.")
    private String email;
    @NotBlank(message = "Значение 'Пароль' не должно быть пустым.")
    @NotNull
    private String password;
    private Long role_id;
    private Long employee_id;

    public UserDTO(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }
}
