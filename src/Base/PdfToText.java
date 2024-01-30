package Base;


//
//import java.io.File;
//import java.io.IOException;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.text.PDFTextStripper;
//
//public class PdfToText {
//    public static void main(String[] args) {
//        String pdfFilePath = "C:\\Users\\Sinan\\Downloads\\INTIBAK\\EK-2 Not Durum Belgesi (Transkript).pdf"; // Transkript PDF dosyasının yolu
//    C:\Users\Sinan\Desktop\transkript1.pdf
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import SQL.*;

public class PdfToText {

    public List<Lesson> readTranskript(String pdfFilePath){
        //String pdfFilePath = "C:\\Users\\tarik\\Downloads\\Study\\BM\\3,\\1. D\\Yazlab I 1\\Sinan\\Ahmad transkript.pdf";

        try {
            File file = new File(pdfFilePath);
            PDDocument document = PDDocument.load(file);
            PDFTextStripper pdfTextStripper = new PDFTextStripper();

            String text = pdfTextStripper.getText(document);
            
            List<Lesson> lessons = new ArrayList<>();

            List<String> letterName = new ArrayList<>();
            List<String> letterGrades = new ArrayList();
            List<Double> gnoList = new ArrayList<>();

            String dersKoduRegex = ("[A-Z]{3}\\d{3} (.*?)\\s*\\(");
            String harfNotuRegex = "(AA|BA|BB|CB|CC|DC|DD| FF |\\sN\\s)";
            String regex = "GNO:\\s*\\(CGPA\\)\\s*(\\d+\\.\\d+)";

            Pattern pattern = Pattern.compile(regex);
            Pattern dersKoduPattern = Pattern.compile(dersKoduRegex);
            Pattern harfNotuPattern = Pattern.compile(harfNotuRegex);

            Matcher dersKoduMatcher = dersKoduPattern.matcher(text);
            Matcher harfNotuMatcher = harfNotuPattern.matcher(text);
            Matcher matcher = pattern.matcher(text);

            while (dersKoduMatcher.find() && harfNotuMatcher.find()) {
                letterName.add(dersKoduMatcher.group());
                letterGrades.add(harfNotuMatcher.group());
            }

            int skipLines = 3;
            for (int i = 0; i < skipLines; i++) {
                int index = text.indexOf(letterName.get(0));
                text = text.substring(index + letterName.get(0).length());
            }
            while (matcher.find()) {
                String gnoStr = matcher.group(1);
                double gno = Double.parseDouble(gnoStr);
                gnoList.add(gno);
            }

            ArrayList<String> letterCode = new ArrayList<>(letterName);

            for (int i = 0; i < letterName.size(); i++) {
                String eleman = letterName.get(i);
                if (eleman.length() >= 8) {
                    eleman = eleman.substring(7, eleman.length() - 1);
                    letterName.set(i, eleman);
                    letterName.set(i,letterName.get(i).trim()); /// Do not forget this 
                }
            }

            for (int i = 0; i < letterCode.size(); i++) {
                String eleman2 = letterCode.get(i);
                if (eleman2.length() >= 8) {
                    eleman2 = eleman2.substring(0, 6);
                    letterCode.set(i, eleman2);
                }
            }
            
            System.out.println("Ders Isimleri:");
            for (String code : letterName) {
                System.out.println(code);
            }
            
            System.out.println("Ders Kodları:");
            for (String code2 : letterCode) {
                System.out.println(code2);
            }
            
            
            System.out.println("Harf Notları:");
            for (String grade : letterGrades) {
                System.out.println(grade);
            }
            System.out.print("GNO:");
            double sonGno = gnoList.get(gnoList.size() - 1);
            System.out.println(sonGno);

            for (int i = 0; i < letterName.size(); i++) {
                lessons.add(new Lesson());
                lessons.get(i).setLessonName(letterName.get(i));
                lessons.get(i).setLessonCode(letterCode.get(i));
                lessons.get(i).setStatus(letterGrades.get(i));
             }
            
            
            document.close();
            
            return lessons;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
        
    }
    
    
    public double readGNO(String pdfFilePath){
        //String pdfFilePath = "C:\\Users\\tarik\\Downloads\\Study\\BM\\3,\\1. D\\Yazlab I 1\\Sinan\\Ahmad transkript.pdf";

        try {
            File file = new File(pdfFilePath);
            PDDocument document = PDDocument.load(file);
            PDFTextStripper pdfTextStripper = new PDFTextStripper();

            String text = pdfTextStripper.getText(document);
            
            List<Double> gnoList = new ArrayList<>();

            String dersKoduRegex = ("[A-Z]{3}\\d{3} (.*?)\\s*\\(");
            String harfNotuRegex = "(AA|BA|BB|CB|CC|DC|DD|FF|\\sN\\s)";
            String regex = "GNO:\\s*\\(CGPA\\)\\s*(\\d+\\.\\d+)";

            Pattern pattern = Pattern.compile(regex);
            Pattern dersKoduPattern = Pattern.compile(dersKoduRegex);
            Pattern harfNotuPattern = Pattern.compile(harfNotuRegex);

            Matcher dersKoduMatcher = dersKoduPattern.matcher(text);
            Matcher harfNotuMatcher = harfNotuPattern.matcher(text);
            Matcher matcher = pattern.matcher(text);


            while (matcher.find()) {
                String gnoStr = matcher.group(1);
                double gno = Double.parseDouble(gnoStr);
                gnoList.add(gno);
            }


            System.out.print("GNO:");
            double sonGno = gnoList.get(gnoList.size() - 1);
            System.out.println(sonGno);

            
            document.close();
            
            return sonGno;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
        
    }
}
