interface DocumentParser{
    void parse(String fileName);
}
class PDFParser implements DocumentParser{
    public void parse(String fileName){
        System.out.println("Parsing PDF Document: "+ fileName);
    }
}
class WordParser implements DocumentParser{
    public void parse(String fileName){
        System.out.println("Parsing Word Document: "+ fileName);
    }
}
class ExcelParser implements DocumentParser{
    public void parse(String fileName){
        System.out.println("Parsing Excel Document: "+ fileName);
    }
}
class parsorFactory{
    public static DocumentParser createParser(String type){
        if(type.equalsIgnoreCase("PDF")){
            return new PDFParser();
        }
        if(type.equalsIgnoreCase("WORD")){
            return new WordParser();
        }
        if(type.equalsIgnoreCase("EXCEL")){
            return new ExcelParser();
        }
        throw new IllegalArgumentException(
            "Illegal Document Type:" + type
        );
    }
}
public class Main{
    public static void main(String[] args){
        DocumentParser d1 = parsorFactory.createParser("PDF");
        d1.parse("Java.pdf");
        DocumentParser d2 = parsorFactory.createParser("WORD");
        d2.parse("Python.word");
        DocumentParser d3 = parsorFactory.createParser("EXCEL");
        d3.parse("C.excel");
        DocumentParser d4 = parsorFactory.createParser("EXE");
        d4.parse("GO.exe");
    }
}