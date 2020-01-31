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
 * DesenhoDAG.java
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
package gspd.ispd.gui.iconico.dag;

import gspd.ispd.arquivo.xml.DAGXML;
import gspd.ispd.gui.iconico.AreaDesenho;
import gspd.ispd.gui.iconico.Aresta;
import gspd.ispd.gui.iconico.Icone;
import gspd.ispd.gui.iconico.Vertice;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.w3c.dom.Document;

/**
 * JPanel com área de desenho para edição da aplicação DAG
 *
 * @author denison
 */
public class DesenhoDAG extends AreaDesenho {

    public final static int DAGTASK = 0;
    public final static int COMPLEXTASK = 1;
    public final static int DATA = 2;
    public final static int LOOP = 3;
    public final static int SEND = 4;
    public final static int RECEIVE = 5;
    public final static int PROCESS = 6;
    public final static int THREAD = 7;
    public final static int INTERVAL = 60;
    private ArrayList<Task> tasks;
    private ArrayList<DataFile> files;
    private int tipoVertice = -1;
    private int indices;
    private Dimension size;
    
    public final static int NONE = -1;
    public final static int TASK = 0;
    public final static int DELAY = 1;
    public final static int SYNC = 2;
    public final static int DEPENDENCY = 3;
    public final static int EXPANSION = 4;
    public final static int RECURSION = 5;

    public DesenhoDAG() {
        super(true, true, true, true);
        size = new java.awt.Dimension(2000, 1000);
        indices = 0;
        setPopupButtonText("Remove", null, null, null);
        this.setUnits(INTERVAL);
        tasks = new ArrayList<Task>();
        files = new ArrayList<DataFile>();
    }

    @Override
    public Dimension getMinimumSize() {
        return size;
    }

    @Override
    public Dimension getPreferredSize() {
        return size;
    }

//    @Override
//    public void adicionarVertice(int x, int y) {
//        switch (tipoVertice) {
//            case DAGTASK:
//                Task beginDag = new Task(x, y, indices);
//                Task endDag = new Task(x, y + INTERVAL * 2, indices);
//                Line t1 = new Line(beginDag, endDag);
//                vertices.add(beginDag);
//                vertices.add(endDag);
//                arestas.add(t1);
//                tasks.add(beginDag);
//                beginDag.exclusiveItemActionPerformed(null);
//                indices++;
//                break;
//            case COMPLEXTASK:
//                Task begin = new Task(x, y, indices);
//                begin.setDag(Boolean.FALSE);
//                Task end = new Task(x, y + INTERVAL * 2, indices);
//                end.setDag(Boolean.FALSE);
//                Line t2 = new Line(begin, end);
//                vertices.add(begin);
//                vertices.add(end);
//                arestas.add(t2);
//                tasks.add(begin);
//                begin.exclusiveItemActionPerformed(null);
//                indices++;
//                break;
//            case DATA:
//                DataFile dt = new DataFile(x, y, indices);
//                files.add(dt);
//                vertices.add(dt);
//                indices++;
//                break;
//            case LOOP:
//                Loop loop = new Loop(x, y);
//                vertices.add(loop);
//                vertices.add(new Loop(x, y + INTERVAL, loop));
//                break;
//            case SEND:
//                vertices.add(new Send(x, y));
//                break;
//            case RECEIVE:
//                vertices.add(new Receive(x, y));
//                break;
//            case PROCESS:
//                vertices.add(new ProcessingBlock(x, y));
//                break;
//            case THREAD:
//                Thread th1 = new Thread(x + INTERVAL, y - INTERVAL);
//                Thread th2 = new Thread(x + INTERVAL, y + INTERVAL);
//                vertices.add(new Thread(x, y, th1, th2));
//                vertices.add(th1);
//                vertices.add(th2);
//                break;
//        }
//        setAddVertice(false);
//    }
    
    @Override
    public void adicionarVertice(int x, int y) {
        switch (tipoVertice) {
            case TASK:
                Task beginDag = new Task(x, y, indices);
                Task endDag = new Task(x, y + INTERVAL * 2, indices);
                Line t1 = new Line(beginDag, endDag);
                vertices.add(beginDag);
                vertices.add(endDag);
                arestas.add(t1);
                tasks.add(beginDag);
                beginDag.exclusiveItemActionPerformed(null);
                indices++;
                break;
            default:
                System.out.println("NOT WORKING");
        }
    }

    @Override
    public void adicionarAresta(Vertice Origem, Vertice Destino) {
        if (Origem instanceof Block || Destino instanceof Block) {
            if (Origem instanceof Task && !((Task) Origem).isDAG()) {
                ((Block) Destino).attachTo((Task) Origem);
            } else if (Destino instanceof Task && !((Task) Destino).isDAG()) {
                ((Block) Origem).attachTo((Task) Destino);
            } else if (Origem instanceof Block && Destino instanceof Block) {
                ((Block) Origem).attachTo((Block) Destino);
            }
            setAddAresta(false);
        } else if (Origem instanceof Identificavel && Destino instanceof Identificavel) {
            Boolean ok = true;
            if (Origem instanceof DataFile && Destino instanceof DataFile) {
                //verifica se é uma conexão entre dois arquivos
                ok = false;
            } else if (((Identificavel) Origem).isConectado(Destino)) {
                //verifica se já possui uma conexão diretamente
                ok = false;
            } else if (((Identificavel) Destino).isConectadoIndiretamente(Origem)) {
                //verifica se já possui uma conexão indiretamente
                ok = false;
            } else if (Origem instanceof Task && !((Task) Origem).isDAG()) {
                //verifica se origem é do tipo DAG
                ok = false;
            } else if (Destino instanceof Task && !((Task) Destino).isDAG()) {
                //verifica se destino é do tipo DAG
                ok = false;
            }
            //se for possivel criar conexão
            if (ok) {
                Message msn = new Message(Origem, Destino);
                ((Identificavel) Origem).getSaidas().add(msn);
                ((Identificavel) Destino).getEntradas().add(msn);
                arestas.add(msn);
            }
            setAddAresta(false);
        }
    }

    @Override
    public void showActionIcon(MouseEvent me, Icone icon) {
        if (icon instanceof Task) {
            Task.edit(this.getParent(), (Task) icon);
        } else if (icon instanceof Message) {
            Message.edit(this.getParent(), (Message) icon);
        } else if (icon instanceof Loop) {
            Loop.edit(this.getParent(), (Loop) icon);
        } else if (icon instanceof Send) {
            Send.edit(this.getParent(), (Send) icon, tasks);
        } else if (icon instanceof Receive) {
            Receive.edit(this.getParent(), (Receive) icon);
        } else if (icon instanceof ProcessingBlock) {
            ProcessingBlock.edit(this.getParent(), (ProcessingBlock) icon);
        } else if (icon instanceof DataFile) {
            DataFile.edit(this.getParent(), (DataFile) icon);
        }
    }

    @Override
    public void showSelectionIcon(MouseEvent me, Icone icon) {
    }

    @Override
    public void botaoPainelActionPerformed(ActionEvent evt) {
    }

    @Override
    public void botaoVerticeActionPerformed(ActionEvent evt) {
    }

    @Override
    public void botaoArestaActionPerformed(ActionEvent evt) {
    }

    @Override
    public void botaoIconeActionPerformed(ActionEvent evt) {
        for (Icone iconeRemover : selecionados) {
            if (iconeRemover instanceof Vertice) {
                if (iconeRemover instanceof Loop) {
                    ((Loop) iconeRemover).destroy(vertices);
                } else if (iconeRemover instanceof Thread) {
                    ((Thread) iconeRemover).destroy(vertices);
                } else if (iconeRemover instanceof Block) {
                    vertices.remove((Vertice) iconeRemover);
                    ((Block) iconeRemover).remove();
                } else if (iconeRemover instanceof DataFile) {
                    DataFile data = (DataFile) iconeRemover;
                    files.remove(data);
                    vertices.remove(data);
                    for (Aresta icon : data.getEntradas()) {
                        arestas.remove(icon);
                    }
                    for (Aresta icon : data.getSaidas()) {
                        arestas.remove(icon);
                    }
                } else if (iconeRemover instanceof Task) {
                    Task tarBegin = (Task) ((Task) iconeRemover).getLine().getOrigem();
                    Task tarEnd = (Task) ((Task) iconeRemover).getLine().getDestino();
                    tasks.remove(tarBegin);
                    vertices.remove(tarBegin);
                    vertices.remove(tarEnd);
                    arestas.remove(tarBegin.getLine());
                    tarBegin.getLine().destroy(vertices);
                    for (Aresta icon : tarBegin.getEntradas()) {
                        arestas.remove(icon);
                        for (Task task : tasks) {
                            task.getEntradas().remove((Message) icon);
                            task.getSaidas().remove((Message) icon);
                        }
                        for (DataFile file : files) {
                            file.getEntradas().remove((Message) icon);
                            file.getSaidas().remove((Message) icon);
                        }
                    }
                    for (Aresta icon : tarEnd.getSaidas()) {
                        arestas.remove(icon);
                        for (Task task : tasks) {
                            task.getEntradas().remove((Message) icon);
                            task.getSaidas().remove((Message) icon);
                        }
                        for (DataFile file : files) {
                            file.getEntradas().remove((Message) icon);
                            file.getSaidas().remove((Message) icon);
                        }
                    }
                }
            } else if (iconeRemover instanceof Message) {
                arestas.remove((Aresta) iconeRemover);
                for (Task task : tasks) {
                    task.getEntradas().remove((Message) iconeRemover);
                    task.getSaidas().remove((Message) iconeRemover);
                }
                for (DataFile file : files) {
                    file.getEntradas().remove((Message) iconeRemover);
                    file.getSaidas().remove((Message) iconeRemover);
                }
            }
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    /**
     * Seleciona um icone dentro do ponto especificado, sobrescrito para
     * selecionar sempre o inicio de uma tarefa
     *
     * @param x posição do mouse no eixo x na area de desenho
     * @param y posição do mouse no eixo y na area de desenho
     * @return Icone selecionado na posição especificada
     */
    @Override
    protected Icone getSelecionado(int x, int y) {
        for (Vertice vertice : vertices) {
            if (vertice.contains(x, y)) {
                if (vertice instanceof Task) {
                    if (((Task) vertice).isVisible()) {
                        return vertice;
                    }
                } else {
                    return vertice;
                }
            }
        }
        for (Aresta aresta : arestas) {
            if (aresta.contains(x, y)) {
                return aresta;
            }
        }
        if (!selecionados.isEmpty()) {
            for (Icone icone : selecionados) {
                icone.setSelected(false);
            }
            selecionados.clear();
        }
        return null;
    }

    public void setTipoVertice(int tipo) {
        tipoVertice = tipo;
    }

    public void save(File file) throws Exception {
        DAGXML modelo = new DAGXML();
        for (Task tar : tasks) {
            modelo.addDagTask(tar);
        }
        for (DataFile fl : files) {
            modelo.addDagFile(fl);
        }
        if (!tasks.isEmpty()) {
            DAGXML.escrever(modelo.getDocument(), file);
            JOptionPane.showMessageDialog(this.getParent(), "The file has been saved with successfully!");
        } else {
            throw new ExceptionInInitializerError("The model has no icon!");
        }
    }

    public void newDAG() {
        vertices.clear();
        arestas.clear();
        selecionados.clear();
        tasks.clear();
        files.clear();
        indices = 0;
        repaint();
    }

    public void open(File file) throws Exception {
        Document doc = DAGXML.ler(file);
        vertices.clear();
        arestas.clear();
        selecionados.clear();
        tasks.clear();
        files.clear();
        DAGXML.newDAG(doc, vertices, arestas);
        int x = 800, y = 600;
        for (Vertice vertice : vertices) {
            if (vertice instanceof Task) {
                Task task = (Task) vertice;
                if (task.getLine().getOrigem().equals(task)) {
                    tasks.add(task);
                    if (task.getRank() > indices) {
                        indices = task.getRank();
                    }
                    if (task.getX() > x) {
                        x = task.getX();
                    }
                    if (task.getY() > y) {
                        y = task.getY();
                    }
                }
            }
            if (vertice instanceof DataFile) {
                DataFile fl = (DataFile) vertice;
                files.add(fl);
            }
        }
        if (indices < files.size() + tasks.size()) {
            indices = files.size() + tasks.size();
        }
    }
}
