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

        if (device != null) {
            result.devices.add(device);
            result.totalObjects++;

            // Подсчёт прочитанных и отсутствующих свойств
            int readCount = 0;
            if (props.containsKey("manufacturer")) readCount++;

            for (String field : expectedFields) {
                if (field.equals("manufacturer")) continue;
                if (props.containsKey(field)) {
                    readCount++;
                }
            }
            result.totalPropertiesRead += readCount;
            result.totalPropertiesMissing += (expectedFields.size() - readCount);
        }
    }

    private Smartphone createSmartphone(Map<String, String> props, ReadResult result) {
        String manufacturer = props.getOrDefault("manufacturer", "Unknown");
        Smartphone sp = new Smartphone(manufacturer);
        if (props.containsKey("batterylevel")) {
            try {
                sp.setBatteryLevel(Integer.parseInt(props.get("batterylevel")));
            } catch (NumberFormatException e) {
                result.errors.add("Неверное значение batteryLevel: " + props.get("batterylevel"));
            }
        }
        if (props.containsKey("cameramegapixels")) {
            try {
                sp.setCameraMegapixels(Integer.parseInt(props.get("cameramegapixels")));
            } catch (NumberFormatException e) {
                result.errors.add("Неверное значение cameraMegapixels: " + props.get("cameramegapixels"));
            }
        }
        if (props.containsKey("osversion")) {
            sp.setOsVersion(props.get("osversion"));
        }
        if (props.containsKey("screensize")) {
            try {
                sp.setScreenSize(Double.parseDouble(props.get("screensize")));
            } catch (NumberFormatException e) {
                result.errors.add("Неверное значение screenSize: " + props.get("screensize"));
            }
        }
        return sp;
    }

    private Tablet createTablet(Map<String, String> props, ReadResult result) {
        String manufacturer = props.getOrDefault("manufacturer", "Unknown");
        Tablet t = new Tablet(manufacturer);
        if (props.containsKey("hasstylus")) {
            t.setHasStylus(Boolean.parseBoolean(props.get("hasstylus")));
        }
        if (props.containsKey("storagegb")) {
            try {
                t.setStorageGB(Integer.parseInt(props.get("storagegb")));
            } catch (NumberFormatException e) {
                result.errors.add("Неверное значение storageGB: " + props.get("storagegb"));
            }
        }
        if (props.containsKey("weightkg")) {
            try {
                t.setWeightKg(Double.parseDouble(props.get("weightkg")));
            } catch (NumberFormatException e) {
                result.errors.add("Неверное значение weightKg: " + props.get("weightkg"));
            }
        }
        if (props.containsKey("screenresolution")) {
            t.setScreenResolution(props.get("screenresolution"));
        }
        return t;
    }

    private Computer createComputer(Map<String, String> props, ReadResult result) {
        String manufacturer = props.getOrDefault("manufacturer", "Unknown");
        Computer c = new Computer(manufacturer);
        if (props.containsKey("ramgb")) {
            try {
                c.setRamGB(Integer.parseInt(props.get("ramgb")));
            } catch (NumberFormatException e) {
                result.errors.add("Неверное значение ramGB: " + props.get("ramgb"));
            }
        }
        if (props.containsKey("cpumodel")) {
            c.setCpuModel(props.get("cpumodel"));
        }
        if (props.containsKey("hasdedicatedgpu")) {
            c.setHasDedicatedGPU(Boolean.parseBoolean(props.get("hasdedicatedgpu")));
        }
        if (props.containsKey("usbports")) {
            try {
                c.setUsbPorts(Integer.parseInt(props.get("usbports")));
            } catch (NumberFormatException e) {
                result.errors.add("Неверное значение usbPorts: " + props.get("usbports"));
            }
        }
        return c;
    }
}