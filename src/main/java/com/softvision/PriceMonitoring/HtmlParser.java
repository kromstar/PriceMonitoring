package com.softvision.PriceMonitoring;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class HtmlParser {

    private static HtmlParser instance = null;
    private double price;
    private Element priceElement;

    private HtmlParser() {
    }

    public static HtmlParser getInstance() {
        if (instance == null) {
            instance = new HtmlParser();
        }
        return instance;
    }

    public double parseAndGetPrice(String uri) {
        getPriceElement(uri);
        formatPrice();
        return price;
    }

    private void getPriceElement(String uri) {
        Document doc = null;
        try {
            doc = Jsoup.connect(uri).get();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        priceElement = doc.selectFirst("p[class~=product-new-price*]");
    }

    private void formatPrice() {
        String unformattedPrice = priceElement.text().replaceAll("[^0-9]", "");
        String formattedPrice = new StringBuilder(unformattedPrice).insert(unformattedPrice.length() - 2, ".").toString();
        price = Double.parseDouble(formattedPrice);
    }
}
