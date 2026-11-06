import java.util.List;

// Создание класса-наследника Computer
public class Computer extends NetworkDevice {

    public Computer(String manufacturer) {
        // Компьютер поддерживает Ethernet, Wi-Fi и Bluetooth
        super(manufacturer, List.of("Ethernet", "Wi-Fi", "Bluetooth"));
    }

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

    // Заглушки
    @Override
    public void randomizeProperties() {
        // Пока ничего
    }

    @Override
    public String getDetailedInfo() {
        return "Компьютер (детальная информация появится позже)";
    }
}