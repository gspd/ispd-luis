package gspd.ispd.motor.workload;

import gspd.ispd.motor.filas.Tarefa;
import gspd.ispd.motor.filas.servidores.CentroServico;
import javafx.util.Builder;

public class SingleTaskBuilder implements Builder<Tarefa> {

    ////////////////////////////////////
    ///////////// OVERRIDES ////////////
    ////////////////////////////////////

    @Override
    public Tarefa build() {
        return new Tarefa(
            getId(),
            getOwner(),
            getApplication(),
            getSource(),
            getSendFile(),
            getReceiveFile(),
            getProcessingSize(),
            getCreationTime()
        );
    }

    ////////////////////////////////////
    //////////// PROPERTIES ////////////
    ////////////////////////////////////

    /**
     * The task ID
     */
    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    /**
     * The task Owner
     */
    private String owner;
    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * The application name
     */
    private String application;
    public String getApplication() {
        return application;
    }
    public void setApplication(String application) {
        this.application = application;
    }

    /**
     * The source service centre
     */
    private CentroServico source;
    public CentroServico getSource() {
        return source;
    }
    public void setSource(CentroServico source) {
        this.source = source;
    }

    /**
     * The file to send's size
     */
    private double sendFile;
    public double getSendFile() {
        return sendFile;
    }
    public void setSendFile(double sendFile) {
        this.sendFile = sendFile;
    }

    /**
     * The file to receive's size
     */
    private double receiveFile;
    public double getReceiveFile() {
        return receiveFile;
    }
    public void setReceiveFile(double receiveFile) {
        this.receiveFile = receiveFile;
    }

    /**
     * The processing size
     */
    private double processingSize;
    public double getProcessingSize() {
        return processingSize;
    }
    public void setProcessingSize(double processingSize) {
        this.processingSize = processingSize;
    }

    /**
     * The task creation time instant
     */
    private double creationTime;
    public double getCreationTime() {
        return creationTime;
    }
    public void setCreationTime(double creationTime) {
        this.creationTime = creationTime;
    }
}
