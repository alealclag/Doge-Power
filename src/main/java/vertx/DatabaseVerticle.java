package vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import types.*;

public class DatabaseVerticle extends AbstractVerticle{
	
	private MySQLPool mySQLPool;
	
	@Override
	public void start(Promise<Void> startPromise) {
		MySQLConnectOptions mySQLConnectOptions = new MySQLConnectOptions().setPort(3306).setHost("localhost")
				.setDatabase("doge power").setUser("root").setPassword("7527");
		PoolOptions poolOptions = new PoolOptions().setMaxSize(5);
		mySQLPool = MySQLPool.pool(vertx, mySQLConnectOptions, poolOptions);
		
		Router router = Router.router(vertx);
		vertx.createHttpServer().requestHandler(router::handle).listen(8080, result -> {
			if (result.succeeded()) {
				startPromise.complete();
			}else {
				startPromise.fail(result.cause());
			}
		});

		router.get("/api/sensor/values/:idSensor").handler(this::getSensorValues);


		
	}

	private void getSensorValues(RoutingContext routingContext) {

		mySQLPool.query("SELECT * FROM sensor WHERE idsensor = " + routingContext.request().getParam("idSensor"), 
				res -> {
					if (res.succeeded()) {
						
						RowSet<Row> resultSet = res.result();
						System.out.println("El número de elementos obtenidos es " + resultSet.size());
						for (Row row : resultSet) {
							switch(row.getString("name")) {
							case "Location":
								mySQLPool.query("SELECT * FROM sensor_value_location WHERE idsensor = " + routingContext.request().getParam("idSensor"), 
										resAux -> {
											
											if (resAux.succeeded()) {
												
												RowSet<Row> resultSetAux = resAux.result();
												System.out.println("El número de elementos obtenidos es " + resultSetAux.size());
												JsonArray resultAux = new JsonArray();
												
												for (Row rowAux : resultSetAux) {
													
													resultAux.add(JsonObject.mapFrom(new Location(rowAux.getInteger("idsensor"),
															rowAux.getFloat("value_x"),
															rowAux.getFloat("value_y"),
															rowAux.getLong("timestamp"))));
												}
												routingContext.response().setStatusCode(200).putHeader("content-type", "application/json")
												.end(resultAux.encodePrettily());
												}else {
													routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
													.end((JsonObject.mapFrom(resAux.cause()).encodePrettily()));
											}
										});break;
								
							case "Pressure":
								mySQLPool.query("SELECT * FROM sensor_value_basic WHERE idsensor = " + routingContext.request().getParam("idSensor"), 
										resAux -> {
											
											if (resAux.succeeded()) {
												
												RowSet<Row> resultSetAux = resAux.result();
												System.out.println("El número de elementos obtenidos es " + resultSetAux.size());
												JsonArray resultAux = new JsonArray();
												
												for (Row rowAux : resultSetAux) {	
													resultAux.add(JsonObject.mapFrom(new Pressure(rowAux.getInteger("idsensor"),
															rowAux.getFloat("value"),
															rowAux.getLong("timestamp"))));
												}
												routingContext.response().setStatusCode(200).putHeader("content-type", "application/json")
												.end(resultAux.encodePrettily());
												}else {
													routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
													.end((JsonObject.mapFrom(resAux.cause()).encodePrettily()));
											}
										});break;
							case "Sound":
								mySQLPool.query("SELECT * FROM sensor_value_basic WHERE idsensor = " + routingContext.request().getParam("idSensor"), 
										resAux -> {
											
											if (resAux.succeeded()) {
												
												RowSet<Row> resultSetAux = resAux.result();
												System.out.println("El número de elementos obtenidos es " + resultSetAux.size());
												JsonArray resultAux = new JsonArray();
												
												for (Row rowAux : resultSetAux) {
													resultAux.add(JsonObject.mapFrom(new Sound(rowAux.getInteger("idsensor"),
															rowAux.getFloat("value"),
															rowAux.getLong("timestamp"))));
												}
												routingContext.response().setStatusCode(200).putHeader("content-type", "application/json")
												.end(resultAux.encodePrettily());
												}else {
													routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
													.end((JsonObject.mapFrom(resAux.cause()).encodePrettily()));
											}
										});break;
							case "Distance":
								mySQLPool.query("SELECT * FROM sensor_value_distance WHERE idsensor = " + routingContext.request().getParam("idSensor"), 
										resAux -> {
											
											if (resAux.succeeded()) {
												
												RowSet<Row> resultSetAux = resAux.result();
												System.out.println("El número de elementos obtenidos es " + resultSetAux.size());
												JsonArray resultAux = new JsonArray();
												
												for (Row rowAux : resultSetAux) {
													resultAux.add(JsonObject.mapFrom(new Distance(rowAux.getInteger("idsensor"),
															rowAux.getFloat("distance_to_door"),
															rowAux.getBoolean("isInside"),
															rowAux.getLong("timestamp"))));
												}
												routingContext.response().setStatusCode(200).putHeader("content-type", "application/json")
												.end(resultAux.encodePrettily());
												}else {
													routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
													.end((JsonObject.mapFrom(resAux.cause()).encodePrettily()));
											}
										});break;
							}
							
						}
						
						
					}else {
						routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
							.end((JsonObject.mapFrom(res.cause()).encodePrettily()));
					}
				});
	}
	
	
}
