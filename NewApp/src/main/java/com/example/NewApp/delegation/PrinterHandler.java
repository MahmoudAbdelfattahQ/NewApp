package com.example.NewApp.delegation;

public class PrinterHandler {

    private final Printer printer;

    public PrinterHandler(Printer printer) {
        this.printer = printer;
    }

    public void print(String message) {
        printer.print(message);
    }
}
