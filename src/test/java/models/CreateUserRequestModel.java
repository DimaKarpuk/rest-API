package models;

import lombok.Data;

@Data
public class CreateUserRequestModel {
    private String email, password;
}
