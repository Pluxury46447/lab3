import java.util.List;
import java.util.Random;
/**
 * Класс-наследник, представляющий смартфон.
 */
//extends - наследование.смартфон является разновидностью сет устройства.
// он забирает себе все поля и методы из networkDevice
public class Smartphone extends NetworkDevice {
    // Уникальные свойства (п. 5)
    private int batteryLevel;//инкапс.изменить можно только сеттерами
    private int cameraMegapixels;
    private String osVersion;
    private double screenSize;
    //конструктор. вызывается при new.
    public Smartphone(String manufacturer) {
        super(manufacturer, List.of("Wi-Fi", "Bluetooth"));//вызов констр родителя. передаём производителяи задаём список инт
        this.batteryLevel = 100;
        this.cameraMegapixels = 12;
        this.osVersion = "Android 14";
        this.screenSize = 6.1;
    }

    // Уникальные методы
    public void takePhoto() {
        if (batteryLevel > 5) {//если ок
            batteryLevel -= 5;//тратим 5 единиц
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

    //Реализация абстрактных методов,переопределение методов,которые были добавлены в родителе
    @Override//аннотация
    public void connect(String interfaceName) {
        if (networkInterfaces.contains(interfaceName)) {
            this.isConnected = true;//если вай фай есть в списке networkInterfaces.есть - тру
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

    //полиморфизм. параметр озер имеет тип нетворкдевайс.сюда можно передать планшет и пк
    //берём айди другого и кладём себе в список.
    @Override
    public void pairWith(NetworkDevice other) {
        if (this.isConnected && other.isConnected()) {
            this.connectedDeviceIds.add(other.getId());
            other.getConnectedDeviceIds().add(this.getId());//а свой айди отдаём ему
            System.out.println("Смартфон сопряжен с устройством: " + other.getManufacturer());
        } else {
            System.out.println("Оба устройства должны быть подключены к сети для сопряжения.");
        }
    }
    //меняем значения на случайные в заданных границах
    @Override
    public void randomizeProperties() {
        Random rand = new Random();
        this.batteryLevel = rand.nextInt(1, 101);
        this.cameraMegapixels = rand.nextInt(8, 109);
        this.screenSize = 5.0 + (10.0 - 5.0) * rand.nextDouble();
    }
    //собиреам данные в одну строчку (свои и родительские)
    @Override
    public String getDetailedInfo() {
        return String.format("Смартфон [ID: %s, Производитель: %s, Заряд: %d%%, Камера: %d Мп, ОС: %s, Экран: %.1f\"]",
                id.substring(0, 8), manufacturer, batteryLevel, cameraMegapixels, osVersion, screenSize);
    }//обрезаем до 8 символов для читаемости


    // Сеттеры для изменения свойств
    public void setBatteryLevel(int batteryLevel) {
        if (batteryLevel >= 0 && batteryLevel <= 100) {
            this.batteryLevel = batteryLevel;
        } else {
            System.out.println("Ошибка: заряд должен быть от 0 до 100! Значение не изменено.");
        }
    }
    // верхняя граница 500 Мп
    public void setCameraMegapixels(int cameraMegapixels) {
        if (cameraMegapixels > 0 && cameraMegapixels <= 500) {
            this.cameraMegapixels = cameraMegapixels;
        } else {
            System.out.println("Ошибка: разрешение камеры должно быть от 1 до 500 Мп! Значение не изменено.");
        }
    }

    public void setOsVersion(String osVersion) {
        if (osVersion != null && !osVersion.isBlank()) {
            this.osVersion = osVersion;
        } else {
            System.out.println("Ошибка: версия ОС не может быть пустой! Значение не изменено.");
        }
    }
}