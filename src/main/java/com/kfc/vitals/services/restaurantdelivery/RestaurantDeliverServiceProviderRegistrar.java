package com.kfc.vitals.services.restaurantdelivery;

import java.util.List;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.kfc.vitals.Utils;

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
		
		
		List<Restaurant> restaurants=Utils.readRestaurants(Utils.readFileAsString("restaurants.json"));
		
		
		for(Restaurant restaurant:restaurants ){
			List<RestaurantDeliveryServiceInput> restaurantServiceInput=Utils.readRestaurantDeliveryInput(Utils.readFileAsString("restaurantDeliveryProviderInput_"+ restaurant.getRefid() + ".json"));
			log.info("FOUND ::::::{}",restaurant.getName());
			service.addProvider(new RestaurantDeliveryServiceProvider(restaurant,restaurantServiceInput));
		}
		

	}

}
