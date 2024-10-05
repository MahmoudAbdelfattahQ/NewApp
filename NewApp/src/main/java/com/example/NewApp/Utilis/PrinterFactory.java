package com.example.NewApp.Utilis;

import com.example.NewApp.delegation.ConsolPrinter;
import com.example.NewApp.delegation.FilePrinter;
import com.example.NewApp.delegation.Printer;
import com.example.NewApp.delegation.RealPrinter;

import static com.example.NewApp.Utilis.PrinterType.*;


public class PrinterFactory {
    private PrinterFactory() {

    }

    public static Printer getPrinter(PrinterType printerType) {
        return switch (printerType) {
            case REAL_PRINTER    -> new RealPrinter();
            case CONSOLE_PRINTER -> new ConsolPrinter();
            case FILE_PRINTER    -> new FilePrinter();
        };

        
    }
}
