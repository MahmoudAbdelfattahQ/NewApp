package com.example.NewApp.delegation;


import lombok.extern.slf4j.Slf4j;

/**
 * Delegated Class
 * @author Mahmoud Abdelfattah
 */

@Slf4j
public class ConsolPrinter implements Printer{

    @Override
    public void print(String message) {
        log.info("printer from delegated class as ConsolePrinter ,{}" ,message);
    }
}
