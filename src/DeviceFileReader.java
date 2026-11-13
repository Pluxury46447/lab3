import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

public class DeviceFileReader {

    public static class ReadResult {
        public List<NetworkDevice> devices = new ArrayList<>();
        public int totalObjects;
        public int totalPropertiesRead;
        public int totalPropertiesMissing;
        public List<String> errors = new ArrayList<>();
    }

    public ReadResult read(Reader reader) throws IOException {
        ReadResult result = new ReadResult();
        BufferedReader br = new BufferedReader(reader);
        List<String> block = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) {
                if (!block.isEmpty()) {
                    processBlock(block, result);
                    block.clear();
                }
            } else {
                block.add(line);
            }
        }
        if (!block.isEmpty()) {
            processBlock(block, result);
        }
        return result;
    }

    private void processBlock(List<String> block, ReadResult result) {
        Map<String, String> props = new HashMap<>();
        for (String line : block) {
            int colon = line.indexOf(':');
            if (colon == -1) {
                result.errors.add("Неверная строка (нет двоеточия): " + line);
                continue;
            }
            String key = line.substring(0, colon).trim().toLowerCase();
            String value = line.substring(colon + 1).trim();
            // Удаляем завершающий разделитель (запятую или точку с запятой), если есть
            if (value.endsWith(",") || value.endsWith(";")) {
                value = value.substring(0, value.length() - 1).trim();
            }
            props.put(key, value);
        }

        if (!props.containsKey("type")) {
            result.errors.add("Блок не содержит поле 'Type': " + block);
            return;
        }

        String type = props.get("type").toLowerCase();
        NetworkDevice device = null;
        Set<String> expectedFields = null;

        switch (type) {
            case "smartphone":
                device = createSmartphone(props, result);
                expectedFields = Set.of("manufacturer", "batterylevel", "cameramegapixels", "osversion", "screensize");
                break;
            case "tablet":
                device = createTablet(props, result);
                expectedFields = Set.of("manufacturer", "hasstylus", "storagegb", "weightkg", "screenresolution");
                break;
            case "computer":
                device = createComputer(props, result);
                expectedFields = Set.of("manufacturer", "ramgb", "cpumodel", "hasdedicatedgpu", "usbports");
                break;
            default:
                result.errors.add("Неизвестный тип устройства: " + type);
                return;
        }


    }

    // Заглушки для методов создания (в коммите  дальше)
    private Smartphone createSmartphone(Map<String, String> props, ReadResult result) { return null; }
    private Tablet createTablet(Map<String, String> props, ReadResult result) { return null; }
    private Computer createComputer(Map<String, String> props, ReadResult result) { return null; }
}