package gspd.ispd.queues;

import java.util.ArrayList;
import java.util.List;

public class QueueSystem {
    private List<ServiceCentre> serviceCentres;

    public QueueSystem() {
        serviceCentres = new ArrayList<>();
    }

    public void add(ServiceCentre serviceCentre) {
        serviceCentres.add(serviceCentre);
    }

    public boolean remove(ServiceCentre serviceCentre) {
        return serviceCentres.remove(serviceCentre);
    }

    public List<ServiceCentre> getServiceCentres() {
        return new ArrayList<>(serviceCentres);
    }

    public void connect(ServiceCentre source, ServiceCentre destination) {
        source.getOutput().add(destination);
    }

    public void disconnect(ServiceCentre output, ServiceCentre from) {
        from.getOutput().remove(output);
    }
}
