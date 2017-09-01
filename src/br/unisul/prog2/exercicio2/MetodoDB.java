/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisul.prog2.exercicio2;

import java.sql.Connection;
import java.sql.Statement;

/**
 *
 * @author gabriel.gomes2
 */
public class MetodoDB {
    public static void Delete(int numero) {
        Connection conn = null;
        Statement st = null;

        try {
            conn = DatabaseService.getConnPostgres();
            st = conn.createStatement();
            st.execute("DELETE FROM resultadosmegasena WHERE numero = " + numero);
            
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
            }
        }
    }
    
    public static void Inserir(int jogo, int dezena, int numero){
        Connection conn = null;
        Statement st = null;

        try {
            conn = DatabaseService.getConnPostgres();
            st = conn.createStatement();
            st.execute("INSERT INTO resultadosmegasena (jogo, dezena, numero) values ('"+ jogo +"', '"+ dezena +"', '"+ numero +"')");
            
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
            }
        }
    }
}
