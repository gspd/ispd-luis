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
 * AppDAG.java
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

import java.util.HashMap;

/**
 * Classe contendo todas as tarefas de uma aplicação
 * Utilizada para busca a partir de um rank
 * @author Denison
 */
public class AppDAG {

    private final HashMap<Integer, TarefaDAG> tarefas;
    private final String nome;

    public AppDAG(String nome) {
        this.nome = nome;
        tarefas = new HashMap<Integer, TarefaDAG>();
    }
    
    public void addTarefa(TarefaDAG task) {
        if(!task.getAplicacao().equals(nome))
            throw new IllegalArgumentException("Tarefa não pertence a aplicação");
        tarefas.put(task.getRank(), task);
        
    }
    
    public TarefaDAG getTarefa(Integer rank) {
        return tarefas.get(rank);
    }

    public String getNome() {
        return nome;
    }
}
