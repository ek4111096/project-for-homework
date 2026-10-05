package clean_code.adapter.files;

public class DocProcessor implements FileProcessor{
    @Override
    public void process(String fileName) {
        System.out.println("Обработка DOC-файла: " + fileName);
    }
}
