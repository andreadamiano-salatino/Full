package com.example.booking.controllers;

import com.example.booking.models.Cliente;
import com.example.booking.models.Prenotazione;
import com.example.booking.models.Ristorante;
import com.example.booking.repositories.DatabaseComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class bookController {
    Cliente cliente = new Cliente();
    Prenotazione prenotazione = new Prenotazione();

    @Autowired
    DatabaseComponent db;


    @PostMapping("/prenotazione")
    public ModelAndView passParametersWithModelAndView(@RequestBody MultiValueMap<String, String> userFormData) throws SQLException {

        String nome = userFormData.get("nome").get(0);
        String cognome = userFormData.get("cognome").get(0);
        String email = userFormData.get("email").get(0);
        String telefono = userFormData.get("telefono").get(0);
        String note = userFormData.get("note").get(0);
        int coperti = Integer.parseInt(userFormData.get("coperti").get(0));
        String ristorante = userFormData.get("ristorante").get(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDateTime dataInizio = LocalDate.parse(userFormData.get("data-inizio").get(0),formatter).atStartOfDay();
        LocalDateTime dataFine = LocalDate.parse(userFormData.get("data-fine").get(0),formatter).atStartOfDay();

        cliente.setNome(nome);
        cliente.setCognome(cognome);
        cliente.setEmail(email);
        cliente.setCellulare(telefono);

        prenotazione.setNote(note);
        Ristorante ristoranteOgg = new Ristorante();
        ristoranteOgg.setNome(ristorante);
        prenotazione.setRistorante(ristoranteOgg);
        prenotazione.setCoperti(coperti);
        prenotazione.setCliente(cliente);

        ModelAndView modelAndView = new ModelAndView("date");
        List<String> date = new ArrayList<>();

        //date.add(dataInizio.plusHours(2).toString());
        //date.add(dataInizio.plusHours(4).toString());
        //date.add(dataInizio.plusHours(6).toString());
        //List<LocalDateTime> dates = db.getAvailableDates(ristorante, dataInizio, dataFine);
        List<LocalDateTime> dates = new ArrayList<>();
        dates.add(LocalDateTime.parse("2024-05-01T12:00"));
        dates.add(LocalDateTime.parse("2024-05-05T12:00"));
        dates.add(LocalDateTime.parse("2024-05-07T12:00"));
        modelAndView.addObject("date", dates);

        return modelAndView;
    }

    @PostMapping("/finalizzaPrenotazione")
    public ModelAndView finalizzaPrenotazione(@RequestBody MultiValueMap<String, String> userFormData){
        LocalDateTime data = LocalDateTime.parse(userFormData.get("date-time").get(0));
        prenotazione.setData(data);

        //int idCliente = db.insertClienteAndGetId(cliente);
        //db.savePrenotazione;

        ModelAndView modelAndView = new ModelAndView("conferma");
        modelAndView.addObject("codice", "1223345");

        return modelAndView;
    }
}
