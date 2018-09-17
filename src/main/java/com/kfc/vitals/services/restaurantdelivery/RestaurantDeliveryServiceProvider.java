package com.kfc.vitals.services.restaurantdelivery;

import java.util.List;

import com.kfc.vitals.ServiceProvider;

import lombok.experimental.Delegate;

/**
 * Represents a Restaurant in conjunction with a particular service it provides.
 * Multiple separate instances would exist if the restaurant provides multiple
 * services
 * 
 * @author Girish Lakshmanan
 *
 */

public class RestaurantDeliveryServiceProvider implements ServiceProvider<RestaurantDeliveryServiceInput> {

	private List<RestaurantDeliveryServiceInput> checkInput;

	@Delegate
	private Restaurant restaurant;

	RestaurantDeliveryServiceProvider(Restaurant restaurant, List<RestaurantDeliveryServiceInput> checkInput) {
		this.checkInput = checkInput;
		this.restaurant = restaurant;
	}

	@Override
	public List<RestaurantDeliveryServiceInput> getProviderCheckInputList() {
		return checkInput;
	}

	@Override
	public String toString() {
		return restaurant.getRefid() + ":" + restaurant.getName();
	}

	@Override
	public String getId() {
		return restaurant.getRefid();
	}

}
