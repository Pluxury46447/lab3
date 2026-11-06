import java.util.List;
import java.util.Random;
/**
 * Класс-наследник, представляющий компьютер.
 * Наследуется от абстрактного класса NetworkDevice.
 */
public class Computer extends NetworkDevice {

    //Уникальные свойства компьютера
    private int ramGB;               // Объем оперативной памяти
    private String cpuModel;         // Модель процессора
    private boolean hasDedicatedGPU; // Наличие дискретной видеокарты
    private int usbPorts;            // Количество свободных USB-портов

    /**
     * Конструктор компьютера.
     * Передает производителя и список интерфейсов в родительский класс.
     */
    public Computer(String manufacturer) {
        // Компьютер поддерживает Ethernet, Wi-Fi и Bluetooth
        super(manufacturer, List.of("Ethernet", "Wi-Fi", "Bluetooth"));

        // Инициализация уникальных свойств значениями по умолчанию
        this.ramGB = 16;
        this.cpuModel = "Intel Core i7";
        this.hasDedicatedGPU = true;
        this.usbPorts = 6;
    }

    //Уникальные методы компьютера
    /** Симуляция компиляции кода. */
    public void compileCode() {
        System.out.println("Компиляция Java-кода на " + cpuModel + " (ОЗУ: " + ramGB + "ГБ)... Успешно!");
    }

    /** Запуск игры (зависит от наличия дискретной видеокарты). */
    public void playGame(String gameName) {
        if (hasDedicatedGPU) {
            System.out.println("Запуск " + gameName + " на максимальных настройках графики.");
        } else {
            System.out.println("Запуск " + gameName + " на минимальных настройках (интегрированная графика).");
        }
    }

    /** Апгрейд оперативной памяти. */
    public void upgradeRAM(int extraGB) {
        this.ramGB += extraGB;
        System.out.println("ОЗУ увеличено до " + ramGB + " ГБ.");
    }

    /** Подключение внешнего устройства к USB. */
    public void connectExternalDevice(String device) {
        if (usbPorts > 0) {
            usbPorts--;
            System.out.println("Подключено внешнее устройство: " + device + ". Свободных USB: " + usbPorts);
        } else {
            System.out.println("Нет свободных USB-портов!");
        }
    }
    //Реализация абстрактных методов родителя
    @Override
    public void connect(String interfaceName) {
        // Проверяем поддержку интерфейса
        if (networkInterfaces.contains(interfaceName)) {
            this.isConnected = true;
            System.out.println("Компьютер подключен к " + interfaceName);
        } else {
            System.out.println("Компьютер не поддерживает " + interfaceName);
        }
    }

    @Override
    public void disconnect() {
        this.isConnected = false;
        this.connectedDeviceIds.clear();
        System.out.println("Компьютер отключен от сети.");
    }

    @Override
    public void pairWith(NetworkDevice other) {
        // Сопряжение только при наличии сети у обоих устройств
        if (this.isConnected && other.isConnected()) {
            this.connectedDeviceIds.add(other.getId());
            other.getConnectedDeviceIds().add(this.getId());
            System.out.println("Компьютер сопряжен с устройством: " + other.getManufacturer());
        } else {
            System.out.println("Оба устройства должны быть подключены к сети для сопряжения.");
        }
    }

    // --- Дополнительный функционал из абстрактного класса (п. 4) ---

    /**
     * Рандомизация свойств .
     * Задает случайные значения в пределах допустимых границ.
     */
    @Override
    public void randomizeProperties() {
        Random rand = new Random();
        this.ramGB = rand.nextInt(4, 129);       // ОЗУ от 4 до 128 ГБ
        this.hasDedicatedGPU = rand.nextBoolean(); // true/false
        this.usbPorts = rand.nextInt(2, 10);     // От 2 до 9 портов
    }

    /**
     * Вывод общей информации (п. 4.1).
     * Реализован вместо toString(), так как по заданию toString() не считается.
     */
    @Override
    public String getDetailedInfo() {
        // Тернарный оператор для вывода типа видеокарты
        return String.format("Компьютер [ID: %s, Производитель: %s, CPU: %s, ОЗУ: %d ГБ, Видеокарта: %s, USB: %d]",
                id.substring(0, 8), manufacturer, cpuModel, ramGB, hasDedicatedGPU ? "Дискретная" : "Интегрированная", usbPorts);
    }
}