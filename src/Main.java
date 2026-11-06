import java.util.Scanner;

void main() {
    Scanner scanner = new Scanner(System.in);
    DeviceManager manager = new DeviceManager();

    manager.addDevice(new Smartphone("Apple"));
    manager.addDevice(new Tablet("Samsung"));
    manager.addDevice(new Computer("ASUS"));

    while (true) {
        System.out.println("\n=== МЕНЮ УПРАВЛЕНИЯ СЕТЕВЫМИ УСТРОЙСТВАМИ ===");
        System.out.println("1. Добавить устройство");
        System.out.println("2. Удалить устройство");
        System.out.println("3. Изменить свойства устройства");
        System.out.println("4. Функциональная работа с устройствами");
        System.out.println("5. Показать все устройства");
        System.out.println("6. Рандомизировать свойства устройств");
        System.out.println("7. [Бонус] Симуляция передачи данных");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1" -> {
                // ДОБАВЛЕНИЕ
                System.out.print("Тип устройства (1-Смартфон, 2-Планшет, 3-Компьютер): ");
                String type = scanner.nextLine();
                System.out.print("Производитель: ");
                String manufacturer = scanner.nextLine();
                switch (type) {
                    case "1" -> manager.addDevice(new Smartphone(manufacturer));
                    case "2" -> manager.addDevice(new Tablet(manufacturer));
                    case "3" -> manager.addDevice(new Computer(manufacturer));
                    default -> System.out.println("Неверный тип.");
                }
                System.out.println("Устройство добавлено.");
            }
            case "2" -> {
                // УДАЛЕНИЕ
                manager.showAllDevices();
                System.out.print("Введите ID (или первые символы) для удаления: ");
                String id = scanner.nextLine();
                if (manager.removeDevice(id)) System.out.println("Удалено.");
                else System.out.println("Не найдено.");
            }
            case "3" -> System.out.println(">> Изменение свойств (в разработке)");
            case "4" -> System.out.println(">> Функциональная работа (в разработке)");
            case "5" -> manager.showAllDevices(); // ПРОСМОТР (п. 3.4)
            case "6" -> System.out.println(">> Рандомизация (в разработке)");
            case "7" -> System.out.println(">> Симуляция передачи (в разработке)");
            case "0" -> {
                System.out.println("Выход...");
                return;
            }
            default -> System.out.println("Неверный ввод.");
        }
    }
}