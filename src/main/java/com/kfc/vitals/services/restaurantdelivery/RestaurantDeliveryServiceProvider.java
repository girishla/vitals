package com.kfc.vitals.services.restaurantdelivery;

import com.kfc.vitals.ServiceProvider;

import lombok.experimental.Delegate;


/**
 * Represents a Restaurant in conjunction with a particular service it provides.
 * Multiple separate instances would exist if the restaurant provides multiple services
 * @author Girish Lakshmanan
 *
 */
public  class RestaurantDeliveryServiceProvider implements ServiceProvider<RestaurantDeliveryServiceInput> {

	private RestaurantDeliveryServiceInput input;
	
	@Delegate
	private Restaurant restaurant;
	
	RestaurantDeliveryServiceProvider(Restaurant restaurant,RestaurantDeliveryServiceInput input){
		this.input=input;
		this.restaurant=restaurant;
	}

	@Override
	public RestaurantDeliveryServiceInput getProviderInfo() {
		return input;
	}

	
}
