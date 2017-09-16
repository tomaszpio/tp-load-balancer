package controller;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoadBalancerController {

    @RequestMapping("/route")
    public String route(@RequestParam(value="id")String param){
        return param;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String wrongArgumentsHandle(){
        return "Wrong Arguments passed";
    }
}
