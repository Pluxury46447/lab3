import java.util.List;
import java.util.Random;

/**
 * Класс-наследник, представляющий смартфон.
 * Наследуется от абстрактного класса NetworkDevice.
 */
public class Smartphone extends NetworkDevice {
    //Уникальные свойства смартфона
    private int batteryLevel;
    private int cameraMegapixels;
    private String osVersion;
    private double screenSize;
    /**
     * Конструктор смартфона.
     * Передает производителя и список поддерживаемых интерфейсов в родительский класс.
     */
    public Smartphone(String manufacturer) {
        // Вызов конструктора родителя (super). Смартфон поддерживает Wi-Fi и Bluetooth.
        super(manufacturer, List.of("Wi-Fi", "Bluetooth"));
        // Инициализация уникальных свойств значениями по умолчанию
        this.batteryLevel = 100;
        this.cameraMegapixels = 12;
        this.osVersion = "Android 14";
        this.screenSize = 6.1;
    }

    // Уникальные методы смартфона

    /** Сделать фото (тратит заряд батареи). */
    public void takePhoto() {
        if (batteryLevel > 5) {
            batteryLevel -= 5;
            System.out.println("Сделан снимок на камеру " + cameraMegapixels + " Мп. Заряд: " + batteryLevel + "%");
        } else {
            System.out.println("Недостаточно заряда для фото!");
        }
    }

    /** Установить приложение. */
    public void installApp(String appName) {
        System.out.println("Приложение " + appName + " успешно установлено на " + manufacturer + " " + osVersion);
    }

    /** Обновить операционную систему. */
    public void updateOS(String newVersion) {
        this.osVersion = newVersion;
        System.out.println("ОС обновлена до версии: " + newVersion);
    }

    /** Зарядить смартфон до 100%. */
    public void chargeBattery() {
        this.batteryLevel = 100;
        System.out.println("Смартфон полностью заряжен.");
    }

    //Реализация абстрактных методов родителя
    @Override
    public void connect(String interfaceName) {
        // Проверяем, поддерживает ли смартфон запрошенный интерфейс
        if (networkInterfaces.contains(interfaceName)) {
            this.isConnected = true;
            System.out.println("Смартфон подключен к " + interfaceName);
        } else {
            System.out.println("Смартфон не поддерживает " + interfaceName);
        }
    }

    @Override
    public void disconnect() {
        this.isConnected = false;
        this.connectedDeviceIds.clear(); // Очищаем список сопряженных устройств
        System.out.println("Смартфон отключен от сети.");
    }

    @Override
    public void pairWith(NetworkDevice other) {
        // Сопряжение возможно только если оба устройства в сети
        if (this.isConnected && other.isConnected()) {
            this.connectedDeviceIds.add(other.getId());
            other.getConnectedDeviceIds().add(this.getId());
            System.out.println("Смартфон сопряжен с устройством: " + other.getManufacturer());
        } else {
            System.out.println("Оба устройства должны быть подключены к сети для сопряжения.");
        }
    }

    //Дополнительный функционал из абстрактного класса

    /**
     * Рандомизация свойств (п. 4.3).
     * Задает случайные значения в заданных границах определения величины.
     */
    @Override
    public void randomizeProperties() {
        Random rand = new Random();
        this.batteryLevel = rand.nextInt(1, 101);       // Заряд от 1 до 100
        this.cameraMegapixels = rand.nextInt(8, 109);   // Камера от 8 до 108 Мп
        this.screenSize = 5.0 + (10.0 - 5.0) * rand.nextDouble(); // Экран от 5.0 до 10.0
    }

    /**
     * Вывод общей информации
     * Реализован вместо toString(), так как по заданию toString() не считается.
     */
    @Override
    public String getDetailedInfo() {
        // Форматируем строку, обрезая длинный ID до первых 8 символов для читаемости
        return String.format("Смартфон [ID: %s, Производитель: %s, Заряд: %d%%, Камера: %d Мп, ОС: %s, Экран: %.1f\"]",
                id.substring(0, 8), manufacturer, batteryLevel, cameraMegapixels, osVersion, screenSize);
    }
}