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
 * DAGXML.java
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
package gspd.ispd.arquivo.xml;

import gspd.ispd.gui.iconico.Aresta;
import gspd.ispd.gui.iconico.Vertice;
import gspd.ispd.gui.iconico.dag.Block;
import gspd.ispd.gui.iconico.dag.DataFile;
import gspd.ispd.gui.iconico.dag.DesenhoDAG;
import gspd.ispd.gui.iconico.dag.Identificavel;
import gspd.ispd.gui.iconico.dag.Line;
import gspd.ispd.gui.iconico.dag.Loop;
import gspd.ispd.gui.iconico.dag.Message;
import gspd.ispd.gui.iconico.dag.ProcessingBlock;
import gspd.ispd.gui.iconico.dag.Receive;
import gspd.ispd.gui.iconico.dag.Send;
import gspd.ispd.gui.iconico.dag.Task;
import gspd.ispd.motor.filas.Tarefa;
import gspd.ispd.motor.filas.dag.AppDAG;
import gspd.ispd.motor.filas.dag.LinhaExecucao;
import gspd.ispd.motor.filas.dag.TarefaDAG;
import gspd.ispd.motor.filas.servidores.CS_Processamento;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Classe para leitura e escrita do arquivo XML contendo uma aplicação DAG
 * @author denison
 */
public class DAGXML {

    private final Document descricao;
    private final Element trace;

    public DAGXML() {
        descricao = ManipuladorXML.novoDocumento();
        Element system = descricao.createElement("system");
        trace = descricao.createElement("trace");
        Element format = descricao.createElement("format");
        trace.appendChild(format);
        format.setAttribute("kind", "iSPD_DAG");
        system.appendChild(trace);
        descricao.appendChild(system);
    }

    /**
     * Converte uma tarefa e todos os seus blocos de instruções para o modelo
     * xml que prepresenta aplicações DAG, e armazena no document descrição
     *
     * @param dagTask tarefa que deve ser adicionada
     * @throws Exception
     */
    public void addDagTask(Task dagTask) throws Exception {
        Task begin = (Task) dagTask.getLine().getOrigem();
        Task end = (Task) dagTask.getLine().getDestino();
        Element docTask = descricao.createElement("task");
        docTask.setAttribute("id", dagTask.getIdentificador());
        docTask.setAttribute("cpsz", begin.getComputingSize().toString());
        docTask.setAttribute("cmsz", begin.getCommunicationSize().toString());

        if (dagTask.isDAG()) {
            Element character = descricao.createElement("dag");
            //Adicionar tarefas das quais esta tarefa depende
            for (Message entrada : begin.getEntradas()) {
                Identificavel item = (Identificavel) entrada.getOrigem();
                Element receive = newReceive(null, item.getIdentificador(), null);
                character.appendChild(receive);
            }
            //Adiciona liberações
            for (Message saida : end.getSaidas()) {
                Identificavel destino = (Identificavel) saida.getDestino();
                Element send = newSend(destino.getIdentificador(), null, saida.getSize());
                character.appendChild(send);
            }
            if (character.hasChildNodes()) {
                docTask.appendChild(character);
            }
        } else {
            //Adicionar os blocos de instruções
            Element thread = descricao.createElement("thread");
            Block itens = dagTask.getLine().getFirst().getNext();
            while (itens != null && itens.getX() != null) {
                Element item = newIcone(itens);
                thread.appendChild(item);
                if (itens instanceof Loop) {
                    itens = ((Loop) itens).getEnd();
                }
                itens = itens.getNext();
            }
            if (thread.hasChildNodes()) {
                docTask.appendChild(thread);
            }
        }
        //Adicionar tarefas que dependem desta tarefa
        trace.appendChild(docTask);
    }

    public void addDagFile(DataFile fl) throws Exception {
        Element docFile = descricao.createElement("file");
        docFile.setAttribute("id", fl.getIdentificador());
        docFile.setAttribute("size_min", fl.getSizeMin().toString());
        docFile.setAttribute("size_max", fl.getSizeMax().toString());
        //Adicionar tarefas das quais este arquivo depende
        for (Message in : fl.getEntradas()) {
            Identificavel item = (Identificavel) in.getOrigem();
            Element receive = newReceive(null, item.getIdentificador(), null);
            docFile.appendChild(receive);
        }
        for (Message out : fl.getSaidas()) {
            Identificavel destino = (Identificavel) out.getDestino();
            Element send = newSend(destino.getIdentificador(), null, out.getSize());
            docFile.appendChild(send);
        }
        trace.appendChild(docFile);
    }

    /**
     * extrai o conteúdo da aplicação DAG de um document xml e salva em uma
     * lista contendo os vértices e arestas do grafos acíclicos dirigidos
     *
     * @param descricao documento com os nós do xml
     * @param vertices lista com os vertices do DAG
     * @param arestas lista com as arestas do DAG
     * @throws Exception
     */
    public static void newDAG(Document descricao, Set<Vertice> vertices, Set<Aresta> arestas) throws Exception {
        int x = DesenhoDAG.INTERVAL;
        int y = DesenhoDAG.INTERVAL;
        HashMap<String, Identificavel> idItens = new HashMap<String, Identificavel>();
        NodeList dagTasks = descricao.getElementsByTagName("task");
        NodeList dagFiles = descricao.getElementsByTagName("file");
        if ((dagTasks.getLength() + dagFiles.getLength()) == 0) {
            throw new Exception("There are no tasks in the model!");
        }
        int num_linhas = (int) Math.floor(Math.sqrt(dagFiles.getLength() + dagTasks.getLength())) + 1;
        int num_col = (num_linhas + 1) * DesenhoDAG.INTERVAL;
        num_linhas *= DesenhoDAG.INTERVAL;
        //Preenche o hashmap com todas as tarefas presentes no modelo
        for (int i = 0; i < dagTasks.getLength(); i++) {
            //cria tarefa
            Element task = (Element) dagTasks.item(i);
            Integer id = Integer.valueOf(task.getAttribute("id"));
            Task begin = new Task(x, y, id);
            Task end = new Task(x, y + DesenhoDAG.INTERVAL, id);
            begin.setCommunicationSize(Double.valueOf(task.getAttribute("cmsz")));
            begin.setComputingSize(Double.valueOf(task.getAttribute("cpsz")));
            Line t1 = new Line(begin, end);
            //adiciona para nas listas
            idItens.put(id.toString(), begin);
            vertices.add(begin);
            vertices.add(end);
            arestas.add(t1);
            x += DesenhoDAG.INTERVAL;
            if (x > num_col) {
                x = DesenhoDAG.INTERVAL;
                y += DesenhoDAG.INTERVAL;
            }
        }
        //Preenche hashmap com os arquivos
        for (int i = 0; i < dagFiles.getLength(); i++) {
            Element xmlfile = (Element) dagFiles.item(i);
            String id = xmlfile.getAttribute("id");
            Double min = Double.valueOf(xmlfile.getAttribute("size_min"));
            Double max = Double.valueOf(xmlfile.getAttribute("size_max"));
            DataFile file = new DataFile(x, y, id);
            file.setSizeMin(min);
            file.setSizeMax(max);
            //adiciona para nas listas
            idItens.put(id, file);
            vertices.add(file);
            x += DesenhoDAG.INTERVAL;
            if (x > num_col) {
                x = DesenhoDAG.INTERVAL;
                y += DesenhoDAG.INTERVAL;
            }
        }
        //busca conexões do arquivo
        for (int i = 0; i < dagFiles.getLength(); i++) {
            Element xmlfile = (Element) dagFiles.item(i);
            String id = xmlfile.getAttribute("id");
            DataFile file = (DataFile) idItens.get(id);
            NodeList file_sends = xmlfile.getElementsByTagName("send");
            for (int j = 0; j < file_sends.getLength(); j++) {
                Element item = (Element) file_sends.item(j);
                if ("send".equals(item.getNodeName())) {
                    String tempId = item.getAttribute("to");
                    Identificavel destino = idItens.get(tempId);
                    Message msn = new Message(file, (Vertice) destino);
                    msn.setSize(Double.valueOf(item.getAttribute("size")));
                    file.getSaidas().add(msn);
                    destino.getEntradas().add(msn);
                    arestas.add(msn);
                }
            }
        }
        //Cria os blocos de instruções de cada tarefa
        for (int i = 0; i < dagTasks.getLength(); i++) {
            Element task = (Element) dagTasks.item(i);
            String id = task.getAttribute("id");
            Task begin = (Task) idItens.get(id);
            NodeList dagList = task.getElementsByTagName("dag");
            NodeList threadList = task.getElementsByTagName("thread");
            //busca conexões da tarefa DAG
            if (dagList.getLength() == 1) {
                NodeList dag_sends = ((Element) dagList.item(0)).getElementsByTagName("send");
                for (int j = 0; j < dag_sends.getLength(); j++) {
                    Element item = (Element) dag_sends.item(j);
                    String tempId = item.getAttribute("to");
                    Identificavel destino = idItens.get(tempId);
                    Message msn = new Message(begin, (Vertice) destino);
                    msn.setSize(Double.valueOf(item.getAttribute("size")));
                    begin.getSaidas().add(msn);
                    destino.getEntradas().add(msn);
                    arestas.add(msn);
                }
            } else if (threadList.getLength() == 1) {
                Element thread = (Element) threadList.item(0);
                begin.setDag(Boolean.FALSE);
                ((Task) begin.getLine().getDestino()).setDag(Boolean.FALSE);
                //busca cada elemento e relaciona com a tarefa
                for (int j = 0; j < thread.getChildNodes().getLength(); j++) {
                    try {
                        Element item = (Element) thread.getChildNodes().item(j);
                        addElement(item, (Task) begin.getLine().getDestino(), vertices, arestas, idItens);
                    } catch (Exception e) {
                    }
                }
            }
            //minimiza a tarefa na tela
            begin.exclusiveItemActionPerformed(null);
        }
    }

    public static ArrayList<Tarefa> getTarefasDAG(File arquivo, int inicioIdentificadorTarefa, CS_Processamento mestre, String aplicacao, String proprietario) throws Exception {
        Document modelo = ler(arquivo);
        NodeList dagTasks = modelo.getElementsByTagName("task");
        if (dagTasks.getLength() == 0) {
            throw new Exception("There are no tasks in the model!");
        }
        HashMap<String, Object> idTask = new HashMap<String, Object>();
        ArrayList<Tarefa> tasks = new ArrayList<Tarefa>();
        AppDAG aplicacaoDAG = new AppDAG(aplicacao);
        //Preenche o hashmap com todas as tarefas presentes no modelo
        for (int i = 0; i < dagTasks.getLength(); i++) {
            //cria tarefa
            Element task = (Element) dagTasks.item(i);
            Double comunicacao = Double.valueOf(task.getAttribute("cmsz"));
            Double computacao = Double.valueOf(task.getAttribute("cpsz"));
            Double criacao = Double.valueOf(task.getAttribute("arr"));
            if (proprietario == null || "".equals(proprietario)) {
                proprietario = task.getAttribute("usr");
            }
            Integer rank = Integer.valueOf(task.getAttribute("id"));
            if (!task.hasChildNodes()) {
                Tarefa tar = new Tarefa(
                        inicioIdentificadorTarefa + i,
                        proprietario,
                        aplicacao,
                        mestre,
                        comunicacao,
                        0.0009765625 /*arquivo recebimento*/,
                        computacao,
                        criacao /* criação */);
                //adiciona para nas listas
                tasks.add(tar);
            } else {
                //cria a tarefa ainda não possui valor de processamento...
                TarefaDAG tar = new TarefaDAG(
                        inicioIdentificadorTarefa + i,
                        rank,
                        proprietario,
                        aplicacaoDAG,
                        mestre,
                        comunicacao,
                        0.0009765625 /*arquivo recebimento*/,
                        computacao,
                        criacao /* criação */);
                //adiciona para nas listas
                aplicacaoDAG.addTarefa(tar);
                idTask.put(rank.toString(), tar);
                tasks.add(tar);
            }
        }
        //cria blocos de instruções
        for (int i = 0; i < dagTasks.getLength(); i++) {
            System.out.println("----------------------------Iniciando nova tarefa");
            Element task = (Element) dagTasks.item(i);
            String id = task.getAttribute("id");
            System.out.println("Tarefa " + id);
            TarefaDAG tarefa = (TarefaDAG) idTask.get(id);
            NodeList dagList = task.getElementsByTagName("dag");
            NodeList threadList = task.getElementsByTagName("thread");
            if (dagList.getLength() == 1) {
                NodeList receives = ((Element) dagList.item(0)).getElementsByTagName("receive");
                //cria relação de dependencia inicial...
                for (int j = 0; j < receives.getLength(); j++) {
                    addBlock(id, (Element) receives.item(j), idTask);
                }

                Double size = tarefa.getTamProcessamento();
                gspd.ispd.motor.filas.dag.Process bloco = new gspd.ispd.motor.filas.dag.Process(size);
                tarefa.getThread().addBlock(bloco);

                NodeList sends = ((Element) dagList.item(0)).getElementsByTagName("send");
                //cria relação de dependencia final...
                for (int j = 0; j < sends.getLength(); j++) {
                    addBlock(id, (Element) sends.item(j), idTask);
                }

            } else if (threadList.getLength() == 1) {
                Element thread = (Element) threadList.item(0);
                //busca cada elemento e relaciona com a tarefa
                for (int j = 0; j < thread.getChildNodes().getLength(); j++) {
                    try {
                        Element item = (Element) thread.getChildNodes().item(j);
                        addBlock(id, item, idTask);
                    } catch (java.lang.ClassCastException e) {
                    }
                }

            } else if (tarefa != null) {
                Double size = tarefa.getTamProcessamento();
                gspd.ispd.motor.filas.dag.Process bloco = new gspd.ispd.motor.filas.dag.Process(size);
                tarefa.getThread().addBlock(bloco);
            }
        }
        System.out.println("--------------------------------Finalizar tarefa");
        return tasks;
    }

    /**
     * Adiciona um bloco de instrução a uma tarefa
     *
     * @param id Identificador do elemento que receberá o bloco
     * @param block bloco de instrução que será adicionado
     * @param elementos lista de elementos identificaveis
     */
    public static void addBlock(String id, Element block, HashMap<String, Object> elementos) {
        String tipo = block.getNodeName();
        TarefaDAG task = null;
        LinhaExecucao linha = null;
        if (elementos.get(id) instanceof TarefaDAG) {
            task = (TarefaDAG) elementos.get(id);
            linha = task.getThread();
        } else if (elementos.get(id) instanceof LinhaExecucao) {
            linha = (LinhaExecucao) elementos.get(id);
        } else {
            throw new IllegalArgumentException("Não implementado ainda");
        }

        if ("process".equals(tipo)) {
            Double size = Double.valueOf(block.getAttribute("size_max"));
            System.out.println("tar " + id + " process " + size);
            linha.addBlock(new gspd.ispd.motor.filas.dag.Process(size));

        } else if ("receive".equals(tipo)) {
            String tarId = block.getAttribute("from");
            String recId = block.getAttribute("id");
            System.out.println("tar " + id + " recebe de " + tarId);
            gspd.ispd.motor.filas.dag.Receive receive;
            if (tarId.equals("ANY")) {
                receive = new gspd.ispd.motor.filas.dag.Receive(tarId);
            } else {
                receive = new gspd.ispd.motor.filas.dag.Receive(elementos.get(tarId));
            }
            linha.addBlock(receive);
            elementos.put(recId, receive);
            if (elementos.get(tarId) instanceof TarefaDAG && task != null) {
                task.getDepende().add((TarefaDAG) elementos.get(tarId));
            }

        } else if ("send".equals(tipo)) {
            String tempId = block.getAttribute("to");
            System.out.println("tar " + id + " envia para " + tempId);
            Double comunicacao = Double.valueOf(block.getAttribute("size"));
            linha.addBlock(new gspd.ispd.motor.filas.dag.Send(elementos.get(tempId), comunicacao));
            if (elementos.get(tempId) instanceof TarefaDAG && task != null) {
                task.getLibera().add((TarefaDAG) elementos.get(tempId));
            }

        } else if ("loop".equals(tipo)) {
            String loopId = block.getAttribute("id");
            System.out.println("tar " + id + " add loop " + loopId);
            Integer start = Integer.valueOf(block.getAttribute("start"));
            Integer iteration = Integer.valueOf(block.getAttribute("iteration"));
            LinhaExecucao loop = new LinhaExecucao(start, iteration, linha.getAppDAG());
            elementos.put(loopId, loop);
            linha.addBlock(loop);
            NodeList loopLista = block.getChildNodes();
            for (int j = 0; j < loopLista.getLength(); j++) {
                try {
                    Element element = (Element) loopLista.item(j);
                    addBlock(loopId, element, elementos);
                } catch (java.lang.ClassCastException e) {
                }
            }
        }
    }

    /**
     * Retorna o document manipulado por esta classe
     *
     * @return DAG model
     */
    public Document getDocument() {
        return descricao;
    }

    public static Document ler(File xmlFile) throws ParserConfigurationException, IOException, SAXException {
        return ManipuladorXML.ler(xmlFile, "iSPDcarga.dtd");
    }

    public static boolean escrever(Document documento, File arquivo) {
        return ManipuladorXML.escrever(documento, arquivo, "iSPDcarga.dtd", false);
    }

    private static void addElement(Element item, Task end, Set<Vertice> vertices, Set<Aresta> arestas, HashMap<String, Identificavel> idTask) {
        String tipo = item.getNodeName();
        if ("process".equals(tipo)) {
            ProcessingBlock block = new ProcessingBlock(end.getX() + DesenhoDAG.INTERVAL, end.getY());
            block.attachTo(end);
            vertices.add(block);
            block.setSizeMin(Double.valueOf(item.getAttribute("size_min")));
            block.setSizeMax(Double.valueOf(item.getAttribute("size_max")));
            end.setPosition(end.getX(), end.getY() + DesenhoDAG.INTERVAL);
        } else if ("receive".equals(tipo)) {
            Receive receive = new Receive(end.getX() + DesenhoDAG.INTERVAL, end.getY());
            receive.attachTo(end);
            vertices.add(receive);
            receive.setId(item.getAttribute("id"));
            end.setPosition(end.getX(), end.getY() + DesenhoDAG.INTERVAL);
            idTask.put(receive.getIdentificador(), receive);
        } else if ("send".equals(tipo)) {
            Send send = new Send(end.getX() + DesenhoDAG.INTERVAL, end.getY());
            send.attachTo(end);
            vertices.add(send);
            //add task:port:size
            String id = item.getAttribute("to");
            send.setDestino((Vertice) idTask.get(id));
            send.setSize(Double.valueOf(item.getAttribute("size")));
            end.setPosition(end.getX(), end.getY() + DesenhoDAG.INTERVAL);
        } else if ("loop".equals(tipo)) {
            Loop loopBegin = new Loop(end.getX() + DesenhoDAG.INTERVAL, end.getY());
            Loop loopEnd = new Loop(end.getX() + DesenhoDAG.INTERVAL, end.getY() + DesenhoDAG.INTERVAL, loopBegin);
            vertices.add(loopBegin);
            vertices.add(loopEnd);
            loopBegin.attachTo(end);
            loopBegin.setId(item.getAttribute("id"));
            loopBegin.setIteration(Integer.valueOf(item.getAttribute("iteration")));
            loopBegin.setStart(Integer.valueOf(item.getAttribute("start")));
            idTask.put(loopBegin.getIdentificador(), loopBegin);
            for (int j = 0; j < item.getChildNodes().getLength(); j++) {
                try {
                    Element element = (Element) item.getChildNodes().item(j);
                    addElementLoop(element, loopEnd, vertices, arestas, idTask);
                } catch (Exception e) {
                    //System.err.println(e.getMessage());
                }
            }
            end.setPosition(end.getX(), loopEnd.getY() + DesenhoDAG.INTERVAL);
        } else if ("thread".equals(tipo)) {
            int x = end.getX() + DesenhoDAG.INTERVAL;
            int y = end.getY();
            gspd.ispd.gui.iconico.dag.Thread thBegin = new gspd.ispd.gui.iconico.dag.Thread(x + DesenhoDAG.INTERVAL, y - DesenhoDAG.INTERVAL);
            gspd.ispd.gui.iconico.dag.Thread thEnd = new gspd.ispd.gui.iconico.dag.Thread(x + DesenhoDAG.INTERVAL, y);
            gspd.ispd.gui.iconico.dag.Thread thp = new gspd.ispd.gui.iconico.dag.Thread(x, y, thBegin, thEnd);
            vertices.add(thp);
            vertices.add(thBegin);
            vertices.add(thEnd);
            thp.attachTo(end);
            for (int j = 0; j < item.getChildNodes().getLength(); j++) {
                try {
                    Element element = (Element) item.getChildNodes().item(j);
                    addElementLoop(element, thEnd, vertices, arestas, idTask);
                } catch (Exception e) {
                    //System.err.println(e.getMessage());
                }
            }
            end.setPosition(end.getX(), end.getY() + DesenhoDAG.INTERVAL);
        }
    }

    private static void addElementLoop(Element item, Block end, Set<Vertice> vertices, Set<Aresta> arestas, HashMap<String, Identificavel> idTask) {
        String tipo = item.getNodeName();
        if ("process".equals(tipo)) {
            ProcessingBlock block = new ProcessingBlock(end.getX() + DesenhoDAG.INTERVAL, end.getY());
            block.attachTo(end);
            vertices.add(block);
            block.setSizeMin(Double.valueOf(item.getAttribute("size_min")));
            block.setSizeMax(Double.valueOf(item.getAttribute("size_max")));
            end.setPosition(end.getX(), end.getY() + DesenhoDAG.INTERVAL);
        } else if ("receive".equals(tipo)) {
            Receive receive = new Receive(end.getX() + DesenhoDAG.INTERVAL, end.getY());
            receive.attachTo(end);
            vertices.add(receive);
            receive.setId(item.getAttribute("id"));
            end.setPosition(end.getX(), end.getY() + DesenhoDAG.INTERVAL);
            idTask.put(receive.getIdentificador(), receive);
        } else if ("send".equals(tipo)) {
            Send send = new Send(end.getX() + DesenhoDAG.INTERVAL, end.getY());
            send.attachTo(end);
            vertices.add(send);
            //add task:port:size
            String id = item.getAttribute("to");
            send.setDestino((Vertice) idTask.get(id));
            send.setSize(Double.valueOf(item.getAttribute("size")));
            end.setPosition(end.getX(), end.getY() + DesenhoDAG.INTERVAL);
        } else if ("loop".equals(tipo)) {
            Loop loopBegin = new Loop(end.getX() + DesenhoDAG.INTERVAL, end.getY());
            Loop loopEndt = new Loop(end.getX() + DesenhoDAG.INTERVAL, end.getY() + DesenhoDAG.INTERVAL, loopBegin);
            vertices.add(loopBegin);
            vertices.add(loopEndt);
            loopBegin.attachTo(end);
            loopBegin.setId(item.getAttribute("id"));
            loopBegin.setIteration(Integer.valueOf(item.getAttribute("iteration")));
            loopBegin.setStart(Integer.valueOf(item.getAttribute("start")));
            idTask.put(loopBegin.getIdentificador(), loopBegin);
            for (int j = 0; j < item.getChildNodes().getLength(); j++) {
                try {
                    Element element = (Element) item.getChildNodes().item(j);
                    addElementLoop(element, loopEndt, vertices, arestas, idTask);
                } catch (Exception e) {
                    //System.err.println(e.getMessage());
                }
            }
            end.setPosition(end.getX(), loopEndt.getY() + DesenhoDAG.INTERVAL);
        } else if ("thread".equals(tipo)) {
            int x = end.getX() + DesenhoDAG.INTERVAL;
            int y = end.getY();
            gspd.ispd.gui.iconico.dag.Thread thBegin = new gspd.ispd.gui.iconico.dag.Thread(x + DesenhoDAG.INTERVAL, y - DesenhoDAG.INTERVAL);
            gspd.ispd.gui.iconico.dag.Thread thEnd = new gspd.ispd.gui.iconico.dag.Thread(x + DesenhoDAG.INTERVAL, y);
            gspd.ispd.gui.iconico.dag.Thread thp = new gspd.ispd.gui.iconico.dag.Thread(x, y, thBegin, thEnd);
            vertices.add(thp);
            vertices.add(thBegin);
            vertices.add(thEnd);
            thp.attachTo(end);
            for (int j = 0; j < item.getChildNodes().getLength(); j++) {
                try {
                    Element element = (Element) item.getChildNodes().item(j);
                    addElementLoop(element, thEnd, vertices, arestas, idTask);
                } catch (Exception e) {
                    //System.err.println(e.getMessage());
                }
            }
            end.setPosition(end.getX(), end.getY() + DesenhoDAG.INTERVAL);
        }
    }

    /**
     * Realiza a leitura de um bloco de instruções e transforma ele em um
     * elemento de xml, para tal é feita a chamada do método para cada
     * implemetação de Block distinta
     *
     * @param block
     * @return
     */
    private Element newIcone(Block block) throws Exception {
        if (block instanceof gspd.ispd.gui.iconico.dag.Thread) {
            return newIcone((gspd.ispd.gui.iconico.dag.Thread) block);
        } else if (block instanceof Loop) {
            return newIcone((Loop) block);
        } else if (block instanceof Send) {
            String to = ((Identificavel) ((Send) block).getDestino()).getIdentificador();
            return newSend(to, null, ((Send) block).getSize());
        } else if (block instanceof Receive) {
            return newReceive(((Receive) block).getIdentificador(), null, null);
        } else if (block instanceof ProcessingBlock) {
            return newProcessing(((ProcessingBlock) block).getSizeMin(), ((ProcessingBlock) block).getSizeMax());
        }
        return null;
    }

    private Element newIcone(gspd.ispd.gui.iconico.dag.Thread tblock) throws Exception {
        Element thread = descricao.createElement("thread");
        Block itens = tblock.getBegin().getNext();
        if (tblock.getEnd().equals(itens)) {
            throw new Exception("Empty Thread!");
        }
        while (!itens.equals(tblock.getEnd())) {
            Element item = newIcone(itens);
            thread.appendChild(item);
            if (itens instanceof Loop) {
                itens = ((Loop) itens).getEnd();
            }
            itens = itens.getNext();
        }
        return thread;
    }

    private Element newIcone(Loop lblock) throws Exception {
        Element receive = descricao.createElement("loop");
        receive.setAttribute("id", lblock.getIdentificador());
        receive.setAttribute("iteration", lblock.getIteration().toString());
        receive.setAttribute("start", lblock.getStart().toString());
        Block itens = lblock.getNext();
        if (lblock.getEnd().equals(itens)) {
            throw new Exception("Empty Loop!");
        }
        do {
            Element item = newIcone(itens);
            receive.appendChild(item);
            if (itens instanceof Loop) {
                itens = ((Loop) itens).getEnd();
            }
            itens = itens.getNext();
        } while (!lblock.getEnd().equals(itens));
        return receive;
    }

    private Element newProcessing(Double min, Double max) {
        Element processing = descricao.createElement("process");
        processing.setAttribute("size_min", min.toString());
        processing.setAttribute("size_max", max.toString());
        return processing;
    }

    private Element newSend(String to, Integer port, Double size) throws Exception {
        Element send = descricao.createElement("send");
        if (to == null || "".equals(to)) {
            throw new Exception("Send without a destination!");
        }
        send.setAttribute("to", to);
        if (port != null) {
            send.setAttribute("port", port.toString());
        }
        if (size <= 0) {
            throw new Exception("Size has an invalid value!");
        }
        send.setAttribute("size", size.toString());
        return send;
    }

    private Element newReceive(String id, String from, Integer port) {
        Element receive = descricao.createElement("receive");
        if (id != null && !"".equals(id)) {
            receive.setAttribute("id", id);
        }
        if (from != null && !"".equals(from)) {
            receive.setAttribute("from", from);
        }
        if (port != null) {
            receive.setAttribute("port", port.toString());
        }
        return receive;
    }
}
