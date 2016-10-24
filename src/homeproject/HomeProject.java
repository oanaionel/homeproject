/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homeproject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JFrame;

/**
 *
 * @author Oana Ionel
 */
public class HomeProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException {

        AdministrareProdus frame = new AdministrareProdus();
        frame.setVisible(true);

        List<Produs> produse = new ArrayList<>();
        Produs p1 = new Produs();
        p1.setNumeProdus("Ipad Mini 2");
        p1.setCuloare("rosu");
        p1.setPeStoc(true);
        p1.setPretProdus(999.99);
        p1.setDataExpirare(new SimpleDateFormat("yyyy-dd-MM").parse("2020-31-10"));

        TipProdus tp1 = new TipProdus();
        tp1.setNumeTipProdus("Tableta");
        tp1.setDescriere("Lorem ipsum dolor sit amet, consectetuer adipiscing elit");
        p1.setTp(tp1);

        Produs p2 = new Produs();
        p2.setNumeProdus("Lenovo Mix 310");
        p2.setCuloare("negru");
        p2.setPeStoc(false);
        p2.setPretProdus(9999.99);
        p2.setDataExpirare(new SimpleDateFormat("yyyy-dd-MM").parse("2030-31-10"));

        TipProdus tp2 = new TipProdus();
        tp2.setNumeTipProdus("Laptop");
        tp2.setDescriere("Aliquam tincidunt mauris eu risus");
        p2.setTp(tp2);

        Produs p3 = new Produs();
        p3.setNumeProdus("Smart Samsung");
        p3.setCuloare("gri");
        p3.setPeStoc(true);
        p3.setPretProdus(2999.99);
        p3.setDataExpirare(new SimpleDateFormat("yyyy-dd-MM").parse("2019-19-11"));

        TipProdus tp3 = new TipProdus();
        tp3.setNumeTipProdus("Televizor");
        tp3.setDescriere("Vestibulum auctor dapibus neque");
        p3.setTp(tp3);

        Produs p4 = new Produs();
        p4.setNumeProdus(" LED Smart Android Philips");
        p4.setCuloare("alb");
        p4.setPeStoc(true);
        p4.setPretProdus(3999.99);
        p4.setDataExpirare(new SimpleDateFormat("yyyy-dd-MM").parse("2021-19-11"));

        TipProdus tp4 = new TipProdus();
        tp4.setNumeTipProdus("Televizor");
        tp4.setDescriere("Vestibulum auctor dapibus neque");
        p4.setTp(tp4);

        Produs p5 = new Produs();
        p5.setNumeProdus("Artic Star");
        p5.setCuloare("alb");
        p5.setPeStoc(false);
        p5.setPretProdus(1499.99);
        p5.setDataExpirare(new SimpleDateFormat("yyyy-dd-MM").parse("2027-16-11"));

        TipProdus tp5 = new TipProdus();
        tp5.setNumeTipProdus("Frigider");
        tp5.setDescriere("Pellentesque habitant morbi tristique");
        p5.setTp(tp5);

        Produs p6 = new Produs();
        p6.setNumeProdus("Beko Start Light");
        p6.setCuloare("verde");
        p6.setPeStoc(true);
        p6.setPretProdus(1399.99);
        p6.setDataExpirare(new SimpleDateFormat("yyyy-dd-MM").parse("2033-12-11"));

        TipProdus tp6 = new TipProdus();
        tp6.setNumeTipProdus("Aragaz");
        tp6.setDescriere("Quisque sit amet est et sapien ullamcorper pharetra.");
        p6.setTp(tp6);

        produse.add(p1);
        produse.add(p2);
        produse.add(p3);
        produse.add(p4);
        produse.add(p5);
        produse.add(p6);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("HomeProjectPU");
        EntityManager em = emf.createEntityManager();
        Iterator it = produse.iterator();
        while (it.hasNext()) {
            em.getTransaction().begin();
            em.persist(it.next());
            em.getTransaction().commit();
        }
        em.close();
    }

}
