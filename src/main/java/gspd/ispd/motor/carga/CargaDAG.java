/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gspd.ispd.motor.carga;

import gspd.ispd.arquivo.xml.DAGXML;
import gspd.ispd.motor.filas.RedeDeFilas;
import gspd.ispd.motor.filas.Tarefa;
import gspd.ispd.motor.filas.dag.TarefaDAG;
import gspd.ispd.motor.filas.servidores.CS_Processamento;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author denison
 */
public class CargaDAG extends GerarCarga {

    private String aplicacao;
    private String proprietario;
    private String escalonador;
    private Integer numeroTarefas;
    private Integer numeroIteracoes;
    private File arquivo;
    private int inicioIdentificadorTarefa;
    
    public CargaDAG(String aplicacao, String proprietario, String escalonador, Integer numTarefas, Integer numIteracoes, File dagFile) {
        this.inicioIdentificadorTarefa = 0;
        this.aplicacao = aplicacao;
        this.proprietario = proprietario;
        this.escalonador = escalonador;
        this.numeroIteracoes = numIteracoes;
        this.numeroTarefas = numTarefas;
        this.arquivo = dagFile;
    }

    @Override
    public List<Tarefa> toTarefaList(RedeDeFilas rdf) {
        List<Tarefa> list = new ArrayList<Tarefa>();
        for (int i = 0; i < numeroTarefas; i++) {
            CS_Processamento mestre = null;
            for (CS_Processamento maq : rdf.getMestres()) {
                if (maq.getId().equals(escalonador)) {
                    mestre = maq;
                    break;
                }
            }
            try {
                List<Tarefa> temp = DAGXML.getTarefasDAG(arquivo,inicioIdentificadorTarefa,mestre,aplicacao,proprietario);
                inicioIdentificadorTarefa += temp.size();
                list.addAll(temp);
            } catch (Exception ex) {
                Logger.getLogger(CargaDAG.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println(list);
        return list;
    }

    @Override
    public String toString() {
        return String.format("%s %d %d %s",
                escalonador, 
                numeroTarefas,
                numeroIteracoes,
                arquivo.getAbsolutePath());
    }

    @Override
    public int getTipo() {
        return GerarCarga.DAG;
    }

    public String getAplicacao() {
        return aplicacao;
    }

    public String getProprietario() {
        return proprietario;
    }

    public String getEscalonador() {
        return escalonador;
    }

    public Integer getNumeroTarefas() {
        return numeroTarefas;
    }

    public Integer getNumeroIteracoes() {
        return numeroIteracoes;
    }
    
    public File getArquivo() {
        return arquivo;
    }

    void setInicioIdentificadorTarefa(int inicio) {
        inicioIdentificadorTarefa = inicio;
    }

    public void setAplicacao(String aplicacao) {
        this.aplicacao = aplicacao;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public void setEscalonador(String escalonador) {
        this.escalonador = escalonador;
    }

    public void setNumeroTarefas(Integer numeroTarefas) {
        this.numeroTarefas = numeroTarefas;
    }

    public void setArquivo(File arquivo) {
        this.arquivo = arquivo;
    }
    
}
