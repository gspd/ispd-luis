package gspd.ispd.queues;

public abstract class ServiceCentre<C extends Client> implements ClientInput<C>, ClientOutput<C> {

    private EventObserver<FutureEvent<C>> clientRequestHandler;
    private EventObserver<FutureEvent<C>> clientEnteredHandler;
    private EventObserver<FutureEvent<C>> clientAttendanceHandler;
    private EventObserver<FutureEvent<C>> clientExitedHandler;

    public void onClientRequest(EventObserver<FutureEvent<C>> handler) {
        this.clientRequestHandler = handler;
    }
    public void onClientEntered(EventObserver<FutureEvent<C>> handler) {
        this.clientEnteredHandler = handler;
    }
    public void onClientAttendance(EventObserver<FutureEvent<C>> handler) {
        this.clientAttendanceHandler = handler;
    }
    public void onClientExited(EventObserver<FutureEvent<C>> handler) {
        this.clientExitedHandler = handler;
    }

}
