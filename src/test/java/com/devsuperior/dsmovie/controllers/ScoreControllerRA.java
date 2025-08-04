package com.devsuperior.dsmovie.controllers;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.devsuperior.dsmovie.tests.TokenUtil;

import io.restassured.http.ContentType;

public class ScoreControllerRA {

	private String clientUsername, clientPassword;
    private String clientToken;

    private Long nonExistingMovieId;
    private JSONObject validScore, missingMovieIdScore, negativeScore;

    @BeforeEach
    void setUp() throws Exception {
        baseURI = "http://localhost:8080";

        clientUsername = "maria@gmail.com";
        clientPassword = "123456";

        clientToken = TokenUtil.obtainAccessToken(clientUsername, clientPassword);

        nonExistingMovieId = 999L;

        validScore = new JSONObject();
        validScore.put("movieId", 1);
        validScore.put("score", 4.5);

        negativeScore = new JSONObject();
        negativeScore.put("movieId", 1);
        negativeScore.put("score", -1.0);
	}
	
	@Test
	public void saveScoreShouldReturnNotFoundWhenMovieIdDoesNotExist() throws Exception {	
		Map<String, Object> score = new HashMap<>();
        score.put("movieId", nonExistingMovieId);
        score.put("score", 4.5);

        given()
            .header("Authorization", "Bearer " + clientToken)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body(score)
        .when()
            .put("/scores")
        .then()
            .statusCode(404)
            .body("status", equalTo(404))
            .body("error", equalTo("Recurso não encontrado"));	
	}
	
	@Test
	public void saveScoreShouldReturnUnprocessableEntityWhenMissingMovieId() throws Exception {
		Map<String, Object> missingMovieIdScore = new HashMap<>();
    	missingMovieIdScore.put("score", 3.0);
		
		given()
    		.header("Authorization", "Bearer " + clientToken)
    		.contentType(ContentType.JSON)
    		.accept(ContentType.JSON)
    		.body(missingMovieIdScore)
		.when()
    		.put("/scores")
		.then()
    		.statusCode(422)
    		.body("errors.message[0]", equalTo("Campo requerido"));
	}
	
	@Test
	public void saveScoreShouldReturnUnprocessableEntityWhenScoreIsLessThanZero() throws Exception {		
		Map<String, Object> negativeScore = new HashMap<>();
    	negativeScore.put("movieId", 1L);
   		negativeScore.put("score", -1.0);

		given()
            .header("Authorization", "Bearer " + clientToken)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body(negativeScore)
        .when()
            .put("/scores")
        .then()
            .statusCode(422)
            .body("errors.message[0]", equalTo("Valor mínimo 0"));
	}
}
