package com.kfc.vitals.services.restaurantdelivery;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@AllArgsConstructor
public class RestaurantDeliverServiceProviderRegistrar {
 

	RestaurantDeliveryService service;
	
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
    	log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>> APP STARTED");
    }
    
    
}