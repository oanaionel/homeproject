/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homeproject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Oana Ionel
 */
@Entity
public class Produs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String numeProdus;
    private double pretProdus;
    private String culoare;
    private boolean peStoc;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataExpirare;
  
    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})

    private TipProdus tp;


    public Date getDataExpirare() {
        return dataExpirare;
    }

    public void setDataExpirare(Date dataExpirare) {
        this.dataExpirare = dataExpirare;
    }

    public String getNumeProdus() {
        return numeProdus;
    }

    public void setNumeProdus(String numeProdus) {
        this.numeProdus = numeProdus;
    }

    public double getPretProdus() {
        return pretProdus;
    }

    public void setPretProdus(double pretProdus) {
        this.pretProdus = pretProdus;
    }

    public String getCuloare() {
        return culoare;
    }

    public void setCuloare(String culoare) {
        this.culoare = culoare;
    }

    public boolean isPeStoc() {
        return peStoc;
    }

    public void setPeStoc(boolean peStoc) {
        this.peStoc = peStoc;
    }

    public TipProdus getTp() {
        return tp;
    }

    public void setTp(TipProdus tp) {
        this.tp = tp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Produs)) {
            return false;
        }
        Produs other = (Produs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "homeproject.Produs[ id=" + id + " ]" + numeProdus+ " "+ dataExpirare+ "   "+ pretProdus+ "   "+tp.getNumeTipProdus()+ "  "+ tp.getDescriere() ;
    }


}
