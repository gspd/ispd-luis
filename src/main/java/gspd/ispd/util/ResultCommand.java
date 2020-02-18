package gspd.ispd.util;

public interface ResultCommand<T> extends Command {

    T getResult();
}
