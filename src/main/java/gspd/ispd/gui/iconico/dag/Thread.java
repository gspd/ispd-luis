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
 * Thread.java
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
import java.awt.Graphics;
import java.util.Set;

/**
 * Block que representa uma linha de execução nova
 * Esperimental: ainda não está implementado no motor
 * @author denison
 */
public class Thread extends Block {

    private Thread begin = this;
    private Thread end = this;
    private Thread main = this;

    public Thread(int x, int y) {
        super(x, y);
    }

    public Thread(int x, int y, Thread begin, Thread end) {
        super(x, y);
        //Indicando quem exerce cada função
        this.begin = begin;
        this.end = end;
        begin.main = this;
        begin.end = end;
        end.main = this;
        end.begin = begin;
        //Realiza as conecções
        begin.next = end;
        end.previous = begin;
        end.next = main;
        
    }

    @Override
    public void draw(Graphics g) {
        if (main.equals(this)) {
            g.setColor(Color.GRAY);
            g.fillOval(getX() - 12, getY() - 12, 25, 25);
            g.drawLine(main.getX(), main.getY(), begin.getX(), begin.getY());
            g.drawLine(main.getX(), main.getY(), end.getX(), end.getY());
        } else {
            g.setColor(Color.LIGHT_GRAY);
            g.fillOval(getX() - 9, getY() - 17, 17, 17);
            g.fillOval(getX() - 9, getY(), 17, 17);
            g.setColor(Color.BLACK);
            g.drawOval(getX() - 19, getY() - 17, 37, 34);
        }
    }
    
    @Override
    public void drawBlock(Graphics g) {
        if (main.equals(this)) {
            g.drawLine(this.getX(), this.getY(), begin.getX(), begin.getY());
            begin.drawBlock(g);
        } else if (next != null && next.getX() != null) {
            g.drawLine(this.getX(), this.getY(), next.getX(), next.getY());
            if(!next.equals(main)) {
                next.drawBlock(g);
            } else if(main.next != null && main.next.getX() != null) {
                g.drawLine(main.getX(), main.getY(), main.next.getX(), main.next.getY());
                main.next.drawBlock(g);
            }
        }
    }
    
    @Override
    public void setVisible(Boolean visible, int difX, int difY) {
        main.visible = visible;
        begin.visible = visible;
        end.visible = visible;
        if (visible) {
            main.setPosition(main.getX() - difX, main.getY() - difY);
            begin.setPosition(begin.getX() - difX, begin.getY() - difY);
            end.setPosition(end.getX() - difX, end.getY() - difY);
        }
        Block temp = begin.getNext();
        while (!temp.equals(end)) {
            temp.setVisible(visible, difX, difY);
            temp = temp.getNext();
        }
    }
    
    @Override
    public void remove() {
        if (main.previous != null && main.next != null) {
            main.previous.setNext(main.next);
            main.next.setPrevious(main.previous);
        }
    }
    
    @Override
    public void attachTo(Block block) {
        //verifica se o bloco está em uma lista
        if (block.getPrevious() != null && block.getNext() != null) {
            remove();
            main.next = block;
            main.previous = block.getPrevious();
            main.previous.setNext(main);
            main.next.setPrevious(main);
        }
    }
    
    @Override
    public void attachTo(Task task) {
        if (task.getLine().getOrigem().equals(task)) {
            //primeiro
            Block first = task.getLine().getFirst();
            remove();
            first.getNext().setPrevious(main);
            main.setNext(first.getNext());
            first.setNext(main);
            main.setPrevious(first);
        } else if (task.getLine().getDestino().equals(task)) {
            //ultimo
            Block last = task.getLine().getLast();
            remove();
            last.getPrevious().setNext(main);
            main.setPrevious(last.getPrevious());
            last.setPrevious(main);
            main.setNext(last);
        }
    }

    public Block getBegin() {
        return begin;
    }

    public Block getEnd() {
        return end;
    }

    public void destroy(Set<Vertice> vertices) {
        remove();
        Block temp = begin.getNext();
        while (!temp.equals(end)) {
            if(temp instanceof Thread) {
                ((Thread)temp).destroy(vertices);
            } else {
                vertices.remove(temp);
            }
            temp = temp.getNext();
        }
        vertices.remove(begin);
        vertices.remove(end);
        vertices.remove(main);
    }
}
