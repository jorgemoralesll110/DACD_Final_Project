package ulpgc.dacd.jorgemorales.control;

import java.io.IOException;
import java.util.Map;

public interface EventStore {
    void saveEvent(Map<String, Object> event) throws IOException;
}