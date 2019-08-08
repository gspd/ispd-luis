/* ==========================================================
 * iSPD : iconic Simulator of Parallel and Distributed System
 * ==========================================================
 *
 * (C) Copyright 2010-2014, by Grupo de pesquisas em Sistemas Paralelos e Distribuídos da Unesp (GSPD).
 *
 * Project Info:  http://gspd.dcce.ibilce.unesp.br/
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 *
 * ---------------
 * TarefaDAG.java
 * ---------------
 * (C) Copyright 2014, by Grupo de pesquisas em Sistemas Paralelos e Distribuídos da Unesp (GSPD).
 *
 * Original Author:  Denison Menezes (for GSPD);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 
 * 14-Out-2014 : Version 2.0.2;
 *
 */
package gspd.ispd.motor.filas.dag;

import gspd.ispd.motor.filas.Tarefa;
import gspd.ispd.motor.filas.servidores.CentroServico;
import java.util.ArrayList;

/**
 * Representação de uma tarefa complexa para o motor de simulação
 * @author denison
 */
public class TarefaDAG extends Tarefa {

    private LinhaExecucao thread;
    private Integer rank;
    private ArrayList<TarefaDAG> depende;
    private ArrayList<TarefaDAG> libera;

    public TarefaDAG(int id, int rank, String proprietario, AppDAG aplicacao, CentroServico origem, double arquivoEnvio, double tamProcessamento, double tempoCriacao) {
        super(id, proprietario, aplicacao.getNome(), origem, arquivoEnvio, tamProcessamento, tempoCriacao);
        this.rank = rank;
        thread = new LinhaExecucao(0, 1, aplicacao);
        depende = new ArrayList<TarefaDAG>();
        libera = new ArrayList<TarefaDAG>();
    }

    public TarefaDAG(int id, int rank, String proprietario, AppDAG aplicacao, CentroServico origem, double arquivoEnvio, double arquivoRecebimento, double tamProcessamento, double tempoCriacao) {
        super(id, proprietario, aplicacao.getNome(), origem, arquivoEnvio, arquivoRecebimento, tamProcessamento, tempoCriacao);
        this.rank = rank;
        thread = new LinhaExecucao(0, 1, aplicacao);
        depende = new ArrayList<TarefaDAG>();
        libera = new ArrayList<TarefaDAG>();
    }

    public TarefaDAG(Tarefa tarefa) {
        super(tarefa);
        throw new Error("Error: You can not copy a DAG task!");
    }
    
    public Integer getRank() {
        return rank;
    }

    /*@Override
     public double getTamProcessamento() {
     if(programCounter < 0 || programCounter >= blocks.size()) {
     return super.getTamProcessamento();
     } else {
     return (Double) blocks.get(programCounter)[1];
     }
     }

     public int getNextBlock() {
     if(programCounter < 0) {
     return START;
     } else if(programCounter == blocks.size()) {
     return END;
     }
     return (Integer) blocks.get(programCounter)[0];
     }*/
    @Override
    public String toString() {
        return "Task " + this.getIdentificador() + " [DAG " + rank + "]";
    }

    /*public TarefaDAG getDestino() {
     return (TarefaDAG) blocks.get(programCounter)[2];
     }

     @Override
     public double getTamComunicacao() {
     if(programCounter <= 0 || programCounter >= blocks.size()) {
     return super.getTamComunicacao();
     } else {
     return (Double) blocks.get(programCounter)[1];
     }
     }*

     /**
     * Adiciona instrução PROCESS
     * [CODIGO] [TAMANHO]
     * @param blocoProcess tamanho do bloco de processamento em Mflops
     *
     public void addProcess(Double blocoProcess) {
     Object[] process = new Object[3];
     process[0] = PROCESS;
     process[1] = blocoProcess;
     blocks.add(process);
     }

     /**
     * Adiciona instrução RECEIVE
     * [CODIGO] [ORIGEM]
     * @param tarefaDAG item que irá se conectar com esta tarefa
     *
     public void addReceive(TarefaDAG tarefaDAG) {
     Object[] receive = new Object[3];
     receive[0] = RECEIVE;
     receive[1] = null;
     receive[2] = tarefaDAG;
     blocks.add(receive);
     }
    
     /**
     * Adiciona instrução SEND
     * [CODIGO] [TAMANHO] [DESTINO]
     * @param tar Destino da mensagem
     * @param arquivoEnvio tamanho da mensagem
     *
     public void addSend(TarefaDAG tar, Double arquivoEnvio) {
     Object[] send = new Object[3];
     send[0] = SEND;
     send[1] = arquivoEnvio;
     send[2] = tar;
     blocks.add(send);
     }*/
    public ArrayList<TarefaDAG> getDepende() {
        return depende;
    }

    public ArrayList<TarefaDAG> getLibera() {
        return libera;
    }

    public LinhaExecucao getThread() {
        return thread;
    }
}
