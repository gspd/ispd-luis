/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gspd.ispd;

import gspd.ispd.gui.JPrincipal;
import gspd.ispd.gui.LogExceptions;
import gspd.ispd.gui.SplashWindow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.net.URL;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 * Classe de inicialização do iSPD.
 * Indetifica se deve executar comando a partir do termila ou carrega interface gráfica
 * @author denison
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // Load iSPD configurations
            IspdProperties.load();
            // Set locale for running program
            Locale.setDefault(IspdProperties.getLocale());
            if (args.length > 0) {
                Terminal tel = new Terminal(args);
                tel.executar();
                System.exit(0);
            } else {
                // Configure loading program window
                ImageIcon image = new ImageIcon(Main.class.getResource("/images/Splash.gif"));
                SplashWindow window = new SplashWindow(image);
                window.setText("Copyright (c) 2010 - 2014 GSPD.  All rights reserved.");
                // Show loading program window
                window.setVisible(true);
                // Configure default log for carry exceptions during the program execution
                LogExceptions logExceptions = new LogExceptions(null);
                Thread.setDefaultUncaughtExceptionHandler(logExceptions);
                // Errors files for simulator
                // Get the error file paths
                // TODO: if a directory does not exists, then create it
                // NOTE: Currently, it assumes that path described in ispd.properties(directories.default) exists
                File simulatorErrorFilePath = new File(IspdProperties.getWorkingDirectory(), "Erros_Simulador");
                File outputSimulatorErrorFilePath = new File(IspdProperties.getWorkingDirectory(), "Saida_Simulador");
                // Error files
                FileOutputStream fosErr = new FileOutputStream(simulatorErrorFilePath);
                FileOutputStream fosOut = new FileOutputStream(outputSimulatorErrorFilePath);
                // Errors streams for simulator
                PrintStream psErr = new PrintStream(fosErr);
                PrintStream psOut = new PrintStream(fosOut);
                // *** (need atention) ****
                // redefine os fluxos na classe System
                // System.setErr(psErr);
                // System.setOut(psOut);
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                // Starts Graphical User Interface
                JPrincipal gui = new JPrincipal();
                gui.setLocationRelativeTo(null);
                gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                logExceptions.setParentComponent(gui);
                window.dispose();
                gui.setVisible(true);
            }
        } catch (FileNotFoundException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "File not Found", e);
        } catch (NullPointerException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Object not defined", e);
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Something wrong has occurred while starting iSPD", e);
        }
    }
}