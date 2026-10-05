package clean_code.adapter.files;

public class Main {
    public static void main(String[] args) {
        FileProcessor doc = new DocProcessor();
        FileProcessor pdf = new PdfAdapter();

        doc.process("document.doc");

        System.out.println();

        pdf.process("document1.pdf");
    }
}
