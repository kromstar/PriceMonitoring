package com.softvision.PriceMonitoring;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RestController
public class FarteApplicationController {

    @RequestMapping(path = "/api/monitor")
    public String runAMonitor(@RequestParam("url") String url, @RequestParam("emailAddress") String emailAddress) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);
        PriceResolver myItem;
        myItem = new PriceResolver(url, emailAddress);
        executorService.scheduleAtFixedRate(myItem, 0, 5, TimeUnit.MINUTES);
        return "Started monitoring the price of " + url + ". An e-mail will be sent when the price updates";
    }
}
