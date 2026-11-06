import java.util.List;
import java.util.Random;
/**
 * Класс-наследник, представляющий планшет.
 */
//наследование. как и смартфон явл сетевым устройством
public class Tablet extends NetworkDevice {
    // Уникальные свойства инкапсуляция
    private boolean hasStylus;//есть стилус
    private int storageGB;
    private double weightKg;//в кг дробное число
    private String screenResolution;//разр экрана
//вызо конструктора родителя.знач по умолчанию
    public Tablet(String manufacturer) {
        super(manufacturer, List.of("Wi-Fi", "Bluetooth"));
        this.hasStylus = false;
        this.storageGB = 128;
        this.weightKg = 0.5;
        this.screenResolution = "2K";
    }

    //Уникальные методы
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
//меняет состояние стилуса
    public void connectStylus() {
        this.hasStylus = true;
        System.out.println("Стилус подключен.");
    }
//увелечение памяти
    public void expandStorage(int extraGB) {
        this.storageGB += extraGB;
        System.out.println("Память расширена до " + storageGB + " ГБ.");
    }

    // Реализация абстрактных методов.переопределение
    @Override//аннотация.проверяем что мы переопр метод родителя
    public void connect(String interfaceName) {
        if (networkInterfaces.contains(interfaceName)) {
            this.isConnected = true;//если есть интерфейс в списке
            System.out.println("Планшет подключен к " + interfaceName);
        } else {
            System.out.println("Планшет не поддерживает " + interfaceName);
        }
    }
//отключаем и очищ список сопр устр
    @Override
    public void disconnect() {
        this.isConnected = false;
        this.connectedDeviceIds.clear();
        System.out.println("Планшет отключен от сети.");
    }
//полиморфизм.параметр  озер. сюда можно передать смартфон и комп
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
//меняем на случ значения
    //rand генерирует случ или целое число или дробное
    @Override
    public void randomizeProperties() {
        Random rand = new Random();
        this.hasStylus = rand.nextBoolean();
        this.storageGB = rand.nextInt(64, 1025);
        this.weightKg = 0.3 + (1.5 - 0.3) * rand.nextDouble();
    }
//собираем все данные и свои и родительские
    @Override
    public String getDetailedInfo() {
        return String.format("Планшет [ID: %s, Производитель: %s, Стилус: %s, Память: %d ГБ, Вес: %.2f кг, Экран: %s]",
                id.substring(0, 8), manufacturer, hasStylus ? "Да" : "Нет", storageGB, weightKg, screenResolution);
    }

    // Сеттеры для изменения свойств
    public void setStorageGB(int storageGB) { this.storageGB = storageGB; }
    public void setScreenResolution(String screenResolution) { this.screenResolution = screenResolution; }
}