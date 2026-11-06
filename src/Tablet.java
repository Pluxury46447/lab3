import java.util.List;

// Создание класса-наследника Tablet
public class Tablet extends NetworkDevice {

    public Tablet(String manufacturer) {
        super(manufacturer, List.of("Wi-Fi", "Bluetooth"));
    }

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

    // Заглушки (будут реализованы позже)
    @Override
    public void randomizeProperties() {
    }

    @Override
    public String getDetailedInfo() {
        return "Планшет (детальная информация появится позже)";
    }
}