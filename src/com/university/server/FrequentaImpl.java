package com.university.server;

public class FrequentaImpl {
    //Ricerca corrispondenza utente corso
    //Ricerca

    /*
    //questo deve stare qui se no non si riesce a richiamare frequenta se lo mettiamo in corsi
    @Override
    public ArrayList<Corso> getCorsiDisponibili(int matricola) {
        try {
            createOrOpenDB();
            Corso[] tuttiCorsi = new Corso[map.size()];
            ArrayList<Corso> corsiDisponibili= new ArrayList<>();
            HashMap<String,Corso> corsi=new HashMap<>();
            for(Corso corso: tuttiCorsi){
                corsi.put(corso.nome,corso);
            }
            ArrayList<Frequenta> mieiCorsi=getIoFrequenta(matricola);
            for(Frequenta frequenta: mieiCorsi){
                if(!corsi.containsKey(frequenta.nomeCorso)){
                    corsiDisponibili.add(corsi.get(frequenta.nomeCorso));
                }
            }
            return corsiDisponibili;



        }catch (Exception e){
            System.out.println("Errore: "+ e);
            return null;
        }
    }*/
}
