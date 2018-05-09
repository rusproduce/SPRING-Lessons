import java.util.ArrayList;
import java.util.List;

public class CacheFileEventLogger extends FileEventLogger {
    private int cacheSize;
    private List<Event> cache;

    public CacheFileEventLogger(String filename, int cachesize) {
        super(filename);
        this.cacheSize = cachesize;
        this.cache = new ArrayList<Event>(cachesize);
    }
    @Override
    public void logEvent(Event event){
        cache.add(event);
        if (cache.size() >= cacheSize){
            writeEventsFromCache();
            cache.clear();
        }
    }

    private void writeEventsFromCache() {
        cache.stream().forEach(super::logEvent);  //это тоже самое?
//        cache.stream().forEach(e->super.logEvent(e));
//        for (Event e : cache) {
//            super.logEvent(e);
//        }
    }

    public void destroy(){
        if (!cache.isEmpty()){
            writeEventsFromCache();
        }
    }
}
