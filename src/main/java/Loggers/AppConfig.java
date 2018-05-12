package Loggers;

import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

import java.util.*;

@Configuration
//@PropertySource("classpath:client.properties")
public class AppConfig {
    @Bean
    public Client client() {
        return new Client("1", "Smith");
    }

    @Bean
    public ConsoleEventLogger consoleEventLogger() {
        return new ConsoleEventLogger();
    }

    @Bean(destroyMethod = "destroy")
    public CacheFileEventLogger cacheFileEventLogger() {
        return new CacheFileEventLogger("target/events_log.txt", 5);
    }

    @Bean(initMethod = "init") //Падает ошибка, если кэш забит и неработает дестрой метод без Autowired, почему?
                               //Ща все работает, но инит 3 раза выполняется, с какого?
//    @Bean
    public FileEventLogger fileEventLogger() {
        return new FileEventLogger("target/events_log.txt");
    }

    @Bean
    public CombinedEventLogger combinedEventLogger() {
        List<EventLogger> list = new ArrayList<>(2);
        list.add(consoleEventLogger());
        list.add(fileEventLogger());
        return new CombinedEventLogger(list);
    }

    @Bean
    public Map<EventType, EventLogger> loggerMap() {
        Map<EventType, EventLogger> loggerMap = new HashMap<>(2);
        loggerMap.put(EventType.INFO, consoleEventLogger());
        loggerMap.put(EventType.ERROR, combinedEventLogger());
        return loggerMap;
    }

    @Bean
    public App app() {
        return new App(loggerMap(), client(), cacheFileEventLogger());
    }

    @Bean
    public java.text.DateFormat dataFormat() {
        return java.text.DateFormat.getDateTimeInstance();
    }

    @Bean
    @Scope("prototype")
    public Event event() {
        return new Event(new Date(), dataFormat());
    }


}
