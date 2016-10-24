/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homeproject;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NonUniqueResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Oana Ionel
 */
public class ProdusDAO {

    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("HomeProjectPU");

    // retrieve all Products whith Product Types as well
    public List<Produs> retrieveProducts() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Produs> q = em.createQuery("SELECT p FROM Produs p ", Produs.class);
        @SuppressWarnings("unchecked")
        List<Produs> produse = q.getResultList();
        em.getTransaction().commit();
        em.close();
        return produse;
    }

    // retrieve all distinct product names
    public List<String> retrieveProductsName() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query q = em.createQuery("SELECT DISTINCT p.numeProdus FROM Produs p");
        @SuppressWarnings("unchecked")
        List<String> produse = q.getResultList();
        for (String p : produse) {
            System.out.println(p);
        }
        em.close();
        return produse;
    }
    
    // add a product type in database
    public void addProductType(String productType, String description) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        TipProdus tp = new TipProdus();
        tp.setNumeTipProdus(productType);
        tp.setDescriere(description);
        em.persist(tp);
        em.getTransaction().commit();
        em.close();
    }
    // check if other product types exist
    public boolean checkProductType(String numeTipProdus) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query q = em.createQuery("SELECT p.numeTipProdus FROM TipProdus p WHERE p.numeTipProdus = :value");
        q.setParameter("value", numeTipProdus);
        @SuppressWarnings("unchecked")
        List<Long> results = q.getResultList();
        System.out.print("result size list: " + results.size());
        if (results.isEmpty()) {
            em.close();
            return false;
        } else {
            em.close();
            return true;
        }
    }
    // retrieve all distinct product types
    public List<String> retrieveProductType() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query q = em.createQuery("SELECT DISTINCT p.numeTipProdus FROM TipProdus p");
        @SuppressWarnings("unchecked")
        List<String> results = q.getResultList();
        em.close();
        return results;
    }
    
    // advanced search 
    public List<Produs> advancedSearchProducts(String name, Double price,
            PriceCompare priceCompare, String color, boolean inStock,
            Date date1, Date date2, String cb) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        String queryText = "SELECT p FROM Produs p ";
        boolean isFirstCondition = true;
// name search
        if (name != null && name.length() > 0) {
            queryText += " WHERE p.numeProdus=\"" + name + "\"";
            isFirstCondition = false;
        }
//price search
        if (price != null) {
            String compareSign;
            switch (priceCompare) {
                case LESS:
                    compareSign = "<";
                    break;
                case EQUAL:
                    compareSign = "=";
                    break;
                case MORE:
                    compareSign = ">";
                    break;
                default:
                    compareSign = "=";
            }
            if (isFirstCondition) {
                queryText += " WHERE ";
            } else {
                queryText += " AND ";
            }
            queryText += "p.pretProdus " + compareSign + Double.toString(price);
            isFirstCondition = false;
        }
// color search
        if (color != null && color.length() > 0) {
            if (isFirstCondition) {
                queryText += " WHERE ";
            } else {
                queryText += " AND ";
            }
            queryText += "p.culoare = \"" + color + "\"";
            isFirstCondition = false;
        }
// in stock search
        if (inStock) {
            if (isFirstCondition) {
                queryText += " WHERE ";
            } else {
                queryText += " AND ";
            }
            queryText += "p.peStoc='" + inStock + "' ";
            isFirstCondition = false;
        }
// date search
        if (date1 != null && date2 != null) {
            SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-dd-MM");

            if (date2.before(date1)) {
                Date temp = date1;
                date1 = date2;
                date2 = temp;
            }

            if (isFirstCondition) {
                queryText += " WHERE ";
            } else {
                queryText += " AND ";
            }
            queryText += "p.dataExpirare BETWEEN '" + myFormat.format(date1) + "' AND '" + myFormat.format(date2) + "'";
            isFirstCondition = false;
        }
// product type search
        if (cb != null) {
            if (!cb.equals("Any")) {
                if (isFirstCondition) {
                    queryText += " WHERE ";
                } else {
                    queryText += " AND ";
                }
                queryText += "p.tp.numeTipProdus='" + cb + "'";
                isFirstCondition = false;
            }
        }

        System.out.println(queryText);
        TypedQuery<Produs> q = em.createQuery(queryText, Produs.class);
        @SuppressWarnings("unchecked")
        List<Produs> produse = q.getResultList();
        em.getTransaction().commit();
        em.close();
        return produse;
    }
    // retrieve product id knowing the price, name, color, inStock
    public Long retrieveIdProduct(Produs prod) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query q = em.createQuery("SELECT p.id FROM Produs p WHERE p.numeProdus ='" + prod.getNumeProdus()
                + "'AND p.pretProdus='" + prod.getPretProdus()
                + "' AND p.culoare='" + prod.getCuloare()
                + "' AND p.peStoc ='" + prod.isPeStoc() + "'");
        @SuppressWarnings("unchecked")
        List<Long> results = q.getResultList();
        em.close();
        if (results.isEmpty()) {
            return null;
        } else {
            System.out.println(results.get(0));
            return results.get(0);
        }
    }
    // erase product from databse knowing the id
    public void removeProductById(Long id) {
        //Find managed Entity reference
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        int isSuccessful = em.createQuery("delete from Produs p where p.id=:id")
                .setParameter("id", id)
                .executeUpdate();
        if (isSuccessful == 0) {
            throw new OptimisticLockException(" product modified concurrently");
        }
        em.getTransaction().commit();
        em.close();
    }
    // retrieve product type object knowing the name
    public TipProdus retrievePTDetails(String numeTP) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<TipProdus> q = (TypedQuery<TipProdus>) em.createQuery("SELECT p FROM TipProdus p WHERE p.numeTipProdus='" + numeTP + "'");
        @SuppressWarnings("unchecked")
        List<TipProdus> results = q.getResultList();
        em.close();
        if (results.isEmpty()) {
            return null;
        } else {
            System.out.println(results.get(0));
            return results.get(0);
        }
    }
    // update product type
    public void updatePTDetails(String oldName, String newName, String description) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        int isSuccessful = em.createQuery("UPDATE TipProdus p SET p.numeTipProdus='" + newName
                + "', p.descriere='" + description
                + "' WHERE p.numeTipProdus='" + oldName + "'")
                .executeUpdate();
        if (isSuccessful == 0) {
            throw new OptimisticLockException(" product modified concurrently");
        }
        em.getTransaction().commit();
        em.close();
    }
    // delete product type
    public void deleteProductType(String name) {
        //Find managed Entity reference
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        int isSuccessful = em.createQuery("delete from TipProdus p where p.numeTipProdus='" + name + "'")
                .executeUpdate();
        if (isSuccessful == 0) {
            throw new OptimisticLockException(" product modified concurrently");
        }
        em.getTransaction().commit();
        em.close();
    }
    // remove product 

}
