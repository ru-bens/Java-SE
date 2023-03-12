/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.tecnico.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author Rubens
 */
public class ModuloConexao {
    
    public static Connection conexao(){
        
        java.sql.Connection conexao = null;
        
        String url = "jdbc:mysql://localhost:3306/dbinfox?characterEncoding=utf-8";
        String user = "dbuser";
        String pass ="";
        String driver = "com.mysql.cj.jdbc.Driver";
        
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url,user,pass);
            return conexao;
        } catch (ClassNotFoundException ex) {
            return null;
        } catch (SQLException ex) {
            return null;
        }
    }
    
}
