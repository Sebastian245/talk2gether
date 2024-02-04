package backend.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import backend.backend.repositories.ReunionVirtualRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class HomeController {


    @Autowired
    private ReunionVirtualRepository reunionVirtualRepository;

    @GetMapping("/hello")
    @ResponseBody
    public String sayHello(@RequestParam(name = "name", required = false, defaultValue = "World") String name) {
        return "Hello, " + name + "!";
    }

    @GetMapping("/prueba")
    @ResponseBody
    public ResponseEntity<?> prueba(@RequestParam Long idCuenta){
        return ResponseEntity.status(HttpStatus.OK).body(reunionVirtualRepository.obtenerIdReunionVirtualActivaPorIdCuenta(idCuenta));
    }

}