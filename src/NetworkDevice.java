import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * Абстрактный класс, представляющий сетевое устройство.
 */
//инкапсуляция: поля скрыты от прямого доступа. protected = доступен наследникам, но не всем подряд
public abstract class NetworkDevice {
    // Уникальный идентификатор устройства
    //протектед-поле видно внутри классов наследников и внутри самого класса
    //файнал-константа, айди не должке меняться
    //manufacturer произв
    protected final String id;
    protected String manufacturer;
    protected boolean isConnected;
    // Список идентификаторов устройств, к которым подключено данное устройство
    protected List<String> connectedDeviceIds;
    // Список поддерживаемых сетевых интерфейсов
    protected List<String> networkInterfaces;
    // КОНСТРУКТОР: ген уник id.Инициализирует поля при создании объекта.вызывается автоматически при new (внутри насл через super()
    public NetworkDevice(String manufacturer, List<String> networkInterfaces) {
        this.id = UUID.randomUUID().toString();
        this.manufacturer = manufacturer;
        this.isConnected = false;
        this.connectedDeviceIds = new ArrayList<>();
        this.networkInterfaces = new ArrayList<>(networkInterfaces);
    }
    // абстрактные методы
    public abstract void connect(String interfaceName);//подкл к сетевому, стринг инт - принимает назв интр инт
    public abstract void disconnect();//откл
    public abstract void pairWith(NetworkDevice other);//сопр(полиморфизм: можно передать сюда смартфон планшет комп)
    public abstract String getDetailedInfo();//общая инфа об устр
    public abstract void randomizeProperties();//меняет свойство на случ вел

    //Геттеры и сеттеры.инкапсуляция.доступ к полям через методы(из других классов напр прочитать нельзя)
    public String getId() { return id; }
    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
    public boolean isConnected() { return isConnected; }
    //возвр ссылку на список(если добавить извек то через этот метод)
    public List<String> getConnectedDeviceIds() { return connectedDeviceIds; }
    public List<String> getNetworkInterfaces() { return networkInterfaces; }
}