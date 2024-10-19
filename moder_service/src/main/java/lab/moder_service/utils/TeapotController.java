package lab.moder_service.utils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
@Tag(name = "Чайник")
public class TeapotController {

    @Operation(summary = "ТЫ ЧАЙНИК???")
    @GetMapping("/teapot")
    public ResponseEntity<Void> isTeapot() {
        return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
    }
}
