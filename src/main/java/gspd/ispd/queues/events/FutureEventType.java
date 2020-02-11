package gspd.ispd.queues.events;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FutureEventType {

    private String name;

    private static final Map<String, FutureEventType> types = new HashMap<>();

    private FutureEventType(String name) {
        this.name = name;
    }

    public static FutureEventType get(String name) {
        if (types.get(name) == null) {
            synchronized (FutureEventType.class) {
                if (types.get(name) == null) {
                    types.put(name, new FutureEventType(name));
                }
            }
        }
        return types.get(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FutureEventType that = (FutureEventType) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

