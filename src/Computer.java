import java.util.List;
import java.util.Random;

public class Computer extends NetworkDevice {

    // Уникальные свойства компьютера
    private int ramGB;
    private String cpuModel;
    private boolean hasDedicatedGPU;
    private int usbPorts;

    public Computer(String manufacturer) {
        super(manufacturer, List.of("Ethernet", "Wi-Fi", "Bluetooth"));
        // Инициализация уникальных свойств
        this.ramGB = 16;
        this.cpuModel = "Intel Core i7";
        this.hasDedicatedGPU = true;
        this.usbPorts = 6;
    }

    // Уникальные методы компьютера
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

    //Абстрактные методы
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

    // Заглушки пока остаются
    @Override
    public void randomizeProperties() {
    }

    @Override
    public String getDetailedInfo() {
        return "Компьютер (детальная информация появится позже)";
    }
}