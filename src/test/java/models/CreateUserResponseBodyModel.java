package models;

import lombok.Data;

@Data
public class CreateUserResponseBodyModel {
    String token;
    int id;
}
