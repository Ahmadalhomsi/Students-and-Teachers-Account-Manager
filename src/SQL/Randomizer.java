/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;


/**
 *
 * @author xandros
 */
public class Randomizer {
    
        
    
        public ArrayList<Teacher> randomTeacherCreator(int uretimSayisi)
        {
        ArrayList<String> isimler = new ArrayList<>();
        String[] isimDizisi = {"Sinan", "Büşra", "Ali",
            "Emre", "Cemre", "Selim", "Gizem", "Burak",
            "Merve", "Selin", "Deniz", "Ceren", "Mustafa",
            "Esra", "Eren", "Tolga", "Zeynep", "Sibel",
            "Yasin", "Sefa", "Canan", "Hakan", "Gökçe",
            "Serkan", "Levent", "Nihal", "Şeyma", "Kemal"};
        for (String isim : isimDizisi) {
            isimler.add(isim);
        }

        ArrayList<String> soyadlar = new ArrayList<>();
        String[] soyadDizisi = {"Yakut", "Kara", "Demir",  "Yılmaz", "Demir", "Çelik", "Koç",
            "Arslan", "Aktaş", "Şahin", "Şen", "Yıldırım",
            "Erdem", "Yavuz", "Türk", "Sarı", "Acar",
            "Yaman", "Ateş", "Taşkın", "Turan", "Kurt",
            "Çetin", "Kurtuluş", "Kaya", "Avcı", "Durmaz",
            "Aslan", "Öztürk", "Eren", "Soydan", "Kılınç"};
        for (String soyad : soyadDizisi) {
            soyadlar.add(soyad);
        }

        ArrayList<String> ilgiAlanlari = new ArrayList<>();
        String[] ilgiAlani = {"Bilgisayar Ağları", "Veritabanı Yönetimi", "Yazılım Mühendisliği", "Veri Bilimi", "Bilgisayar Grafikleri",
        "Mobil Uygulama Geliştirme", "Oyun Programlama", "Büyük Veri Analizi", "Yapay Zeka", "Doğal Dil İşleme",
        "Bilgisayar Güvenliği", "Makine Öğrenimi", "Robotik", "Bulut Bilişim", "E-ticaret",
        "İnternet of Things (IoT)", "Web Geliştirme", "Veri Tabanı Tasarımı", "Eğitim Teknolojileri",
        "Bilgisayar Mimarisi", "Bilişim Hukuku", "E-Devlet Uygulamaları", "Mobil Oyun Geliştirme", "Sosyal Medya Analizi",
        "Görüntü İşleme", "Bilgisayar Destekli Tasarım (CAD)", "Veri Madenciliği", "Nesnelerin İnterneti (IoT)",
        "Ağ Güvenliği", "Büyük Veri Depolama ve Yönetimi"};
        
        
        for (String ilgi : ilgiAlani) {
            ilgiAlanlari.add(ilgi);
        }
           
           
           
        Random rastgele = new Random();
        
        ArrayList<Teacher> teacherArray= new ArrayList<Teacher>();

        for (int i = 0; i < uretimSayisi; i++) {
            String ad = isimler.get(rastgele.nextInt(isimler.size()));
            String soyad = soyadlar.get(rastgele.nextInt(soyadlar.size()));
            HashSet<String> secilenIlgiAlanlari = new HashSet<>();

            while (secilenIlgiAlanlari.size() < 5) {
                int index = rastgele.nextInt(ilgiAlanlari.size());
                secilenIlgiAlanlari.add(ilgiAlanlari.get(index));
            }
            
            
            
            teacherArray.add(new Teacher(ad, soyad, Arrays.toString(secilenIlgiAlanlari.toArray())));
            //System.out.println("Akademisyen #" + (i + 1) + "\n" + teacherArray + "\n");
        }
            return teacherArray;
    
        }
        
         //////  Student Method
        
        
        
        // Second Method
        
        public ArrayList<Student> randomStudentCreator(int ogrenciSayisi)
        {

        ArrayList<String> names = new ArrayList<>(Arrays.asList(
            "Ahmet", "Mehmet", "Ayşe", "Fatma", "Ali",
            "Elif", "Zeynep", "Mustafa", "Emre", "Selin",
            "Berk", "Gizem", "Eren", "Gül", "Murat",
            "Ebru", "Cem", "Pelin", "Deniz", "Kemal",
            "Nazlı", "Serdar", "Yasmin", "Sibel", "Baran",
            "Dilara", "Tolga", "Gözde", "Serkan", "Cansu",
            "Cihan", "Selma", "Mert", "Sema", "Hakan",
            "Leyla", "Oğuz", "Rabia", "Ege", "Gizem",
            "Batuhan", "Beyza", "Can", "Derya", "Emir",
            "Nil", "Uğur"
        ));

        ArrayList<String> surnames = new ArrayList<>(Arrays.asList(
            "Yılmaz", "Demir", "Kaya", "Öztürk", "Çelik",
            "Arslan", "Özdemir", "Kara", "Toprak", "Koç",
            "Güneş", "Sarı", "Bulut", "Turan", "Güler",
            "Aydın", "Erdoğan", "Yıldırım", "Kaplan", "Aktaş",
            "Sahin", "Çetin", "Aydınlı", "Yıldız", "Şahin",
            "Taş", "Kurt", "Soylu", "Alp", "Çakır",
            "Şen", "Taşkın", "Bakır", "Güneş", "Türk",
            "Yıldız", "Özkan", "Erdoğan", "Kurt", "Koç",
            "Yavuz", "Tosun", "Çetin", "Aydın", "Taşkın",
            "Korkmaz", "Koçak", "Yaman", "Aksoy", "Yılmaz"
        ));

        ArrayList<String> ilgiAlanlari = new ArrayList<>((Arrays.asList(
            "Yazılım Geliştirme",
            "Sistem Analizi",
            "Ağ Mühendisliği",
            "Veritabanı Yönetimi",
            "Web Geliştirme",
            "Oyun Geliştirme",
            "Mobil Uygulama Geliştirme",
            "Gömülü Sistem Mühendisliği",
            "Veri Bilimi",
            "Yapay Zeka ve Makine Öğrenimi",
            "Siber Güvenlik",
            "Bilgisayar Grafikleri",
            "Otomasyon ve Kontrol Sistemleri",
            "Bulut Bilişim",
            "Büyük Veri Analitiği",
            "İnternet of Things (IoT) Geliştirme",
            "Ağ Güvenliği",
            "Robotik Mühendisliği",
            "Donanım Tasarımı",
            "Sistem Güncelleme ve Bakım",
            "Veri Tabanlı Uygulama Geliştirme",
            "Mobil Oyun Geliştirme",
            "Veri Tabanı Tasarımı",
            "Ağ Altyapı Tasarımı",
            "Sanal Gerçeklik (VR) ve Artırılmış Gerçeklik (AR) Geliştirme",
            "Veri Tabanı Yönetimi ve Analitiği",
            "DevOps ve Sürekli Entegrasyon/Sürekli Dağıtım (CI/CD)",
            "Bilgisayar Ağları Yönetimi",
            "E-Commerce Uygulama Geliştirme",
            "Sistem Performans Optimizasyonu"
        )));

        ArrayList<Student> students = new ArrayList<>();// -----------------------------> öğrenci arrayi
        Random random = new Random();

        
        for (int i = 0; i < ogrenciSayisi; i++) {
            String randomName = names.get(random.nextInt(names.size()));
            String randomSurname = surnames.get(random.nextInt(surnames.size()));
            students.add(new Student(randomName, randomSurname, ilgiAlanlari));
            
        }
        

        for (Student student : students) { //---------------------->  öğrencilerileri arrayden toStringle yazdırma 
            System.out.println(student);
        }
            return students;
        }
    
}
