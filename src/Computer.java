import java.util.List;
import java.util.Random;

/**
 * Класс-наследник, представляющий компьютер.
 */
public class Computer extends NetworkDevice {

    // Уникальные свойства (п. 5)
    private int ramGB;
    private String cpuModel;
    private boolean hasDedicatedGPU;
    private int usbPorts;

    public Computer(String manufacturer) {
        super(manufacturer, List.of("Ethernet", "Wi-Fi", "Bluetooth"));
        this.ramGB = 16;
        this.cpuModel = "Intel Core i7";
        this.hasDedicatedGPU = true;
        this.usbPorts = 6;
    }

    // --- Уникальные методы (п. 5) ---
    public void compileCode() {
        System.out.println("Компиляция Java-кода на " + cpuModel + " (ОЗУ: " + ramGB + "ГБ)... Успешно!");
    }

    public void playGame(String gameName) {
        if (hasDedicatedGPU) {
            System.out.println("Запуск " + gameName + " на максимальных настройках графики.");
        } else {
            System.out.println("Запуск " + gameName + " на минимальных настройках (интегрированная графика).");
        }
    }

    public void upgradeRAM(int extraGB) {
        this.ramGB += extraGB;
        System.out.println("ОЗУ увеличено до " + ramGB + " ГБ.");
    }

    public void connectExternalDevice(String device) {
        if (usbPorts > 0) {
            usbPorts--;
            System.out.println("Подключено внешнее устройство: " + device + ". Свободных USB: " + usbPorts);
        } else {
            System.out.println("Нет свободных USB-портов!");
        }
    }

    // --- Реализация абстрактных методов ---
    @Override
    public void connect(String interfaceName) {
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
        if (this.isConnected && other.isConnected()) {
            this.connectedDeviceIds.add(other.getId());
            other.getConnectedDeviceIds().add(this.getId());
            System.out.println("Компьютер сопряжен с устройством: " + other.getManufacturer());
        } else {
            System.out.println("Оба устройства должны быть подключены к сети для сопряжения.");
        }
    }

    @Override
    public void randomizeProperties() {
        Random rand = new Random();
        this.ramGB = rand.nextInt(4, 129);
        this.hasDedicatedGPU = rand.nextBoolean();
        this.usbPorts = rand.nextInt(2, 10);
    }

    @Override
    public String getDetailedInfo() {
        return String.format("Компьютер [ID: %s, Производитель: %s, CPU: %s, ОЗУ: %d ГБ, Видеокарта: %s, USB: %d]",
                id.substring(0, 8), manufacturer, cpuModel, ramGB, hasDedicatedGPU ? "Дискретная" : "Интегрированная", usbPorts);
    }

    // --- Сеттеры для изменения свойств (п. 3.3) ---
    public void setRamGB(int ramGB) { this.ramGB = ramGB; }
    public void setCpuModel(String cpuModel) { this.cpuModel = cpuModel; }
}