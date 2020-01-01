package com.example.sensitivebuying.dataObject;

import java.util.ArrayList;

public class SenstivieListFinal {


    public static final Sensitive eggs = new Sensitive("ביצים","0");
    public static final Sensitive peants = new Sensitive("בוטנים","1");
    public static final Sensitive gluten = new Sensitive("גלוטן","2");
    public static final Sensitive nuts = new Sensitive("אגוזים","3");
    public static final Sensitive soya = new Sensitive("סויה","4");
    public static final Sensitive lactose = new Sensitive("לקטוז","5");
    public static final Sensitive sesame = new Sensitive("שומשום","6");
    public static final Sensitive pine_nut = new Sensitive("צנובר","7");
    public static final Sensitive sinapis = new Sensitive("חרדל","8");
    public static final Sensitive celery = new Sensitive("סלרי","9");
    public static final Sensitive tonsils = new Sensitive("שקדים","10");

    public static  ArrayList<Sensitive> getSensitiveListFinal () {

         ArrayList<Sensitive> sensitives = new ArrayList<>();
        sensitives.add(eggs);
        sensitives.add(peants);
        sensitives.add(gluten);
        sensitives.add(nuts);
        sensitives.add(soya);
        sensitives.add(lactose);
        sensitives.add(sesame);
        sensitives.add(pine_nut);
        sensitives.add(sinapis);
        sensitives.add(celery);
        sensitives.add(tonsils);

return sensitives;


    }

    // protected listSensitive
}


