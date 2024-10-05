package com.example.NewApp.controller;


import com.example.NewApp.Utilis.PrinterFactory;
import com.example.NewApp.Utilis.PrinterType;
import com.example.NewApp.delegation.Printer;
import com.example.NewApp.delegation.PrinterHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("delegator")
public class DelegatorController {

    @PostMapping("printer/{printerType}")
    public void printer(@PathVariable String printerType, @RequestParam String massage) {

        Printer printer =  PrinterFactory.getPrinter(PrinterType.valueOf(printerType.toUpperCase()));
        PrinterHandler printerHandler = new PrinterHandler(printer);
        printerHandler.print(massage);

    }
}
