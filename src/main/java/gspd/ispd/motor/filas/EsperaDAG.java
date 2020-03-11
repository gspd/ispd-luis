package gspd.ispd.motor.filas;

import gspd.ispd.motor.filas.servidores.CentroServico;

public class EsperaDAG extends TarefaDAG {

    double time;

    public EsperaDAG(int id, double time, String proprietario, String aplicacao, CentroServico origem, double arquivoEnvio, double tamProcessamento, double tempoCriacao) {
        super(id, proprietario, aplicacao, origem, arquivoEnvio, tamProcessamento, tempoCriacao);
        this.time = time;
    }

    public EsperaDAG(int id, String proprietario, String aplicacao, CentroServico origem, double arquivoEnvio, double tamProcessamento, double tempoCriacao) {
        super(id, proprietario, aplicacao, origem, arquivoEnvio, tamProcessamento, tempoCriacao);
    }

    public EsperaDAG(int id, String proprietario, String aplicacao, CentroServico origem, double arquivoEnvio, double arquivoRecebimento, double tamProcessamento, double tempoCriacao) {
        super(id, proprietario, aplicacao, origem, arquivoEnvio, arquivoRecebimento, tamProcessamento, tempoCriacao);
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getTime() {
        return time;
    }
}
