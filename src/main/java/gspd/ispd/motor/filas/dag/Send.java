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
 * Send.java
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

/**
 * Instrução de envio de mensagem
 * @author denison
 */
public class Send {

    private final Object destino;
    private final Double tamanho;

    public Send(Object destino, Double tamanho) {
        if (destino instanceof TarefaDAG
                || destino instanceof Receive
                || destino instanceof LinhaExecucao) {
            this.destino = destino;
        } else {
            throw new IllegalArgumentException(destino+": Destino não é valido");
        }
        this.tamanho = tamanho;
    }

    public TarefaDAG getDestino() {
        if (destino instanceof TarefaDAG) {
            return (TarefaDAG) destino;
        } else if(destino instanceof Receive) {
            return ((Receive)destino).getOrigem();
        } else if(destino instanceof LinhaExecucao) {
            return ((LinhaExecucao)destino).getAtualTask();
        }
        return null;
    }

    public Double getTamanho() {
        return tamanho;
    }
}
