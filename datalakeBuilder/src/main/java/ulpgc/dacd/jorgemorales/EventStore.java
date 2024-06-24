package ulpgc.dacd.jorgemorales;

import java.io.IOException;

public interface EventStore {
    void saveEvent(String eventJson) throws IOException;
}
