import java.util.List;
import java.util.Random;

/**
 * Класс-наследник, представляющий смартфон.
 */
public class Smartphone extends NetworkDevice {

    // Уникальные свойства (п. 5)
    private int batteryLevel;
    private int cameraMegapixels;
    private String osVersion;
    private double screenSize;

    public Smartphone(String manufacturer) {
        super(manufacturer, List.of("Wi-Fi", "Bluetooth"));
        this.batteryLevel = 100;
        this.cameraMegapixels = 12;
        this.osVersion = "Android 14";
        this.screenSize = 6.1;
    }

    // --- Уникальные методы (п. 5) ---
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

    // --- Реализация абстрактных методов ---
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

    @Override
    public void randomizeProperties() {
        Random rand = new Random();
        this.batteryLevel = rand.nextInt(1, 101);
        this.cameraMegapixels = rand.nextInt(8, 109);
        this.screenSize = 5.0 + (10.0 - 5.0) * rand.nextDouble();
    }

    @Override
    public String getDetailedInfo() {
        return String.format("Смартфон [ID: %s, Производитель: %s, Заряд: %d%%, Камера: %d Мп, ОС: %s, Экран: %.1f\"]",
                id.substring(0, 8), manufacturer, batteryLevel, cameraMegapixels, osVersion, screenSize);
    }

    // --- Сеттеры для изменения свойств (п. 3.3) ---
    public void setBatteryLevel(int batteryLevel) { this.batteryLevel = batteryLevel; }
    public void setCameraMegapixels(int cameraMegapixels) { this.cameraMegapixels = cameraMegapixels; }
    public void setOsVersion(String osVersion) { this.osVersion = osVersion; }
}