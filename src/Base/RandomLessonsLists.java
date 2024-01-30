/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Base;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xandros
 */
public class RandomLessonsLists {
    
    List<String> dersIsimleri;
    List<String> dersKodlari;
    List<String> dersNotlari;

    public RandomLessonsLists(List<String> dersIsimleri, List<String> dersKodlari, List<String> dersNotlari) {
        this.dersIsimleri = dersIsimleri;
        this.dersKodlari = dersKodlari;
        this.dersNotlari = dersNotlari;
    }
    

    public List<String> getDersIsimleri() {
        return dersIsimleri;
    }

    public void setDersIsimleri(List<String> dersIsimleri) {
        this.dersIsimleri = dersIsimleri;
    }

    public List<String> getDersKodlari() {
        return dersKodlari;
    }

    public void setDersKodlari(List<String> dersKodlari) {
        this.dersKodlari = dersKodlari;
    }

    public List<String> getDersNotlari() {
        return dersNotlari;
    }

    public void setDersNotlari(List<String> dersNotlari) {
        this.dersNotlari = dersNotlari;
    }

    
    
    
    
}
