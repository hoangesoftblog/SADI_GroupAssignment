package com.demo;

import com.demo.model.Category;
import com.demo.model.PriceHistory;
import com.demo.model.Product;
import com.demo.model.login.Role;
import com.demo.model.login.UserLogin;
import com.demo.repository.LoginRepository;
import com.demo.service.ProductService;
import com.demo.model.RandomInterface;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableCaching
public class SpringBootWithKafkaApplication implements CommandLineRunner {

	public static void main(String[] args) {

		SpringApplication.run(SpringBootWithKafkaApplication.class, args);
	}


	@Autowired
	LoginRepository repository;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	RandomInterface productService;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Service is ProductService: " + (productService instanceof ProductService));

		Role r1 = new Role(); r1.setRole("USER");
		Role r2 = new Role(); r1.setRole("ADMIN");

		UserLogin u = new UserLogin(); u.setUsername("user"); u.setPassword(passwordEncoder.encode("user"));
		u.setRoles(Arrays.asList(r1));
		repository.save(u);

		UserLogin admin = new UserLogin(); admin.setUsername("admin"); admin.setPassword(passwordEncoder.encode("admin"));
		admin.setRoles(Arrays.asList(r2));
		repository.save(admin);

        JSONParser parser = new JSONParser();
	    try (FileReader reader = new FileReader("data-12380.json")) {
			System.out.println("File exists.");
	    	Object obj = parser.parse(reader);

			JSONArray productList = (JSONArray) obj;

			for (int i = 0; i < 100 && i < productList.size(); i++) {
				JSONObject o = (JSONObject) productList.get(i);
				productService.add(parseProduct(o));
			}
		} catch (FileNotFoundException fileNotFoundException) {
	    	fileNotFoundException.printStackTrace();
		} catch (Exception e){
	    	e.printStackTrace();
		}
	}

	public Product parseProduct(JSONObject productJSON) {
		Product p = new Product();

		String name = (String) productJSON.get("name");
		String description = (String) productJSON.get("name");
		String brand = (String) productJSON.get("brand");
		String productAvatar = (String) productJSON.get("productAvatar");
		String url = (String) productJSON.get("url");

		Integer status = ((Long) productJSON.get("status")).intValue();
		Double rating = ((Double) productJSON.get("lowestPrice"));

		Long shopeeID = (Long) productJSON.get("shopeeID");
		Long shopeeShopID = (Long) productJSON.get("shopeeShopID");
		Long currentPrice = ((Double) productJSON.get("currentPrice")).longValue();
		Long highestPrice = ((Double) productJSON.get("highestPrice")).longValue();
		Long lowestPrice = ((Double) productJSON.get("lowestPrice")).longValue();
		Long stock = (Long) productJSON.get("stock");

		JSONArray historyList = (JSONArray) productJSON.get("histories");
		List<PriceHistory> histories = new ArrayList<>();
		historyList.forEach(o -> {
			PriceHistory history = parseHistory((JSONObject) o);
			histories.add(history);
		});

		JSONArray categoryList = (JSONArray) productJSON.get("categories");
		Set<Category> categories = new LinkedHashSet<>();
		categoryList.forEach(o -> {
			Category category = parseCategory((JSONObject) o);
			categories.add(category);
		});

		List<String> images = new ArrayList<>();
		((JSONArray) productJSON.get("images")).forEach(o -> {
			String str = (String) o;
			images.add(str);
		});

		// Set all properties
		p.setCategories(categories);
		p.setHistories(histories);
		p.setImages(images);
		p.setName(name);
		p.setDescription(description);
		p.setShopeeShopID(shopeeShopID);
		p.setShopeeID(shopeeID);
		p.setBrand(brand);
		p.setCurrentPrice(currentPrice);
		p.setHighestPrice(highestPrice);
		p.setLowestPrice(lowestPrice);
		p.setRating(rating);
		p.setStock(stock);
		p.setProductAvatar(productAvatar);
		p.setStatus(status);
		p.setUrl(url);

		return p;
	}

	public PriceHistory parseHistory(JSONObject object) {
		Long price = ((Double) object.get("price")).longValue();

		Date date;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse((String) object.get("date"));
		} catch (ParseException e) {
			date = new Date();
		}

		return new PriceHistory(price, date);
	}

	public Category parseCategory(JSONObject object) {
		Long shopeeCategoryID = (Long) object.get("shopeeCategoryID");
		String name = (String) object.get("name");
		return new Category(shopeeCategoryID, name);
	}
}
