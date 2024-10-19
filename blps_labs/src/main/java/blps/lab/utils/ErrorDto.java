package blps.lab.utils;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorDto {
    private String message;

    public ErrorDto(String message) {
        this.message = message;
    }

}
