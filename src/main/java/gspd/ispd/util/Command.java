package gspd.ispd.util;

public interface Command<C extends Command<C>> {

     void execute();
}
