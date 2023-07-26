package vb.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}
	
	@Test
	public void returnTasks() {
		RestAssured
		.given()
		.when()
			.get("/todo")
		.then()
			.statusCode(200)
			.log().all();
	}
	
	@Test
	public void addTask() {
		RestAssured
		.given()
			.body("{ \"task\": \"Teste post API\", \"dueDate\": \"2027-12-10\" }")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.log().all()
			.statusCode(201);
	}
	
	@Test
	public void invalidDateValidation() {
		RestAssured
		.given()
			.body("{ \"task\": \"Teste post API\", \"dueDate\": \"2020-12-10\" }")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.log().all()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"));
	}
}
