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
 * Line.java
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

import gspd.ispd.gui.iconico.Aresta;
import gspd.ispd.gui.iconico.Vertice;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Set;

/**
 * Linha de execução de uma tarefa, contendo uma lista com todos os blocos adicionados
 * @author denison
 */
public class Line extends Aresta {

    private ProcessingBlock first;
    private ProcessingBlock last;
    private Boolean visible;

    public Line(Task origem, Task destino) {
        super(origem, destino);
        origem.setLine(this);
        destino.setLine(this);
        visible = Boolean.TRUE;
        first = new ProcessingBlock(null, null);
        last = new ProcessingBlock(null, null);
        first.setNext(last);
        last.setPrevious(first);
    }

    @Override
    public boolean isSelected() {
        return false;
    }

    @Override
    public void setSelected(boolean selected) {
    }

    @Override
    public void draw(Graphics g) {
        if (visible) {
            g.setColor(Color.blue);
            if (first.getNext() != last) {
                g.drawLine(getOrigem().getX(), getOrigem().getY(), first.getNext().getX(), first.getNext().getY());
                first.getNext().drawBlock(g);
                g.drawLine(last.getPrevious().getX(), last.getPrevious().getY(), getDestino().getX(), getDestino().getY());
            } else {
                g.drawLine(getOrigem().getX(), getOrigem().getY(), getDestino().getX(), getDestino().getY());
            }
        }
    }

    @Override
    public boolean contains(int x, int y) {
        return false;
    }

    public void setVisible(Boolean visible, int difX, int difY) {
        this.visible = visible;
        Block temp = first.getNext();
        while (temp != null && temp.getX() != null) {
            temp.setVisible(visible, difX, difY);
            temp = temp.getNext();
        }
    }

    public Boolean isVisible() {
        return visible;
    }

    public ProcessingBlock getFirst() {
        return first;
    }

    public ProcessingBlock getLast() {
        return last;
    }

    public void destroy(Set<Vertice> vertices) {
        Block temp = first.getNext();
        while (temp != null && temp.getX() != null) {
            if(temp instanceof Thread) {
                ((Thread)temp).destroy(vertices);
            } else {
                vertices.remove(temp);
            }
            temp = temp.getNext();
        }
    }
}
