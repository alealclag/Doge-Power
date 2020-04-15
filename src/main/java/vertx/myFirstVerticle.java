package vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
public class myFirstVerticle extends AbstractVerticle{
	
	@Override
	public void start(Future<Void> startFuture) {
		vertx.createHttpServer().requestHandler(
				request ->{
					request.response().end();
		}).listen(8081, result->{
			if(result.succeeded()) {
				System.out.println("Todo correcto");
			}else {
				System.out.println(result.cause());
			}
		});
		
		vertx.deployVerticle(DatabaseVerticle.class.getName());
		/*
		vertx.deployVerticle(MySecondVerticle.class.getName());
		vertx.deployVerticle(MyThirdVerticle.class.getName());
		
		EventBus eventBus = vertx.eventBus();
		vertx.setPeriodic(4000, action -> {
			eventBus.request("mensaje_p2p", "Hola mundo", reply -> {
				if(reply.succeeded()) {
					String replyMessage = (String) reply.result().body();
					System.out.println("Respuesta: " + replyMessage);
				}else {
					System.out.println("No ha habido respuesta");
				}
			});
		});
		vertx.setPeriodic(4000, action -> {
			eventBus.publish("mensaje_broadcast", "Eyyyyy que pasa gente");
		});
		*/
	}
}