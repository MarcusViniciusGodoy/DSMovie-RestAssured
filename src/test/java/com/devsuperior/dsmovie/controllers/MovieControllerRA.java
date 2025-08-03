package com.devsuperior.dsmovie.controllers;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import com.devsuperior.dsmovie.tests.TokenUtil;

public class MovieControllerRA {

	private String clientUsername, clientPassword, adminUsername, adminPassword;
	private String adminToken, clientToken, invalidToken;
	private Long existingMovieId, nonExistingMovieId, dependentMovieId;
	private String movieName;

	void setUp() throws Exception{

		baseURI = "http://localhost:8080";
		
		clientUsername = "maria@gmail.com";
		clientPassword = "123456";
		adminUsername = "alex@gmail.com";
		adminPassword = "123456";
		
		clientToken = TokenUtil.obtainAccessToken(clientUsername, clientPassword);
		adminToken = TokenUtil.obtainAccessToken(adminUsername, adminPassword);
		invalidToken = adminToken + "xpto";

		movieName = "";

	}
	
	@Test
	public void findAllShouldReturnOkWhenMovieNoArgumentsGiven() {
        given()
            .get("/movies")
        .then()
            .statusCode(200)
            .body("content.title", hasItems(
                "The Witcher",
                "Venom: Tempo de Carnificina",
                "O Espetacular Homem-Aranha 2: A Ameaça de Electro"
            ));
    }
	
	@Test
	public void findAllShouldReturnPagedMoviesWhenMovieTitleParamIsNotEmpty() {		
		given()
			.get("/movies?name={movieName}", movieName)
		.then()
			.statusCode(200)
			.body("content.id[0]", is(1))
			.body("content.score[0]", is(4.5))
			.body("content.count[0]", is(2))
			.body("content.title[0]", equalTo("The Witcher"))
			.body("content.image[0]", equalTo("https://www.themoviedb.org/t/p/w533_and_h300_bestv2/jBJWaqoSCiARWtfV0GlqHrcdidd.jpg"));
	}
	
	@Test
	public void findByIdShouldReturnMovieWhenIdExists() {	
		existingMovieId = 2L;
		
		given()
			.get("/movies/{id}", existingMovieId)
		.then()
			.statusCode(200)
			.body("id", is(2))
			.body("title", equalTo("Venom: Tempo de Carnificina"))
			.body("score", is(3.3))
			.body("count", is(3))
			.body("user.id", hasItems(3, 4, 5))
			.body("user.name", hasItems("João", "Ana", "Lucia"));
	}
	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() {	
		nonExistingMovieId = 100L;
		
		given()
			.get("/movies/{id}", nonExistingMovieId)
		.then()
			.statusCode(404)
			.body("error", equalTo("Not Found"))
			.body("status", equalTo(404));
	}
	
	@Test
	public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndBlankTitle() throws JSONException {		
	}
	
	@Test
	public void insertShouldReturnForbiddenWhenClientLogged() throws Exception {
	}
	
	@Test
	public void insertShouldReturnUnauthorizedWhenInvalidToken() throws Exception {
	}
}
