/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisul.prog2.exercicio2;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author gabriel.gomes2
 */
public class DatabaseService {
   public static Connection getConnPostgres() {
        Connection conn = null;
        
        try {
           Class.forName("org.postgresql.Driver");
           conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", 
                   "postgres", "postgres");
       } catch (Exception e) {
            System.err.println(e);
       }
        return conn;
   } 
}
