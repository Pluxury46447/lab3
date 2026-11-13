import java.util.List;
import java.util.Random;

/**
 * Класс-наследник, представляющий компьютер.
 */
//компьютер получает от родителя айди производ списки и обяз реал абстр методы
public class Computer extends NetworkDevice {
    // Уникальные свойства
    private int ramGB;
    private String cpuModel;
    private boolean hasDedicatedGPU;//есть ли дискр вид
    private int usbPorts;//колво свободных usb портов
    //super вызов констр родителя.комп передаёт три интр
    public Computer(String manufacturer) {
        super(manufacturer, List.of("Ethernet", "Wi-Fi", "Bluetooth"));
        this.ramGB = 16;
        this.cpuModel = "Intel Core i7";
        this.hasDedicatedGPU = true;
        this.usbPorts = 6;
    }

    //Уникальные методы
    //вывод о комп кода с указание модели проц и объёма озу
    public void compileCode() {
        System.out.println("Компиляция Java-кода на " + cpuModel + " (ОЗУ: " + ramGB + "ГБ)... Успешно!");
    }
    //проверка дискр видюхи для игр
    public void playGame(String gameName) {
        if (hasDedicatedGPU) {
            System.out.println("Запуск " + gameName + " на максимальных настройках графики.");
        } else {
            System.out.println("Запуск " + gameName + " на минимальных настройках (интегрированная графика).");
        }
    }
    //увелич объём
    // ДОБАВЛЕНО: проверка extraGB > 0
    public void upgradeRAM(int extraGB) {
        if (extraGB > 0) {
            this.ramGB += extraGB;
            System.out.println("ОЗУ увеличено до " + ramGB + " ГБ.");
        } else {
            System.out.println("Ошибка: количество ГБ для апгрейда должно быть положительным!");
        }
    }
    //пров есть ли свободные порты и если есть то подкл устройство и уменьш на 1 счётчик
    public void connectExternalDevice(String device) {
        if (usbPorts > 0) {
            usbPorts--;
            System.out.println("Подключено внешнее устройство: " + device + ". Свободных USB: " + usbPorts);
        } else {
            System.out.println("Нет свободных USB-портов!");
        }
    }

    // Реализация абстрактных методов
    @Override
    public void connect(String interfaceName) {
        //проверяем поддержку интерфейса
        if (networkInterfaces.contains(interfaceName)) {
            this.isConnected = true;
            System.out.println("Компьютер подключен к " + interfaceName);
        } else {
            System.out.println("Компьютер не поддерживает " + interfaceName);
        }
    }
    //откл от сети
    @Override
    public void disconnect() {
        this.isConnected = false;
        this.connectedDeviceIds.clear();
        System.out.println("Компьютер отключен от сети.");
    }
    //полиморфизм. сопряжение.
    @Override
    public void pairWith(NetworkDevice other) {
        //при том что если два устройства в сети
        if (this.isConnected && other.isConnected()) {
            this.connectedDeviceIds.add(other.getId());
            other.getConnectedDeviceIds().add(this.getId());
            System.out.println("Компьютер сопряжен с устройством: " + other.getManufacturer());
        } else {
            System.out.println("Оба устройства должны быть подключены к сети для сопряжения.");
        }
    }
    //на случайные знач
    @Override
    public void randomizeProperties() {
        Random rand = new Random();
        this.ramGB = rand.nextInt(4, 129);
        this.hasDedicatedGPU = rand.nextBoolean();
        this.usbPorts = rand.nextInt(2, 10);
    }
    //собираем в одну строчку
    @Override
    public String getDetailedInfo() {
        return String.format("Компьютер [ID: %s, Производитель: %s, CPU: %s, ОЗУ: %d ГБ, Видеокарта: %s, USB: %d]",
                id.substring(0, 8), manufacturer, cpuModel, ramGB, hasDedicatedGPU ? "Дискретная" : "Интегрированная", usbPorts);
    }

    //Сеттеры для изменения свойств
    //верхняя граница 1024 ГБ
    public void setRamGB(int ramGB) {
        if (ramGB > 0 && ramGB <= 1024) {
            this.ramGB = ramGB;
        } else {
            System.out.println("Ошибка: объём ОЗУ должен быть от 1 до 1024 ГБ! Значение не изменено.");
        }
    }

    public void setCpuModel(String cpuModel) {
        if (cpuModel != null && !cpuModel.isBlank()) {
            this.cpuModel = cpuModel;
        } else {
            System.out.println("Ошибка: модель процессора не может быть пустой! Значение не изменено.");
        }
    }

    // ЛАБА 4: сеттеры для чтения из файла
    public void setHasDedicatedGPU(boolean hasDedicatedGPU) {
        this.hasDedicatedGPU = hasDedicatedGPU;
    }

    public void setUsbPorts(int usbPorts) {
        if (usbPorts >= 0) {
            this.usbPorts = usbPorts;
        } else {
            System.out.println("Ошибка: количество USB-портов не может быть отрицательным! Значение не изменено.");
        }
    }
}