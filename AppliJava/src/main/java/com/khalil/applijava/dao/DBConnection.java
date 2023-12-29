package com.khalil.applijava.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBConnection {
    // Pour la connexion
    private Connection cnx;
    private PreparedStatement pstm;
    //Pour les requetes de consultation (SELECT)
    private ResultSet rs;
    //Pour les requetes de mise en jour(INSERT INTO, UPDATE, DELETE)
    private int ok;

    // Methode d'ouverture de la connexion
    public Connection getConnection()
    {
        //Parametres de connexion
        String host = "localhost";
        String database = "applijava_db";
        String url = "jdbc:mysql://"+host+":3306/"+database;

        //String url = "jdbc:mysql://localhost:3306/user_jdbc_db_2023";
        String user = "root";
        String password = "";
        try {
            //Chargement du pilote
            Class.forName("com.mysql.jdbc.Driver");
            //Ouverture de la connexion
            cnx = DriverManager.getConnection(url, user,password);
            //System.out.println("Connexion Reussie !");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return cnx;
    }

    public void initPrepar(String sql){
        try {
            getConnection();
            pstm = cnx.prepareStatement(sql);
        }catch (Exception e){
            e. printStackTrace();
        }
    }

    public ResultSet executeSelect()
    {
        rs = null;
        try {
            rs = pstm.executeQuery();
        }catch (Exception e){
            e. printStackTrace();
        }
        return rs;
    }

    public int executeMaj()
    {
        try{
        ok = pstm.executeUpdate();
    }catch(Exception e) {
            e.printStackTrace();
        }
       return ok;
    }

    public void closeConnection()
    {
        try {
            if (cnx != null)
                cnx.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PreparedStatement getPstm(){
        return pstm;
    }

}
