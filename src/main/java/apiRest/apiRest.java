package apiRest;
import java.util.LinkedHashMap;
import java.util.Map;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import types.Led;
//eclipse ya funciona
//de hecho esta es la segunda prueba que hago
public class apiRest extends AbstractVerticle {

	private Map<Integer, Led> elements = new LinkedHashMap<>();

	@SuppressWarnings("deprecation")
	@Override
	public void start(Future<Void> startFuture) {
		createSomeData();
		Router router = Router.router(vertx);
		vertx.createHttpServer().requestHandler(router::accept).listen(8080, result -> {
			if (result.succeeded()) {
				startFuture.complete();
			} else {
				startFuture.fail(result.cause());
			}
		});
		router.route("/api/elements*").handler(BodyHandler.create());
		router.get("/api/elements").handler(this::getAll);
		router.put("/api/elements").handler(this::addOne);
		router.delete("/api/elements").handler(this::deleteOne);
		router.post("/api/elements/:elementid").handler(this::postOne);
	}

	private void createSomeData() {
		Led light1 = new Led(0, 0, 0);//foo
		elements.put(light1.getId(), light1);
		Led light2 = new Led(1, 50, 0);
		elements.put(light2.getId(), light2);
		Led tv1 = new Led(2, 30, 1000);
		elements.put(tv1.getId(), tv1);
	}

	private void getAll(RoutingContext routingContext) {
		routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
				.end(Json.encodePrettily(elements.values()));
	}

	private void addOne(RoutingContext routingContext) {
		final Led element = Json.decodeValue(routingContext.getBodyAsString(), Led.class);
		elements.put(element.getId(), element);
		routingContext.response().setStatusCode(201).putHeader("content-type", "application/json; charset=utf-8")
				.end(Json.encodePrettily(element));
	}
	
	private void postOne(RoutingContext routingContext) {
		int id = Integer.parseInt(routingContext.request().getParam("elementid"));
		Led ds = elements.get(id);
		final Led element = Json.decodeValue(routingContext.getBodyAsString(), Led.class);
		ds.setMode(element.getMode());
		ds.setLuminosity(element.getLuminosity());
		ds.setLength(element.getLength());
		elements.put(ds.getId(), ds);
		routingContext.response().setStatusCode(201).putHeader("content-type", "application/json; charset=utf-8")
				.end(Json.encode(element));
	}

	private void deleteOne(RoutingContext routingContext) {
		final Led element = Json.decodeValue(routingContext.getBodyAsString(), Led.class);
		elements.remove(element.getId());
		routingContext.response().setStatusCode(201).putHeader("content-type", "application/json; charset=utf-8")
				.end(Json.encodePrettily(element));
	}

	@Override
	public void stop(Future<Void> stopFuture) throws Exception {
		super.stop(stopFuture);
	}

}