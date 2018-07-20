package qmq;

import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

public class Rabbit {
	public static void main(final String... args) throws Exception {
		CachingConnectionFactory fac=new CachingConnectionFactory();
		fac.setAddresses("127.0.0.1");
		fac.setPort(5672);
		fac.setUsername("guest");
		fac.setPassword("guest");
	 ConnectionFactory cf = fac;


		// set up the queue, exchange, binding on the broker
		/*
		RabbitAdmin admin = new RabbitAdmin(cf);
		Queue queue = new Queue("myQueue");
		admin.declareQueue(queue);
		TopicExchange exchange = new TopicExchange("myExchange");
		admin.declareExchange(exchange);
		admin.declareBinding(
			BindingBuilder.bind(queue).to(exchange).with("foo.*"));

		// send something
		RabbitTemplate template = new RabbitTemplate(cf);

		template.convertAndSend("myExchange", "foo.bar", "这是新发的消息2");
		Thread.sleep(1000);
		//container.stop();
*/
		// set up the listener and container
		SimpleMessageListenerContainer container =
				new SimpleMessageListenerContainer(cf);
		//ConsumerListener consumerListener=new ConsumerListener();
		final Jackson2JsonMessageConverter converter=new Jackson2JsonMessageConverter();

		MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageListener(){
			@Override
			public void onMessage(Message var1){
				System.out.println(var1.getBody());
			}

		});
		container.setMessageListener(adapter);
		container.setQueueNames("myQueue");
		container.start();


		
 }
}
