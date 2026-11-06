import java.util.List;
import java.util.Random;

/**
 * Класс-наследник, представляющий планшет.
 * Наследуется от абстрактного класса NetworkDevice (п. 1, 2.3).
 */
public class Tablet extends NetworkDevice {

    //Уникальные свойства планшета
    private boolean hasStylus;       // Есть ли стилус
    private int storageGB;           // Объем памяти в ГБ
    private double weightKg;         // Вес в кг
    private String screenResolution; // Разрешение экрана

    /**
     * Конструктор планшета.
     * Передает производителя и список интерфейсов (Wi-Fi, Bluetooth) в родительский класс.
     */
    public Tablet(String manufacturer) {
        // Вызов конструктора родителя (п. 2.3)
        super(manufacturer, List.of("Wi-Fi", "Bluetooth"));

        // Инициализация уникальных свойств значениями по умолчанию
        this.hasStylus = false;
        this.storageGB = 128;
        this.weightKg = 0.5;
        this.screenResolution = "2K";
    }

    //Уникальные методы планшета

    /** Рисование (зависит от наличия стилуса). */
    public void drawSketch() {
        if (hasStylus) {
            System.out.println("Рисование стилусом...");
        } else {
            System.out.println("Стилус не подключен. Рисование пальцем.");
        }
    }

    /** Просмотр фильма (выводит разрешение экрана). */
    public void watchMovie() {
        System.out.println("Просмотр фильма в разрешении " + screenResolution);
    }

    /** Подключить стилус. */
    public void connectStylus() {
        this.hasStylus = true;
        System.out.println("Стилус подключен.");
    }

    /** Расширить встроенную память. */
    public void expandStorage(int extraGB) {
        this.storageGB += extraGB;
        System.out.println("Память расширена до " + storageGB + " ГБ.");
    }

    // Реализация абстрактных методов родителя

    @Override
    public void connect(String interfaceName) {
        // Проверяем поддержку интерфейса
        if (networkInterfaces.contains(interfaceName)) {
            this.isConnected = true;
            System.out.println("Планшет подключен к " + interfaceName);
        } else {
            System.out.println("Планшет не поддерживает " + interfaceName);
        }
    }

    @Override
    public void disconnect() {
        this.isConnected = false;
        this.connectedDeviceIds.clear();
        System.out.println("Планшет отключен от сети.");
    }

    @Override
    public void pairWith(NetworkDevice other) {
        // Сопряжение только при наличии сети у обоих устройств
        if (this.isConnected && other.isConnected()) {
            this.connectedDeviceIds.add(other.getId());
            other.getConnectedDeviceIds().add(this.getId());
            System.out.println("Планшет сопряжен с устройством: " + other.getManufacturer());
        } else {
            System.out.println("Оба устройства должны быть подключены к сети для сопряжения.");
        }
    }

    // Дополнительный функционал из абстрактного класса

    /**
     * Рандомизация свойств (п. 4.3).
     * Задает случайные значения в пределах допустимых границ.
     */
    @Override
    public void randomizeProperties() {
        Random rand = new Random();
        this.hasStylus = rand.nextBoolean(); // Случайно true/false
        this.storageGB = rand.nextInt(64, 1025); // От 64 до 1024 ГБ
        this.weightKg = 0.3 + (1.5 - 0.3) * rand.nextDouble(); // От 0.3 до 1.5 кг
    }

    /**
     * Вывод общей информации (п. 4.1).
     * Реализован вместо toString(), так как по заданию toString() не считается.
     */
    @Override
    public String getDetailedInfo() {
        // Тернарный оператор для красивого вывода boolean
        return String.format("Планшет [ID: %s, Производитель: %s, Стилус: %s, Память: %d ГБ, Вес: %.2f кг, Экран: %s]",
                id.substring(0, 8), manufacturer, hasStylus ? "Да" : "Нет", storageGB, weightKg, screenResolution);
    }
}