import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Класс-менеджер для управления списком устройств.
 */
public class DeviceManager {
    // Единый список для объектов разных классов (Полиморфизм)
    // final - ссылка на список не может быть переприсвоена (но сами элементы внутри списка можно удалять и добавлять)
    // в List можно класть любые объекты, которые наследуются от NetworkDevice
    // new ArrayList<>() - создаём экземпляр ArrayList
    private final List<NetworkDevice> devices = new ArrayList<>();

    // Добавление. Принимает любое устройство типа NetworkDevice (или его наследника) и добавляет в конец списка
    public void addDevice(NetworkDevice device) {
        devices.add(device);
    }

    // Лямбда-выражение - для каждого элемента d проверяем полное совпадение и начинается с введенной строки
    public boolean removeDevice(String id) {
        return devices.removeIf(d -> d.getId().equals(id) || d.getId().startsWith(id));
    }

    public NetworkDevice findDevice(String id) {
        return devices.stream()                                        // превращаем список в поток данных для обработки
                .filter(d -> d.getId().equals(id) || d.getId().startsWith(id)) // оставляем только те устройства, чей ID совпадает
                .findFirst()                                           // берём первый найденный элемент
                .orElse(null);                                         // если ничего - null
    }
    // Полиморфизм: из Main вызываем findDevice, получаем NetworkDevice,
    // а потом проверяем через instanceof, кто это на самом деле

    public void showAllDevices() {
        if (devices.isEmpty()) {
            System.out.println("Список устройств пуст.");
            return;
        }
        // Циклом проходимся по списку
        for (var device : devices) {
            System.out.println(device.getDetailedInfo() + " | В сети: " + (device.isConnected() ? "Да" : "Нет"));
        }
        // Динамический полиморфизм: вызываем getDetailedInfo() - JVM смотрит,
        // что реально лежит в device, и вызывает его переопределённый метод
        // Тернарный оператор для красивого вывода статуса
    }

    // Возвращает ссылку на список. Инкапсуляция: мы не даём прямой доступ к полю devices,
    // но даём метод для чтения
    public List<NetworkDevice> getDevices() {
        return devices;
    }

    // Симуляция передачи данных
    public void simulateDataTransfer(String sourceId, String targetId) { // строка ID, которую ввёл пользователь
        var source = findDevice(sourceId); // var - это то же самое, если написать NetworkDevice
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