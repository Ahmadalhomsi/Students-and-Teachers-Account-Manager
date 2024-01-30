/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Base;

import com.formdev.flatlaf.FlatDarculaLaf;
import java.text.ParseException;
import java.util.*;
import javax.swing.UIManager;


/**
 *
 * @author pooqw
 */
public class Main {

    public static void main(String[] args) throws ParseException {

    // Look and feel  
    
        try {
    UIManager.setLookAndFeel( new FlatDarculaLaf()); //FlatMacDarkLaf() FlatDarculaLaf()
    } catch( Exception ex ) {
    System.err.println( "Failed to initialize LaF" );
    }
        
    //-------

        GUI gui = new GUI();
        gui.setVisible(true);

  
    }

}
