package models;

import lombok.Data;

@Data
public class CreateUserRequestModel {
    String email, password;
}
