package com.university.server;

import com.university.client.services.IndexService;
public class IndexImpl extends Database implements IndexService {
    @Override
    public int[] getDataHP(){
        return new int[]{super.getCorsi().length,
                        super.getDocenti().length,
                        super.getStudenti().length};
    }


}
