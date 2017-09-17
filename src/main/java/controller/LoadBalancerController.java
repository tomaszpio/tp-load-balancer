package controller;

import dispatching.RequestsDispatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@RestController
public class LoadBalancerController {

    private final RequestsDispatcher requestsDispatcher;

    public LoadBalancerController(RequestsDispatcher requestsDispatcher) {

        this.requestsDispatcher = requestsDispatcher;
    }

    @RequestMapping("/route")
    public String route(@RequestParam(value="id")String id){
        return this.requestsDispatcher.dispatch(id);
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public String wrongArgumentsHandle(){
        return "Wrong Arguments passed";
    }
}
