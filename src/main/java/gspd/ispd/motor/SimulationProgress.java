/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gspd.ispd.motor;

import InterpretadorInterno.ModeloIconico.InterpretadorIconico;
import InterpretadorInterno.ModeloSimulavel.InterpretadorSimulavel;
import gspd.ispd.arquivo.xml.IconicoXML;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;

/**
 * Middleware between model from user interface and simulation motor
 * 
 * @author Denison
 * @author Luis
 */
public abstract class SimulationProgress {

    public void println(String text, Color cor) {
        this.print(text + "\n", cor);
    }

    public void println(String text) {
        this.println(text, Color.BLACK);
    }

    public void print(String text) {
        this.print(text, Color.black);
    }

    public abstract void print(String text, Color cor);
    
    public abstract void incProgresso(int n);

    /**
     * Escreve os arquivos com os modelos icônicos e simuláveis, e realiza a
     * analise e validação dos mesmos
     *
     * @param ModeloIconico Texto contendo o modelo icônico que será analisado
     */
    public void AnalisarModelos(String ModeloIconico) {
        //escreve modelo iconico
        this.print("Writing iconic model.");
        this.print(" -> ");
        File arquivo = new File("modeloiconico");
        try {
            FileWriter writer = new FileWriter(arquivo);
            PrintWriter saida = new PrintWriter(writer, true);
            saida.print(ModeloIconico);
            saida.close();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(SimulationProgress.class.getName()).log(Level.SEVERE, null, ex);
        }
        incProgresso(5);//[5%] --> 10%
        this.println("OK", Color.green);
        //interpreta modelo iconico
        this.print("Interpreting iconic model.");
        this.print(" -> ");
        InterpretadorIconico parser = new InterpretadorIconico();
        parser.leArquivo(arquivo);
        incProgresso(5);//[5%] --> 15%
        this.println("OK", Color.green);
        this.print("Writing simulation model.");
        this.print(" -> ");
        parser.escreveArquivo();
        incProgresso(5);//[5%] --> 20%
        this.println("OK", Color.green);
        this.print("Interpreting simulation model.");
        this.print(" -> ");
        InterpretadorSimulavel parser2 = new InterpretadorSimulavel();
        PrintStream stdout = System.out;
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        parser2.leArquivo(new File("modelosimulavel"));
        System.setOut(stdout);
        incProgresso(5);//[5%] --> 25%
        this.println("OK", Color.green);
    }

    public void validarInicioSimulacao(Document modelo) throws IllegalArgumentException {
        this.print("Verifying configuration of the icons.");
        this.print(" -> ");
        if (modelo == null) {
            this.println("Error!", Color.red);
            throw new IllegalArgumentException("The model has no icons.");
        }
        try {
            IconicoXML.validarModelo(modelo);
        } catch (IllegalArgumentException e) {
            this.println("Error!", Color.red);
            throw e;
        }
        this.incProgresso(5);
        this.println("OK", Color.green);
    }
}
