import java.util.List;
// Добавлен для будущей рандомизации
import java.util.Random;

public class Smartphone extends NetworkDevice {
    // 4 уникальных свойства смартфона
    private int batteryLevel;
    private int cameraMegapixels;
    private String osVersion;
    private double screenSize;

    public Smartphone(String manufacturer) {
        super(manufacturer, List.of("Wi-Fi", "Bluetooth"));
        // Инициализация уникальных свойств
        this.batteryLevel = 100;
        this.cameraMegapixels = 12;
        this.osVersion = "Android 14";
        this.screenSize = 6.1;
    }

    // 4 уникальных метода смартфона
    public void takePhoto() {
        if (batteryLevel > 5) {
            batteryLevel -= 5;
            System.out.println("Сделан снимок на камеру " + cameraMegapixels + " Мп. Заряд: " + batteryLevel + "%");
        } else {
            System.out.println("Недостаточно заряда для фото!");
        }
    }

    public void installApp(String appName) {
        System.out.println("Приложение " + appName + " успешно установлено на " + manufacturer + " " + osVersion);
    }

    public void updateOS(String newVersion) {
        this.osVersion = newVersion;
        System.out.println("ОС обновлена до версии: " + newVersion);
    }

    public void chargeBattery() {
        this.batteryLevel = 100;
        System.out.println("Смартфон полностью заряжен.");
    }

    // абстрактные методы
    @Override
    public void connect(String interfaceName) {
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
        this.connectedDeviceIds.clear();
        System.out.println("Смартфон отключен от сети.");
    }

    @Override
    public void pairWith(NetworkDevice other) {
        if (this.isConnected && other.isConnected()) {
            this.connectedDeviceIds.add(other.getId());
            other.getConnectedDeviceIds().add(this.getId());
            System.out.println("Смартфон сопряжен с устройством: " + other.getManufacturer());
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
        return "Смартфон (детальная информация появится позже)";
    }
}