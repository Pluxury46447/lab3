import java.util.List;

// Создан класс-наследник, реализованы абстрактные методы родителя
public class Smartphone extends NetworkDevice {

    public Smartphone(String manufacturer) {
        super(manufacturer, List.of("Wi-Fi", "Bluetooth"));
    }

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

    // Заглушки для методов из п. 4 (будут реализованы позже)
    @Override
    public void randomizeProperties() {
        // Пока ничего не делает
    }

    @Override
    public String getDetailedInfo() {
        return "Смартфон (детальная информация появится позже)";
    }
}