import java.util.ArrayList;
import java.util.List;

public class DeviceManager {
    // Единый список для объектов разных классов (Полиморфизм)
    private final List<NetworkDevice> devices = new ArrayList<>();

    // Добавление устройства
    public void addDevice(NetworkDevice device) {
        devices.add(device);
    }

    // Удаление устройства по ID (п. 3.2)
    public boolean removeDevice(String id) {
        // Ищем по полному совпадению или по началу строки (удобно для ввода)
        return devices.removeIf(d -> d.getId().equals(id) || d.getId().startsWith(id));
    }

    // Поиск устройства по ID (нужно для функционала)
    public NetworkDevice findDevice(String id) {
        return devices.stream()
                .filter(d -> d.getId().equals(id) || d.getId().startsWith(id))
                .findFirst()
                .orElse(null);
    }

    // Вывод всех устройств
    public void showAllDevices() {
        if (devices.isEmpty()) {
            System.out.println("Список устройств пуст.");
            return;
        }
        for (var device : devices) {
            System.out.println(device.getDetailedInfo() + " | В сети: " + (device.isConnected() ? "Да" : "Нет"));
        }
    }
}