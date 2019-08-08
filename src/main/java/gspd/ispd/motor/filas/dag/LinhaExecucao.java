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
 * LinhaExecucao.java
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

import java.util.ArrayList;

/**
 * Classe contendo conjunto de blocos que devem ser executados e número de repetições
 * Modelada para representar uma thread ou um loop no motor
 * @author Denison
 */
public class LinhaExecucao {

    /**
     * Conjunto de tarefas DAG que o objeto faz parte
     */
    private final AppDAG app;
    private final Integer start;
    private final Integer iteration;
    private Integer iterationAtual;
    private Integer blocoAtual;
    private final ArrayList blocks;

    public LinhaExecucao(Integer start, Integer iteration, AppDAG application) {
        this.start = start;
        if (iteration > 0) {
            this.iteration = iteration - 1;
            this.blocoAtual = 0;
        } else {
            this.iteration = 0;
            this.blocoAtual = -1;
        }
        this.iterationAtual = 0;
        blocks = new ArrayList();
        app = application;
    }

    public void addBlock(Object block) {
        if (block instanceof Send
                || block instanceof Receive
                || block instanceof Process
                || block instanceof LinhaExecucao) {
            blocks.add(block);
        } else {
            throw new IllegalArgumentException(block + ": Não implementado ainda!");
        }
    }

    public Object getBlock() {
        if (blocoAtual < 0 || blocoAtual >= blocks.size()) {
            return null;
        } else if (blocks.get(blocoAtual) instanceof LinhaExecucao) {
            Object block = ((LinhaExecucao) blocks.get(blocoAtual)).getBlock();
            if (block != null) {
                return block;
            } else {
                blocoAtual++;
                return getBlock();
            }
        }
        return blocks.get(blocoAtual);
    }

    public void setNextBlock() {
        if (blocks.get(blocoAtual) instanceof LinhaExecucao) {
            ((LinhaExecucao)blocks.get(blocoAtual)).setNextBlock();
        } else {
            blocoAtual++;
            if (blocoAtual == blocks.size() && iterationAtual < iteration) {
                blocoAtual = 0;
                iterationAtual++;
            }
        }
    }

    /**
     * Busca a tarefa referenciada pelo contador da iteração
     *
     * @return tarefa atualmente apontada pelo loop
     */
    public TarefaDAG getAtualTask() {
        System.out.println("Buscar tarefa pelo id: "+(start+iterationAtual));
        return app.getTarefa(start + iterationAtual);
    }

    public AppDAG getAppDAG() {
        return app;
    }
}
