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
 * Loop.java
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

import gspd.ispd.gui.iconico.Vertice;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Bloco que representa laço de repetição
 * @author denison
 */
public class Loop extends Block implements Identificavel {

    private static JTextField jValueId;
    private static JTextField jiteration;
    private static JTextField jstart;
    private static JPanel jpanel;

    public static void edit(Component parent, Loop icon) {
        if (jpanel == null) {
            jValueId = new JTextField();
            jValueId.setBorder(javax.swing.BorderFactory.createTitledBorder("Identifier"));
            jiteration = new JTextField();
            jiteration.setBorder(javax.swing.BorderFactory.createTitledBorder("Iteration"));
            jstart = new JTextField();
            jstart.setBorder(javax.swing.BorderFactory.createTitledBorder("Initial value"));
            jpanel = new JPanel();
            jpanel.setLayout(new GridLayout(3, 1));
            jpanel.add(jValueId);
            jpanel.add(jstart);
            jpanel.add(jiteration);
        }
        jValueId.setText(icon.begin.id);
        jiteration.setText(icon.begin.getIteration().toString());
        jstart.setText(icon.begin.getStart().toString());
        int opcao = JOptionPane.showConfirmDialog(
                parent,
                jpanel,
                "Set the port",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        //Caso ok o send é preenchido
        if (opcao == JOptionPane.OK_OPTION) {
            Integer iteracao = icon.begin.getIteration();
            Integer inicial = icon.begin.getStart();
            try {
                iteracao = Integer.valueOf(jiteration.getText());
                inicial = Integer.valueOf(jstart.getText());
            } catch (Exception e) {
            }
            icon.begin.id = jValueId.getText();
            icon.begin.setIteration(iteracao);
            icon.begin.setStart(inicial);
        }
    }
    private Loop begin = this;
    private Loop end = this;
    private String id = "loop_i";
    private Integer start = 0;
    private Integer iteration = 2;
    
    public Loop(Integer x, Integer y) {
        super(x, y);
    }

    public Loop(Integer x, Integer y, Loop begin) {
        super(x, y);
        begin.end = this;
        this.begin = begin;
        begin.next = end;
        end.previous = begin;
    }

    @Override
    public void draw(Graphics g) {
        int[] send_x;
        int[] send_y;
        String texto = "";
        g.setColor(Color.BLUE);
        if (begin.equals(this)) {
            send_x = new int[]{getX() - 17, getX() + 17, getX() - 17};
            send_y = new int[]{getY() - 17, getY() - 17, getY() + 17};
            g.drawLine(begin.getX() - 17, begin.getY() + 17, end.getX() - 17, end.getY() - 17);
            texto = start + " to " + (start + iteration - 1);
        } else {
            send_x = new int[]{getX() - 17, getX() - 17, getX() + 17};
            send_y = new int[]{getY() - 17, getY() + 17, getY() + 17};
        }
        g.fillPolygon(send_x, send_y, 3);
        g.setColor(Color.BLACK);
        g.drawPolygon(send_x, send_y, 3);
        g.drawString(texto, getX() - 25, getY() - 20);
    }

    public void destroy(Set<Vertice> vertices) {
        remove();
        Block temp = begin.getNext();
        while (temp != end) {
            if (temp instanceof Thread) {
                ((Thread) temp).destroy(vertices);
            } else {
                vertices.remove(temp);
            }
            temp = temp.getNext();
        }
        vertices.remove(begin);
        vertices.remove(end);
    }

    @Override
    public void remove() {
        if (begin.previous != null && end.next != null) {
            begin.previous.setNext(end.next);
            end.next.setPrevious(begin.previous);
        }
    }

    @Override
    public void attachTo(Block block) {
        //verifica se o bloco está em uma lista
        if (block.getPrevious() != null && block.getNext() != null) {
            if (begin.equals(this)) {
                remove();
                end.next = block;
                begin.previous = block.getPrevious();
                begin.previous.setNext(begin);
                end.next.setPrevious(end);
            }
        }
    }

    @Override
    public void attachTo(Task task) {
        if (task.getLine().getOrigem().equals(task)) {
            //primeiro
            Block first = task.getLine().getFirst();
            remove();
            first.getNext().setPrevious(end);
            end.setNext(first.getNext());
            first.setNext(begin);
            begin.setPrevious(first);
        } else if (task.getLine().getDestino().equals(task)) {
            //ultimo
            Block last = task.getLine().getLast();
            remove();
            last.getPrevious().setNext(begin);
            begin.setPrevious(last.getPrevious());
            last.setPrevious(end);
            end.setNext(last);
        }
    }

    public Loop getEnd() {
        return end;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public Integer getIteration() {
        return iteration;
    }

    public void setIteration(Integer iteration) {
        this.iteration = iteration;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public String getIdentificador() {
        return id;
    }
    
    @Override
    public ArrayList<Message> getEntradas() {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Message> getSaidas() {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isConectadoIndiretamente(Vertice Origem) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public boolean isConectado(Vertice Destino) {
        throw new UnsupportedOperationException("Not supported.");
    }
}
