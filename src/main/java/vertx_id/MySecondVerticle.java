package vertx_id;

import io.vertx.core.*;

public class MySecondVerticle extends AbstractVerticle{
	@Override
	public void start(Future<Void> startFuture) {
		vertx.eventBus().consumer("mensaje_p2p", message -> {
			String stringmessage = (String) message.body();
			System.out.println("Mensaje recibido por 2: " + stringmessage);
			message.reply("Recibido hefa");
		});
		vertx.eventBus().consumer("mensaje_broadcast", message -> {
			String stringmessage = (String) message.body();
			System.out.println("Mensaje broadcast recibido por 2: " + stringmessage);
		});
	}
}
