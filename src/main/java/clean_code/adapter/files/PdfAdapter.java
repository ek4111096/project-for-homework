package clean_code.adapter.files;

public class PdfAdapter implements FileProcessor {
    private final DocProcessor docProcessor;

    public PdfAdapter() {
        this.docProcessor = new DocProcessor();
    }

    @Override
    public void process(String fileName) {

        System.out.println("Конвертация PDF в DOC: " + fileName);

        String docFile = fileName.replace(".pdf", ".doc");

        docProcessor.process(docFile);
    }
}
