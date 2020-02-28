package gspd.ispd.util;

public interface CancellableHandler<E> extends Handler<E> {
    void cancel();
}
