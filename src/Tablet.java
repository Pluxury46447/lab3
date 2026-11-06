import java.util.List;
import java.util.Random;

/**
 * Класс-наследник, представляющий планшет.
 */
public class Tablet extends NetworkDevice {

    // Уникальные свойства (п. 5)
    private boolean hasStylus;
    private int storageGB;
    private double weightKg;
    private String screenResolution;

    public Tablet(String manufacturer) {
        super(manufacturer, List.of("Wi-Fi", "Bluetooth"));
        this.hasStylus = false;
        this.storageGB = 128;
        this.weightKg = 0.5;
        this.screenResolution = "2K";
    }

    // --- Уникальные методы (п. 5) ---
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

    // --- Реализация абстрактных методов ---
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

    @Override
    public void randomizeProperties() {
        Random rand = new Random();
        this.hasStylus = rand.nextBoolean();
        this.storageGB = rand.nextInt(64, 1025);
        this.weightKg = 0.3 + (1.5 - 0.3) * rand.nextDouble();
    }

    @Override
    public String getDetailedInfo() {
        return String.format("Планшет [ID: %s, Производитель: %s, Стилус: %s, Память: %d ГБ, Вес: %.2f кг, Экран: %s]",
                id.substring(0, 8), manufacturer, hasStylus ? "Да" : "Нет", storageGB, weightKg, screenResolution);
    }

    // --- Сеттеры для изменения свойств (п. 3.3) ---
    public void setStorageGB(int storageGB) { this.storageGB = storageGB; }
    public void setScreenResolution(String screenResolution) { this.screenResolution = screenResolution; }
}