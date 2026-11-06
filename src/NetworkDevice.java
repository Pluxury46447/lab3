import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * Абстрактный класс, представляющий сетевое устройство.
 */
public abstract class NetworkDevice {
    // Уникальный идентификатор устройства
    protected final String id;
    protected String manufacturer;
    protected boolean isConnected;

    // Список идентификаторов устройств, к которым подключено данное устройство
    protected List<String> connectedDeviceIds;
    // Список поддерживаемых сетевых интерфейсов
    protected List<String> networkInterfaces;
    public NetworkDevice(String manufacturer, List<String> networkInterfaces) {
        this.id = UUID.randomUUID().toString();
        this.manufacturer = manufacturer;
        this.isConnected = false;
        this.connectedDeviceIds = new ArrayList<>();
        this.networkInterfaces = new ArrayList<>(networkInterfaces);
    }
    //Абстрактные методы
    public abstract void connect(String interfaceName);
    public abstract void disconnect();
    public abstract void pairWith(NetworkDevice other);