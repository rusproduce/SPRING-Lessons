package Loggers;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

public class App {
    private Map<EventType, EventLogger> loggers;
    private Client client;
    private EventLogger defaultLogger;

    public App(Map<EventType, EventLogger> loggers, Client client, EventLogger defaultLogger) {
        this.loggers = loggers;
        this.client = client;
        this.defaultLogger = defaultLogger;
    }

    public static void main(String[] args) {
//        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("Spring.xml");
//        App app = (App) ctx.getBean("app");
//        Event event = ctx.getBean(Event.class);
//
//        app.logEvent(null, event, "So...");
//        app.logEvent(null, ctx.getBean(Event.class), "I made you up, bro, that's why you so awesome!");
//        app.logEvent(EventType.ERROR, ctx.getBean(Event.class), "Hi 1!");
//        app.logEvent(EventType.INFO, ctx.getBean(Event.class), "I made you up!");
//        ctx.close();

        AnnotationConfigApplicationContext ctxAnnotation = new AnnotationConfigApplicationContext(AppConfig.class);
        App app = (App) ctxAnnotation.getBean("app");

        app.logEvent(null, ctxAnnotation.getBean(Event.class), "So...");
        app.logEvent(null, ctxAnnotation.getBean(Event.class), "I made you up, bro, that's why you so awesome!");
//        app.logEvent(null, ctxAnnotation.getBean(Event.class), "I made you up, bro, that's why you so awesome!");
//        app.logEvent(null, ctxAnnotation.getBean(Event.class), "I made you up, bro, that's why you so awesome!");
//        app.logEvent(null, ctxAnnotation.getBean(Event.class), "I made you up, bro, that's why you so awesome!");
//        app.logEvent(null, ctxAnnotation.getBean(Event.class), "I made you up, bro, that's why you so awesome!");
        app.logEvent(EventType.ERROR, ctxAnnotation.getBean(Event.class), "Hi 1!");
        app.logEvent(EventType.INFO, ctxAnnotation.getBean(Event.class), "I made you up!");
        ctxAnnotation.close();
    }

    private void logEvent(EventType eventType, Event event, String msg) {
        String massege = msg.replaceAll(client.getId(), client.getFullName());
        event.setMsg(massege);
        EventLogger logger = loggers.get(eventType);
        if (logger == null) {
            logger = defaultLogger;
        }
        logger.logEvent(event);
    }
}
