import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Класс-менеджер для управления списком устройств.
 */
public class DeviceManager {
    // Единый список для объектов разных классов (Полиморфизм)
    //файнал - ссылка на список не может быть переприсвоена(но сами элементы внутри списка можно удалять и доб)
    //в лист можно класть любые объекты которые насл от нетворк девайс
    //нью эрэй - созд экзэмпляр arraylist
    private final List<NetworkDevice> devices = new ArrayList<>();
//добавление.принимает любое устройство типа нетворкдевайс (или его насл) и в конец списка
    public void addDevice(NetworkDevice device) {
        devices.add(device);
    }
//лямбда выражение- для каждого элемента d проверяем полное совп и начинается с введенной строки
    public boolean removeDevice(String id) {
        return devices.removeIf(d -> d.getId().equals(id) || d.getId().startsWith(id));
    }

    public NetworkDevice findDevice(String id) {
        return devices.stream()//прев список в поток данных для обработки
                .filter(d -> d.getId().equals(id) || d.getId().startsWith(id))//ост ток те устройства чей айди совп
                .findFirst()//берём первый найденный элемент
                .orElse(null);//если ничего то нал
    }//полиморфизм: из мэйн вызываем файнд девайс, получаем нетфоркдевайс, а потом проверяем через instanceof кто это на самом деле

    public void showAllDevices() {
        if (devices.isEmpty()) {
            System.out.println("Список устройств пуст.");
            return;
        }//циклом проходимся
        for (var device : devices) {
            System.out.println(device.getDetailedInfo() + " | В сети: " + (device.isConnected() ? "Да" : "Нет"));
        }//динамический полиморфизм()гет детэйлед инфо)(что реально лежит в девайс) и вызыв его переопр метод
    }//тернарный оператор для красивого вывода
//возвр ссылку на списо. инкапсуляция. мы не даём прямой доступ к полю девайсес но даём метод для чтения
    public List<NetworkDevice> getDevices() {
        return devices;
    }

    // Симуляция передачи данных
    public void simulateDataTransfer(String sourceId, String targetId) {//строка айди которую ввёл пользователь
        var source = findDevice(sourceId);//это то же самое если написать нетворк девайс
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