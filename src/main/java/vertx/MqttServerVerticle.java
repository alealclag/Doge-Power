package vertx;

import java.util.ArrayList;
import java.util.List;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.SetMultimap;

import io.netty.handler.codec.mqtt.MqttConnectReturnCode;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.ClientAuth;
import io.vertx.mqtt.MqttEndpoint;
import io.vertx.mqtt.MqttServer;
import io.vertx.mqtt.MqttServerOptions;
import io.vertx.mqtt.MqttTopicSubscription;
import io.vertx.mqtt.messages.MqttPublishMessage;

public class MqttServerVerticle extends AbstractVerticle {

	public static final String TOPIC_LED = "led";
	public static final String TOPIC_VIBR = "vibration";
	
	public static final SetMultimap<String, MqttEndpoint> clients = LinkedHashMultimap.create();
	
	public void start(Promise<Void> promise) {
		MqttServerOptions options = new MqttServerOptions();
		options.setClientAuth(ClientAuth.REQUIRED);
		options.setPort(1885);
		MqttServer mqttServer = MqttServer.create(vertx, options);
		init(mqttServer);
	}

	private static void init(MqttServer mqttServer) {
		
		mqttServer.endpointHandler(endpoint -> {
			System.out.println("MQTT client [" + endpoint.clientIdentifier() + "] request to connect, clean session = "
					+ endpoint.isCleanSession());
			endpoint.accept(false);
			if (endpoint.auth().getPassword().contentEquals("mqttbrokerpass") && endpoint.auth().getUsername().contentEquals("mqttbroker")) {
				handleSubscription(endpoint);
				handleUnsubscription(endpoint);
				publishHandler(endpoint);
				handleClientDisconnect(endpoint);	
			}else {
				endpoint.reject(MqttConnectReturnCode.CONNECTION_REFUSED_BAD_USER_NAME_OR_PASSWORD);
			}
		}).listen(ar -> {
			if (ar.succeeded()) {
				System.out.println("MQTT server is listening on port " + ar.result().actualPort());
			} else {
				System.out.println("Error on starting the server");
				ar.cause().printStackTrace();
			}
		});
	}

	private static void handleSubscription(MqttEndpoint endpoint) {
		endpoint.subscribeHandler(subscribe -> {
			List<MqttQoS> grantedQosLevels = new ArrayList<>();
			for (MqttTopicSubscription s : subscribe.topicSubscriptions()) {
				System.out.println("Subscription for " + s.topicName() + " with QoS " + s.qualityOfService());
				grantedQosLevels.add(s.qualityOfService());
				clients.put(s.topicName(), endpoint);
			}
			endpoint.subscribeAcknowledge(subscribe.messageId(), grantedQosLevels);
		});
	}

	private static void handleUnsubscription(MqttEndpoint endpoint) {
        endpoint.unsubscribeHandler(unsubscribe -> {
            for (String t: unsubscribe.topics()) {
                System.out.println("Unsubscription for " + t);
                clients.remove(t, endpoint);
            }
            endpoint.unsubscribeAcknowledge(unsubscribe.messageId());
        });
    }

	private static void publishHandler(MqttEndpoint endpoint) {
		endpoint.publishHandler(message -> {
			handleQoS(message, endpoint);
		}).publishReleaseHandler(messageId -> {
			endpoint.publishComplete(messageId);
		});
	}

	private static void handleQoS(MqttPublishMessage message, MqttEndpoint endpoint) {
		if (message.qosLevel() == MqttQoS.AT_LEAST_ONCE) {
			String topicName = message.topicName();
			switch (topicName) {
			case TOPIC_LED:
				System.out.println("Luces published");
				break;
			case TOPIC_VIBR:
				System.out.println("Vibration published");
				break;
			}
			for (MqttEndpoint subscribed: clients.get(message.topicName())) {
				subscribed.publish(message.topicName(), message.payload(), message.qosLevel(), message.isDup(), message.isRetain());
			}
			endpoint.publishAcknowledge(message.messageId());
		} else if (message.qosLevel() == MqttQoS.EXACTLY_ONCE) {
			endpoint.publishRelease(message.messageId());
		}
	}

	private static void handleClientDisconnect(MqttEndpoint endpoint) {
		endpoint.disconnectHandler(h -> {
			System.out.println("The remote client has closed the connection.");
		});
	}

}
