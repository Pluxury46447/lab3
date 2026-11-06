import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Класс-менеджер для управления списком устройств.
 * Реализует хранение объектов в едином списке (п. 2) и управление ими.
 */
public class DeviceManager {
    // Единый список для объектов разных классов (Полиморфизм)
    private final List<NetworkDevice> devices = new ArrayList<>();

    // Управление списком

    /** Добавление нового устройства в список. */
    public void addDevice(NetworkDevice device) {
        devices.add(device);
    }

    /**
     * Удаление устройства по ID.
     * Используется startsWith, чтобы можно было вводить только первые символы UUID.
     */
    public boolean removeDevice(String id) {
        return devices.removeIf(d -> d.getId().equals(id) || d.getId().startsWith(id));
    }

    /** Поиск устройства по ID (используется для операций и сопряжения). */
    public NetworkDevice findDevice(String id) {
        return devices.stream()
                .filter(d -> d.getId().equals(id) || d.getId().startsWith(id))
                .findFirst()
                .orElse(null);
    }

    // Вывод информации

    /** Вывод всех устройств в консоль. */
    public void showAllDevices() {
        if (devices.isEmpty()) {
            System.out.println("Список устройств пуст.");
            return;
        }
        // Благодаря полиморфизму у каждого устройства вызовется свой getDetailedInfo()
        for (var device : devices) {
            System.out.println(device.getDetailedInfo() + " | В сети: " + (device.isConnected() ? "Да" : "Нет"));
        }
    }

    /** Геттер для доступа к списку (нужен в Main для массовой рандомизации). */
    public List<NetworkDevice> getDevices() {
        return devices;
    }

    //  Интересный функционал

    /**
     * Симуляция передачи данных между двумя устройствами.
     * Проверяет, сопряжены ли устройства, и рассчитывает время передачи.
     */
    public void simulateDataTransfer(String sourceId, String targetId) {
        var source = findDevice(sourceId);
        var target = findDevice(targetId);

        if (source == null || target == null) {
            System.out.println("Одно из устройств не найдено.");
            return;
        }

        // Проверка на сопряжение (есть ли ID получателя в списке у источника)
        if (!source.getConnectedDeviceIds().contains(target.getId())) {
            System.out.println("Устройства не сопряжены! Сначала выполните сопряжение (пункт меню 4 -> 3).");
            return;
        }

        Random rand = new Random();
        int dataSizeMB = rand.nextInt(50, 500); // Размер файла от 50 до 500 МБ
        double speedMbps = 10.0 + (100.0 - 10.0) * rand.nextDouble(); // Скорость от 10 до 100 Мбит/с
        double timeSeconds = (dataSizeMB * 8) / speedMbps; // Время в секундах

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