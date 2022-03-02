package com.softvision.PriceMonitoring;

public class PriceResolver implements Runnable {

    private final String uri;
    private final double initialPrice;
    private final String emailAddress;
    private double newPrice;
    private final HtmlParser htmlParser;
    private final EmailSender emailSender = EmailSender.getInstance();

    public PriceResolver(String uri, String emailAddress) {
        this.uri = uri;
        htmlParser = HtmlParser.getInstance();
        initialPrice = htmlParser.parseAndGetPrice(uri);
        this.emailAddress = emailAddress;
    }

    private boolean isPriceModified() {
        return initialPrice != newPrice;
    }

    private void updatePrice() {
        newPrice = htmlParser.parseAndGetPrice(uri);
    }

    @Override
    public void run() {
        updatePrice();
        if (isPriceModified()) {
            emailSender.sendMessage(uri, initialPrice, newPrice, emailAddress);
        } else {
            System.out.println("The price for " + uri + " is still " + newPrice);
        }
    }
}
