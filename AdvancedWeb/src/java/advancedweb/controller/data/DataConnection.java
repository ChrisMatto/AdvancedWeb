/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advancedweb.controller.data;

import advancedweb.model.classi.IgwDataLayerMysqlImpl;
import advancedweb.model.interfacce.IgwDataLayer;
import java.sql.SQLException;
import javax.naming.NamingException;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;



/**
 *
 * @author Chris-PC
 */


public class DataConnection {
    private BasicDataSource bds;
    private IgwDataLayer dl;
    
    public DataConnection(){
        if(dl==null){
            bds=new BasicDataSource();
            dl=null;
        }
    }
    
    public IgwDataLayer getData() throws SQLException, NamingException, DataLayerException{
        if(dl!=null)
            return dl;
        bds.setDriverClassName("com.mysql.jdbc.Driver");
        bds.setUrl("jdbc:mysql://localhost:3306/advancedweb");
        bds.setUsername("website");
        bds.setPassword("webpass");
        bds.setMaxIdle(5);
        bds.setMaxTotal(10);
        bds.setMaxWaitMillis(10000);
        dl=new IgwDataLayerMysqlImpl(bds);
        dl.init();
        return dl;
    }
        
    
}
