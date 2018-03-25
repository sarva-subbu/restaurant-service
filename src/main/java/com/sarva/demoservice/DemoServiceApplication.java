package com.sarva.demoservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class DemoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoServiceApplication.class, args);
	}
}

@RestController
class RestaurantController {
	
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	public RestaurantController(RestaurantRepository restaurantRepository) {
		this.restaurantRepository = restaurantRepository;
	}
	
	@PostMapping("/restaurants/insert/{count}")
	public String restaurantsRandomPost(@PathVariable int count, @RequestBody Restaurant restaurant) {
		List<Restaurant> restaurants = new ArrayList<Restaurant>();
		IntStream.rangeClosed(1, count).forEach(i -> restaurants
				.add(new Restaurant(restaurant.getName() + "-" + i, new Random().nextDouble(), "vegan")));

		long startTime = System.currentTimeMillis();
		restaurantRepository.insert(restaurants);
		long endTime = System.currentTimeMillis();
		System.out.println((endTime - startTime));

		return "done and took -> " + (endTime - startTime);
	}
	
	@GetMapping("/restaurants")
	public List<Restaurant> allRestaurants() {
		return restaurantRepository.findAll();
	}
}