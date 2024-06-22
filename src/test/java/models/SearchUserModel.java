package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SearchUserModel {
    private DataObject data;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataObject {
        private int id;
        private String email;
        private String first_name;
        private String last_name;
    }
}
