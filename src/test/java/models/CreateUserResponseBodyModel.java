package models;

import lombok.Data;

@Data
public class CreateUserResponseBodyModel {
    private String token;
    private int id;
}
