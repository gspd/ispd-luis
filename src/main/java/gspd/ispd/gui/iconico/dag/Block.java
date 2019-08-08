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
 * Block.java
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
import java.awt.Graphics;

/**
 * Classe base para criação dos blocos de instruções que podem ser adicionados as tarefas
 * @author denison
 */
public abstract class Block extends Vertice {

    protected Block previous;
    protected Block next;
    protected Boolean visible;

    public Block(Integer x, Integer y) {
        super(x, y);
        visible = Boolean.TRUE;
    }

    public void setPrevious(Block previous) {
        this.previous = previous;
    }

    public void setNext(Block next) {
        this.next = next;
    }

    public Block getPrevious() {
        return previous;
    }

    public Block getNext() {
        return next;
    }

    public void attachTo(Block block) {
        //verifica se o bloco está em uma lista
        if (block.getPrevious() != null && block.getNext() != null) {
            remove();
            next = block;
            previous = block.getPrevious();
            previous.setNext(this);
            next.setPrevious(this);
        }
    }

    public void remove() {
        if (previous != null && next != null) {
            previous.setNext(next);
            next.setPrevious(previous);
        }
    }

    public void drawBlock(Graphics g) {
        if (next != null && next.getX() != null) {
            g.drawLine(this.getX(), this.getY(), next.getX(), next.getY());
            next.drawBlock(g);
        }
    }

    public void setVisible(Boolean visible, int difX, int difY) {
        this.visible = visible;
        if (visible) {
            this.setPosition(this.getX() - difX, this.getY() - difY);
        }
    }

    @Override
    public boolean contains(int x, int y) {
        if (x < getX() + 17 && x > getX() - 17) {
            if (y < getY() + 17 && y > getY() - 17) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Integer getX() {
        if (visible) {
            return super.getX();
        } else {
            return -20;
        }
    }

    @Override
    public Integer getY() {
        if (visible) {
            return super.getY();
        } else {
            return -20;
        }
    }

    public void attachTo(Task task) {
        if (task.getLine().getOrigem().equals(task)) {
            //primeiro
            Block first = task.getLine().getFirst();
            remove();
            first.getNext().setPrevious(this);
            this.setNext(first.getNext());
            first.setNext(this);
            this.setPrevious(first);
        } else if (task.getLine().getDestino().equals(task)) {
            //ultimo
            Block last = task.getLine().getLast();
            remove();
            last.getPrevious().setNext(this);
            this.setPrevious(last.getPrevious());
            last.setPrevious(this);
            this.setNext(last);
        }
    }
}
