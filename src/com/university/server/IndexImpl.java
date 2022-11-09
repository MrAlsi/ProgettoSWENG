package com.university.server;

import com.university.client.services.IndexService;
import org.mapdb.DB;
import org.mapdb.DBMaker;

import javax.servlet.ServletContext;

public class IndexImpl extends Database implements IndexService {
    @Override
    public int[] getDataHP(){
        return new int[]{super.getCorsi().length,
                super.getDocenti().length,
                super.getStudenti().length};
    }



/*
    private DB getDb(String nomeDB) {
        ServletContext context = this.getServletContext();
        synchronized (context) {
            DB db = (DB)context.getAttribute("DB");
            if(db == null){
                System.out.println("Sono qui");
                db = DBMaker.fileDB(nomeDB).closeOnJvmShutdown().make();
                context.setAttribute("DB", db);
            }
            return db;
        }
    }*/
}
