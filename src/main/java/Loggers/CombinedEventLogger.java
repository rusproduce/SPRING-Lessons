package Loggers;

import java.util.List;

public class CombinedEventLogger implements EventLogger {
    private final List<EventLogger> loggers;

    public CombinedEventLogger(List<EventLogger> loggers) {
    this.loggers = loggers;
    }

    @Override
    public void logEvent(Event event) {
//        loggers.forEach(e->e.logEvent(event));
        for (EventLogger e: loggers){
            e.logEvent(event);  //как написать лямдой?
        }
    }
}
