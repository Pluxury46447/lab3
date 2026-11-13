import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

/**
 * ЛАБА 4: Класс для чтения устройств из текстового файла.
 * Принимает в качестве параметра поток файла (Reader).
 */
public class DeviceFileReader {

    // Результат чтения файла: список устройств и статистика
    public static class ReadResult {
        public List<NetworkDevice> devices = new ArrayList<>();
        public int totalObjects;           // количество найденных объектов
        public int totalPropertiesRead;    // сколько свойств успешно прочитано
        public int totalPropertiesMissing; // сколько свойств не найдено
        public List<String> errors = new ArrayList<>(); // ошибки парсинга
    }

    /**
     * Читает данные из потока и возвращает результат.
     * @param reader поток для чтения (например, FileReader)
     */
    public ReadResult read(Reader reader) throws IOException {
        ReadResult result = new ReadResult();
        BufferedReader br = new BufferedReader(reader);
        List<String> block = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) {
                // Пустая строка — разделитель между объектами
                if (!block.isEmpty()) {
                    processBlock(block, result);
                    block.clear();
                }
            } else {
                block.add(line);
            }
        }
        // Обработать последний блок, если файл не заканчивается пустой строкой
        if (!block.isEmpty()) {
            processBlock(block, result);
        }
        return result;
    }

    /**
     * Обрабатывает один блок строк (одно устройство).
     */
    private void processBlock(List<String> block, ReadResult result) {
    }
}