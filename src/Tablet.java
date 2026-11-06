import java.util.List;
import java.util.Random;

public class Tablet extends NetworkDevice {

    // Уникальные свойства планшета
    private boolean hasStylus;
    private int storageGB;
    private double weightKg;
    private String screenResolution;

    public Tablet(String manufacturer) {
        super(manufacturer, List.of("Wi-Fi", "Bluetooth"));
        // Инициализация уникальных свойств
        this.hasStylus = false;
        this.storageGB = 128;
        this.weightKg = 0.5;
        this.screenResolution = "2K";
    }

    // Уникальные методы планшета
    public void drawSketch() {
        if (hasStylus) {
            System.out.println("Рисование стилусом...");
        } else {
            System.out.println("Стилус не подключен. Рисование пальцем.");
        }
    }

    public void watchMovie() {
        System.out.println("Просмотр фильма в разрешении " + screenResolution);
    }

    public void connectStylus() {
        this.hasStylus = true;
        System.out.println("Стилус подключен.");
    }

    public void expandStorage(int extraGB) {
        this.storageGB += extraGB;
        System.out.println("Память расширена до " + storageGB + " ГБ.");
    }

    // Абстрактные методы (из коммита 1)
    @Override
    public void connect(String interfaceName) {
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
        if (this.isConnected && other.isConnected()) {
            this.connectedDeviceIds.add(other.getId());
            other.getConnectedDeviceIds().add(this.getId());
            System.out.println("Планшет сопряжен с устройством: " + other.getManufacturer());
        } else {
            System.out.println("Оба устройства должны быть подключены к сети для сопряжения.");
        }
    }
    // Заглушки
    @Override
    public void randomizeProperties() {
    }

    @Override
    public String getDetailedInfo() {
        return "Планшет (детальная информация появится позже)";
    }
}