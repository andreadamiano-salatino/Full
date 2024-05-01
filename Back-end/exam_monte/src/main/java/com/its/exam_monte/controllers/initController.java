package com.its.exam_monte.controllers;

import com.its.exam_monte.models.Chef;
import com.its.exam_monte.models.Ristorante;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@RestController
public class initController {

    /*@RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView welcome(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("prenotazione.html");
        return modelAndView;
    }*/
    @GetMapping("/init")
    public String init(){
        ArrayList<Chef> chefs = new ArrayList<Chef>();

        Chef chef1 = new Chef("Mario","Rossi", "mario.rossi@mail.com", "3211988791", "chef");
        Chef chef2 = new Chef("Giorgio","Bianchi", "giorgio.bianchi@mail.com", "3211988792", "chef");
        Chef chef3 = new Chef("Luca","Marinelli", "luca.marinelli@mail.com", "3211988793", "chef");
        Chef chef4 = new Chef("Valeria","Lombardi", "valeria.lombardi@mail.com", "3211988794", "chef");
        Chef chef5 = new Chef("Marco","Fanizzi", "marco.fanizzi@mail.com", "3211988795", "chef");

        chefs.add(chef1);
        chefs.add(chef2);
        chefs.add(chef3);
        chefs.add(chef4);
        chefs.add(chef5);

        ArrayList<Ristorante> ristoranti= new ArrayList<Ristorante>();

        Ristorante ristorante1 = new Ristorante("Ristorante del Sole", "via lombardi 1");
        Ristorante ristorante2 = new Ristorante("Mangia come puoi", "via milano 2");

        ristoranti.add(ristorante1);
        ristoranti.add(ristorante2);



        return "init";
    }
}
