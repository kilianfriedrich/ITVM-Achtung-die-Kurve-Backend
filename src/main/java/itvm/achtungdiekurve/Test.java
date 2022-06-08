package itvm.achtungdiekurve;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

    @GetMapping("/test")
    String test() {
        return "hallo ben";
    }

}
