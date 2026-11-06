import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Класс-менеджер для управления списком устройств.
 */
public class DeviceManager {
    // Единый список для объектов разных классов (Полиморфизм)
    private final List<NetworkDevice> devices = new ArrayList<>();

    public void addDevice(NetworkDevice device) {
        devices.add(device);
    }

    public boolean removeDevice(String id) {
        return devices.removeIf(d -> d.getId().equals(id) || d.getId().startsWith(id));
    }

    public NetworkDevice findDevice(String id) {
        return devices.stream()
                .filter(d -> d.getId().equals(id) || d.getId().startsWith(id))
                .findFirst()
                .orElse(null);
    }

    public void showAllDevices() {
        if (devices.isEmpty()) {
            System.out.println("Список устройств пуст.");
            return;
        }
        for (var device : devices) {
            System.out.println(device.getDetailedInfo() + " | В сети: " + (device.isConnected() ? "Да" : "Нет"));
        }
    }

    public List<NetworkDevice> getDevices() {
        return devices;
    }

    // Интересный функционал (п. 7): Симуляция передачи данных
    public void simulateDataTransfer(String sourceId, String targetId) {
        var source = findDevice(sourceId);
        var target = findDevice(targetId);

        if (source == null || target == null) {
            System.out.println("Одно из устройств не найдено.");
            return;
        }

        if (!source.getConnectedDeviceIds().contains(target.getId())) {
            System.out.println("Устройства не сопряжены! Сначала выполните сопряжение (пункт меню 4 -> 3).");
            return;
        }

        Random rand = new Random();
        int dataSizeMB = rand.nextInt(50, 500);
        double speedMbps = 10.0 + (100.0 - 10.0) * rand.nextDouble();
        double timeSeconds = (dataSizeMB * 8) / speedMbps;

        System.out.printf("Передача %d МБ с %s на %s...%n", dataSizeMB, source.getManufacturer(), target.getManufacturer());
        System.out.printf("Средняя скорость: %.2f Мбит/с%n", speedMbps);
        System.out.printf("Затраченное время: %.1f сек.%n", timeSeconds);

        if (timeSeconds > 30) {
            System.out.println("Внимание: передача заняла много времени из-за низкой скорости сети!");
        } else {
            System.out.println("Передача завершена успешно!");
        }
    }
}