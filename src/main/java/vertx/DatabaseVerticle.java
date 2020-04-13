package vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.*;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.*;
import types.*;

public class DatabaseVerticle extends AbstractVerticle{
	
	private MySQLPool mySQLPool;
	
	@Override
	public void start(Promise<Void> startPromise) {
		MySQLConnectOptions mySQLConnectOptions = new MySQLConnectOptions().setPort(3306).setHost("localhost")
				.setDatabase("doge power").setUser("root").setPassword("Kike");
		PoolOptions poolOptions = new PoolOptions().setMaxSize(5);
		mySQLPool = MySQLPool.pool(vertx, mySQLConnectOptions, poolOptions);
		
		Router router = Router.router(vertx);
		router.route().handler(BodyHandler.create());
		vertx.createHttpServer().requestHandler(router::handle).listen(8080, result -> {
			if (result.succeeded()) {
				startPromise.complete();
			}else {
				startPromise.fail(result.cause());
			}
		});

		router.get("/api/user/:idUser").handler(this::getUserInfo);
		router.get("/api/devicesOf/:idUser").handler(this::getDeviceInfoByUser);
		router.get("/api/device/:idDevice").handler(this::getDeviceInfo);
		
		router.get("/api/sensor/values/:idSensor/:timestamp").handler(this::getSensorValues);
		router.get("/api/sensor/values/:idSensor").handler(this::getSensorValues);
		//router.get("/api/sensorsOf/values/:idDevice").handler(this::getSensorValuesByDevice);
		
		router.get("/api/actuator/values/:idActuator/:timestamp").handler(this::getActuatorValues);
		router.get("/api/actuator/values/:idActuator").handler(this::getActuatorValues);
		
		router.post("/api/user/newUser").handler(this::postUserInfo);
		router.post("/api/device/newDevice/:idUser").handler(this::postDeviceInfo);
		
		router.post("/api/sensor/values/:idSensor").handler(this::postSensorValues);
		router.post("/api/actuator/values/:idSensor").handler(this::postActuatorValues);
		
	
	}

	private void getUserInfo(RoutingContext routingContext) {
		mySQLPool.query("SELECT * FROM user WHERE iduser = " + routingContext.request().getParam("idUser"), 
				res -> {
					if (res.succeeded()) {	
						RowSet<Row> resultSet = res.result();
						System.out.println("El número de elementos obtenidos es " + resultSet.size());
						JsonArray result = new JsonArray();
						
						for (Row row : resultSet) {
							result.add(JsonObject.mapFrom(new User(row.getInteger("iduser"),
									row.getString("name"),
									row.getString("password"),
									row.getLong("birthdate"),
									row.getString("city"))));
							
						}
						routingContext.response().setStatusCode(200).putHeader("content-type", "application/json")
						.end(result.encodePrettily());
						}else {
							routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
							.end((JsonObject.mapFrom(res.cause()).encodePrettily()));
					}
				});
	}
	
	private void getDeviceInfo(RoutingContext routingContext) {
		mySQLPool.query("SELECT * FROM device WHERE iddevice = " + routingContext.request().getParam("idDevice"), 
				res -> {
					if (res.succeeded()) {	
						RowSet<Row> resultSet = res.result();
						System.out.println("El número de elementos obtenidos es " + resultSet.size());
						JsonArray result = new JsonArray();
						
						for (Row row : resultSet) {
							result.add(JsonObject.mapFrom(new Device(row.getInteger("iddevice"),
									row.getString("dog"))));
						}
						routingContext.response().setStatusCode(200).putHeader("content-type", "application/json")
						.end(result.encodePrettily());
						}else {
							routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
							.end((JsonObject.mapFrom(res.cause()).encodePrettily()));
					}
				});
	}

	private void getDeviceInfoByUser(RoutingContext routingContext) {
		mySQLPool.query("SELECT * FROM device WHERE iduser = " + routingContext.request().getParam("idUser"), 
				res -> {
					if (res.succeeded()) {	
						RowSet<Row> resultSet = res.result();
						System.out.println("El número de elementos obtenidos es " + resultSet.size());
						JsonArray result = new JsonArray();
						
						for (Row row : resultSet) {
							result.add(JsonObject.mapFrom(new Device(row.getInteger("iddevice"),
									row.getString("dog"))));
						}
						routingContext.response().setStatusCode(200).putHeader("content-type", "application/json")
						.end(result.encodePrettily());
						}else {
							routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
							.end((JsonObject.mapFrom(res.cause()).encodePrettily()));
					}
				});
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
									getLocation(routingContext, -1);break;
								
								case "Pressure":
									getPressure(routingContext, -1);break;
									
								case "Sound":
									getSound(routingContext, -1);break;
									
								case "Distance":
									getDistance(routingContext, -1);break;
							}
						}
					}else{
						routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
							.end((JsonObject.mapFrom(res.cause()).encodePrettily()));
					}
				});
	}

	private void getLocation(RoutingContext routingContext, int idSensor) {
		String query;
		if(routingContext.request().getParam("timestamp")==null) {
			if(idSensor==-1) {
				query="SELECT * FROM sensor_value_location WHERE idsensor = "
						+ routingContext.request().getParam("idSensor");
			}else {
				query="SELECT * FROM sensor_value_location WHERE idsensor = " + idSensor;
			}
			
		}else {
			if(idSensor==-1) {
			query="SELECT * FROM sensor_value_location WHERE timestamp > "
					+ routingContext.request().getParam("timestamp") + " AND idsensor = "
					+ routingContext.request().getParam("idSensor");
			}else {
				query="SELECT * FROM sensor_value_location WHERE timestamp > "
						+ routingContext.request().getParam("timestamp") + " AND idsensor = "
						+ idSensor;
			}
		}
		mySQLPool.query(query, res -> {
				if (res.succeeded()) {
					
					RowSet<Row> resultSet = res.result();
					System.out.println("aEl número de elementos obtenidos es " + resultSet.size());
					JsonArray result = new JsonArray();
					
					for (Row row : resultSet) {
						
						result.add(JsonObject.mapFrom(new Location(row.getInteger("idsensor"),
								row.getFloat("value_x"),
								row.getFloat("value_y"),
								row.getLong("timestamp"))));
					}
					routingContext.response().setStatusCode(200).putHeader("content-type", "application/json")
					.end(result.encodePrettily());
					}else {
						routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
						.end((JsonObject.mapFrom(res.cause()).encodePrettily()));
				}
			});
	}
	
	private void getPressure(RoutingContext routingContext, int idSensor) {
		String query;
		if(routingContext.request().getParam("timestamp")==null) {
			if(idSensor==-1) {
				query="SELECT * FROM sensor_value_basic WHERE idsensor = "
						+ routingContext.request().getParam("idSensor");
			}else {
				query="SELECT * FROM sensor_value_basic WHERE idsensor = " + idSensor;
			}
			
		}else {
			if(idSensor==-1) {
			query="SELECT * FROM sensor_value_basic WHERE timestamp > "
					+ routingContext.request().getParam("timestamp") + " AND idsensor = "
					+ routingContext.request().getParam("idSensor");
			}else {
				query="SELECT * FROM sensor_value_basic WHERE timestamp > "
						+ routingContext.request().getParam("timestamp") + " AND idsensor = "
						+ idSensor;
			}
		}
		mySQLPool.query(query, res -> {
				if (res.succeeded()) {
							
					RowSet<Row> resultSet = res.result();
					System.out.println("bEl número de elementos obtenidos es " + resultSet.size());
					JsonArray result = new JsonArray();
							
					for (Row row : resultSet) {
								
						result.add(JsonObject.mapFrom(new Pressure(row.getInteger("idsensor"),
								row.getFloat("value"),
								row.getLong("timestamp"))));
					}
					routingContext.response().setStatusCode(200).putHeader("content-type", "application/json")
						.end(result.encodePrettily());
				}else {
					routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
						.end((JsonObject.mapFrom(res.cause()).encodePrettily()));
				}
			});
	}
	
	private void getSound(RoutingContext routingContext, int idSensor) {
		String query;
		if(routingContext.request().getParam("timestamp")==null) {
			if(idSensor==-1) {
				query="SELECT * FROM sensor_value_basic WHERE idsensor = "
						+ routingContext.request().getParam("idSensor");
			}else {
				query="SELECT * FROM sensor_value_basic WHERE idsensor = " + idSensor;
			}
			
		}else {
			if(idSensor==-1) {
			query="SELECT * FROM sensor_value_basic WHERE timestamp > "
					+ routingContext.request().getParam("timestamp") + " AND idsensor = "
					+ routingContext.request().getParam("idSensor");
			}else {
				query="SELECT * FROM sensor_value_basic WHERE timestamp > "
						+ routingContext.request().getParam("timestamp") + " AND idsensor = "
						+ idSensor;
			}
		}
		mySQLPool.query(query, res -> {
				if (res.succeeded()) {

					RowSet<Row> resultSet = res.result();
					System.out.println("cEl número de elementos obtenidos es " + resultSet.size());
					JsonArray result = new JsonArray();

					for (Row row : resultSet) {

						result.add(JsonObject.mapFrom(new Sound(row.getInteger("idsensor"),
								row.getFloat("value"),
								row.getLong("timestamp"))));
					}
					routingContext.response().setStatusCode(200).putHeader("content-type", "application/json")
					.end(result.encodePrettily());
					}else {
						routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
						.end((JsonObject.mapFrom(res.cause()).encodePrettily()));
				}
			});
	}
	
	private void getDistance(RoutingContext routingContext, int idSensor) {
		String query;
		if(routingContext.request().getParam("timestamp")==null) {
			if(idSensor==-1) {
				query="SELECT * FROM sensor_value_distance WHERE idsensor = "
						+ routingContext.request().getParam("idSensor");
			}else {
				query="SELECT * FROM sensor_value_distance WHERE idsensor = " + idSensor;
			}
			
		}else {
			if(idSensor==-1) {
			query="SELECT * FROM sensor_value_distance WHERE timestamp > "
					+ routingContext.request().getParam("timestamp") + " AND idsensor = "
					+ routingContext.request().getParam("idSensor");
			}else {
				query="SELECT * FROM sensor_value_distance WHERE timestamp > "
						+ routingContext.request().getParam("timestamp") + " AND idsensor = "
						+ idSensor;
			}
		}
		mySQLPool.query(query, res -> {
				if (res.succeeded()) {
					
					RowSet<Row> resultSet = res.result();
					System.out.println("dEl número de elementos obtenidos es " + resultSet.size());
					JsonArray result = new JsonArray();
					
					for (Row row : resultSet) {
						
						result.add(JsonObject.mapFrom(new Distance(row.getInteger("idsensor"),
								row.getFloat("distance_to_door"),
								row.getBoolean("isInside"),
								row.getLong("timestamp"))));
					}
					routingContext.response().setStatusCode(200).putHeader("content-type", "application/json")
					.end(result.encodePrettily());
					}else {
						routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
						.end((JsonObject.mapFrom(res.cause()).encodePrettily()));
				}
			});
	}
	
	private void getActuatorValues(RoutingContext routingContext) {

		mySQLPool.query("SELECT * FROM actuator WHERE idactuator = " + routingContext.request().getParam("idActuator"), 
				res -> {
					if (res.succeeded()) {	
						RowSet<Row> resultSet = res.result();
						System.out.println("El número de elementos obtenidos es " + resultSet.size());
						for (Row row : resultSet) {
							switch(row.getString("name")) {
								case "led":
									getLed(routingContext);break;
								
								case "vibration":
									getVibration(routingContext);break;

							}
						}
					}else{
						routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
							.end((JsonObject.mapFrom(res.cause()).encodePrettily()));
					}
				});
	}
		
	private void getLed(RoutingContext routingContext) {
		String query;
		if(routingContext.request().getParam("timestamp")==null) {
			query="SELECT * FROM actuator_value_basic WHERE idactuator = "
					+ routingContext.request().getParam("idActuator");
		}else {
			query="SELECT * FROM actuator_value_basic WHERE timestamp > "
					+ routingContext.request().getParam("timestamp") + " AND idactuator = "
					+ routingContext.request().getParam("idActuator");
		}
		mySQLPool.query(query, res -> {
			if (res.succeeded()) {
				
				RowSet<Row> resultSet = res.result();
				System.out.println("El número de elementos obtenidos es " + resultSet.size());
				JsonArray result = new JsonArray();
				
				for (Row row : resultSet) {	
					result.add(JsonObject.mapFrom(new Led(row.getInteger("idactuator"),
							row.getInteger("mode"),
							row.getFloat("value"),
							row.getFloat("length"),
							row.getLong("timestamp"))));
				}
				routingContext.response().setStatusCode(200).putHeader("content-type", "application/json")
				.end(result.encodePrettily());
				}else {
					routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
					.end((JsonObject.mapFrom(res.cause()).encodePrettily()));
			}
		});
	}
	
	private void getVibration(RoutingContext routingContext) {
		String query;
		if(routingContext.request().getParam("timestamp")==null) {
			query="SELECT * FROM actuator_value_basic WHERE idactuator = "
					+ routingContext.request().getParam("idActuator");
		}else {
			query="SELECT * FROM actuator_value_basic WHERE timestamp > "
					+ routingContext.request().getParam("timestamp") + " AND idactuator = "
					+ routingContext.request().getParam("idActuator");
		}
		mySQLPool.query(query, res -> {
				if (res.succeeded()) {
					
					RowSet<Row> resultSet = res.result();
					System.out.println("El número de elementos obtenidos es " + resultSet.size());
					JsonArray result = new JsonArray();
					
					for (Row row : resultSet) {
						
						result.add(JsonObject.mapFrom(new Vibration(row.getInteger("idactuator"),
								row.getInteger("mode"),
								row.getFloat("value"),
								row.getFloat("length"),
								row.getLong("timestamp"))));
					}
					routingContext.response().setStatusCode(200).putHeader("content-type", "application/json")
					.end(result.encodePrettily());
					}else {
						routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
						.end((JsonObject.mapFrom(res.cause()).encodePrettily()));
				}
			});
	}
	
	/*
	private void getSensorValuesByDevice(RoutingContext routingContext) {
		mySQLPool.query("SELECT * FROM sensor WHERE iddevice = " + routingContext.request().getParam("idDevice"), 
				res -> {
					if (res.succeeded()) {	
						RowSet<Row> resultSet = res.result();
						System.out.println("El número de elementos obtenidos es " + resultSet.size());
						for (Row row : resultSet) {
							
							switch(row.getString("name")) {
								case "Location":
									getLocation(routingContext, row.getInteger("idsensor"));break;
								
								case "Pressure":
									getPressure(routingContext, row.getInteger("idsensor"));break;
									
								case "Sound":
									getSound(routingContext, row.getInteger("idsensor"));break;
									
								case "Distance":
									getDistance(routingContext, row.getInteger("idsensor"));break;
							}
						}
					}else{
						routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
							.end((JsonObject.mapFrom(res.cause()).encodePrettily()));
					}
				});
	}
*/	
	
	
	private void postUserInfo(RoutingContext routingContext) {
		User user = Json.decodeValue(routingContext.getBodyAsString(), User.class);	
		
		mySQLPool.preparedQuery("INSERT INTO sensor_value_location (name, password, birthdate, City) VALUES (?,?,?,?)",
				Tuple.of(user.getName(), user.getPassword(), user.getCity(), user.getCity()),
				handler -> {
					
					if (handler.succeeded()) {
						System.out.println(handler.result().rowCount());
						
						routingContext.response().setStatusCode(200).putHeader("content-type", "application/json")
								.end(JsonObject.mapFrom(user).encodePrettily());
						
						}else {
							routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
							.end((JsonObject.mapFrom(handler.cause()).encodePrettily()));
					}
				});
	}
	private void postDeviceInfo(RoutingContext routingContext) {
		Device device = Json.decodeValue(routingContext.getBodyAsString(), Device.class);	
		
		mySQLPool.preparedQuery("INSERT INTO device (dog, iduser) VALUES (?,?)",
				Tuple.of(device.getDog(), routingContext.request().getParam("idUser")),
				handler -> {
					
					if (handler.succeeded()) {
						System.out.println(handler.result().rowCount());
						
						routingContext.response().setStatusCode(200).putHeader("content-type", "application/json")
								.end(JsonObject.mapFrom(device).encodePrettily());
						
						}else {
							routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
							.end((JsonObject.mapFrom(handler.cause()).encodePrettily()));
					}
				});
	}
	
	private void postSensorValues(RoutingContext routingContext) {                                                  //inserta en la base de datos los datos de un unico sensor elegido en la URL 
 
		mySQLPool.query("SELECT * FROM sensor WHERE idsensor = " + routingContext.request().getParam("idSensor"), 
				resAux -> {
					
					if (resAux.succeeded()) {
						RowSet<Row> resultSet = resAux.result();
						for (Row row : resultSet) {

							switch(row.getString("name")) {
							
							case "Location":
								postLocation(routingContext);break;
								
							case "Pressure":
								postPressure(routingContext);break;
								
							case "Sound":
								postSound(routingContext);break;
							
							case "Distance":
								postDistance(routingContext);break;
								
							}
							
						}
						
						
					}else {
						System.out.println("you're into resAux not succeeded");
						routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
							.end((JsonObject.mapFrom(resAux.cause()).encodePrettily()));
					}
				});
		
	}
	private void postLocation(RoutingContext routingContext) {
		Location location = Json.decodeValue(routingContext.getBodyAsString(), Location.class);	
		
		mySQLPool.preparedQuery("INSERT INTO sensor_value_location (value_x, value_y, timestamp, idsensor) VALUES (?,?,?,?)",
				Tuple.of(location.getX(), location.getY(), location.getTimestamp(),
						routingContext.request().getParam("idSensor")),
				handler -> {
					
					if (handler.succeeded()) {
						System.out.println(handler.result().rowCount());
						
						routingContext.response().setStatusCode(200).putHeader("content-type", "application/json")
								.end(JsonObject.mapFrom(location).encodePrettily());
						
						}else {
							routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
							.end((JsonObject.mapFrom(handler.cause()).encodePrettily()));
					}
				});
	}
	private void postPressure(RoutingContext routingContext) {
		Pressure pressure = Json.decodeValue(routingContext.getBodyAsString(), Pressure.class);	
		
		mySQLPool.preparedQuery("INSERT INTO sensor_value_basic (value, timestamp, idsensor) VALUES (?,?,?)",
				Tuple.of(pressure.getValue(), pressure.getTimestamp(),
						routingContext.request().getParam("idSensor")),
				handler -> {
					
					if (handler.succeeded()) {
						System.out.println(handler.result().rowCount());
						
						routingContext.response().setStatusCode(200).putHeader("content-type", "application/json")
								.end(JsonObject.mapFrom(pressure).encodePrettily());
						
						}else {
							routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
							.end((JsonObject.mapFrom(handler.cause()).encodePrettily()));
					}
				});
	}
	private void postSound(RoutingContext routingContext) {
		Sound sound = Json.decodeValue(routingContext.getBodyAsString(), Sound.class);	
		
		mySQLPool.preparedQuery("INSERT INTO sensor_value_basic (value, timestamp, idsensor) VALUES (?,?,?)",
				Tuple.of(sound.getDecibels(), sound.getTimestamp(),
						routingContext.request().getParam("idSensor")),
				handler -> {
					
					if (handler.succeeded()) {
						System.out.println(handler.result().rowCount());
						
						routingContext.response().setStatusCode(200).putHeader("content-type", "application/json")
								.end(JsonObject.mapFrom(sound).encodePrettily());
						
						}else {
							routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
							.end((JsonObject.mapFrom(handler.cause()).encodePrettily()));
					}
				});
	}
	private void postDistance(RoutingContext routingContext) {
		Distance distance = Json.decodeValue(routingContext.getBodyAsString(), Distance.class);	
		
		mySQLPool.preparedQuery("INSERT INTO sensor_value_distance (distance_to_door, is_inside, timestamp, idsensor) VALUES (?,?,?,?)",
				Tuple.of(distance.getDistance_to_door(), distance.getIs_inside(), distance.getTimestamp(),
						routingContext.request().getParam("idSensor")),
				handler -> {
					
					if (handler.succeeded()) {
						System.out.println(handler.result().rowCount());
						
						routingContext.response().setStatusCode(200).putHeader("content-type", "application/json")
								.end(JsonObject.mapFrom(distance).encodePrettily());
						
						}else {
							routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
							.end((JsonObject.mapFrom(handler.cause()).encodePrettily()));
					}
				});
	}

	private void postActuatorValues(RoutingContext routingContext) {                                                 //inserta en la base de datos los datos de un unico actuador elegido en la URL

		mySQLPool.query("SELECT * FROM actuator WHERE idactuator = " + routingContext.request().getParam("idActuator"), 
				res -> {
					if (res.succeeded()) {	
						
						RowSet<Row> resultSet = res.result();
						for (Row row : resultSet) {
							
							switch(row.getString("name")) {
							
								case "led":
									postLed(routingContext);break;
								
								case "vibration":
									postVibration(routingContext);break;

							}
						}
						
					}else{
						routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
							.end((JsonObject.mapFrom(res.cause()).encodePrettily()));
					}
				});
	}
	private void postLed(RoutingContext routingContext){
		Led led = Json.decodeValue(routingContext.getBodyAsString(), Led.class);	
		
		mySQLPool.preparedQuery("INSERT INTO sensor_value_distance (value, timestamp, idactuator, length, mode) VALUES (?,?,?,?,?)",
				Tuple.of(led.getLuminosity(), led.getTimestamp(), led.getTimestamp(),
						routingContext.request().getParam("idSensor"), led.getLength(), led.getMode()),
				handler -> {
					
					if (handler.succeeded()) {
						System.out.println(handler.result().rowCount());
						
						routingContext.response().setStatusCode(200).putHeader("content-type", "application/json")
								.end(JsonObject.mapFrom(led).encodePrettily());
						
						}else {
							routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
							.end((JsonObject.mapFrom(handler.cause()).encodePrettily()));
					}
				});
		
	}
	private void postVibration(RoutingContext routingContext){
		Vibration vibration = Json.decodeValue(routingContext.getBodyAsString(), Vibration.class);	
		
		mySQLPool.preparedQuery("INSERT INTO sensor_value_distance (value, timestamp, idactuator, length, mode) VALUES (?,?,?,?,?)",
				Tuple.of(vibration.getIntensity(), vibration.getTimestamp(), vibration.getTimestamp(),
						routingContext.request().getParam("idSensor"), vibration.getLength(), vibration.getMode()),
				handler -> {
					
					if (handler.succeeded()) {
						System.out.println(handler.result().rowCount());
						
						routingContext.response().setStatusCode(200).putHeader("content-type", "application/json")
								.end(JsonObject.mapFrom(vibration).encodePrettily());
						
						}else {
							routingContext.response().setStatusCode(401).putHeader("content-type", "application/json")
							.end((JsonObject.mapFrom(handler.cause()).encodePrettily()));
					}
				});
	}



}



