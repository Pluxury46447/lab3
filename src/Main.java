import java.util.Scanner;

void main() {
    Scanner scanner = new Scanner(System.in);
    DeviceManager manager = new DeviceManager();//объект который хранит список устройств. все операции идут через него

    // Предзаполнение списка. полиморфизм.эд девайс принимает нетворк девайс, но передаём наследников
    manager.addDevice(new Smartphone("Apple"));
    manager.addDevice(new Tablet("Samsung"));
    manager.addDevice(new Computer("ASUS"));

    while (true) {
        System.out.println("\n=== МЕНЮ УПРАВЛЕНИЯ СЕТЕВЫМИ УСТРОЙСТВАМИ ===");
        System.out.println("1. Добавить устройство");
        System.out.println("2. Удалить устройство");
        System.out.println("3. Изменить свойства устройства");
        System.out.println("4. Функциональная работа с устройствами (Подключение/Отключение/Сопряжение)");
        System.out.println("5. Показать все устройства");
        System.out.println("6. Рандомизировать свойства устройств");
        System.out.println("7. Симуляция передачи данных");
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
                    //полиморфизм: мэнэджер эд девайс принимает нетворк девайс но ма передаём наследников
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
            case "3" -> {
                // ИЗМЕНЕНИЕ СВОЙСТВ
                manager.showAllDevices();
                System.out.print("Введите ID устройства для изменения: ");
                String id = scanner.nextLine();
                var dev = manager.findDevice(id);

                if (dev == null) {
                    System.out.println("Устройство не найдено.");
                    break;
                }

                System.out.println("Выбрано устройство: " + dev.getManufacturer());
                System.out.print("Изменить производителя? (1-Да, 0-Нет): ");
                if (scanner.nextLine().equals("1")) {
                    System.out.print("Введите нового производителя: ");
                    dev.setManufacturer(scanner.nextLine());
                }

                // Проверяем тип устройства с помощью instanceof
                if (dev instanceof Smartphone phone) {
                    System.out.println("Свойства смартфона");
                    System.out.print("Новый заряд батареи (0-100): ");
                    phone.setBatteryLevel(Integer.parseInt(scanner.nextLine()));

                    System.out.print("Новое разрешение камеры (Мп): ");
                    phone.setCameraMegapixels(Integer.parseInt(scanner.nextLine()));

                    System.out.print("Новая версия ОС: ");
                    phone.setOsVersion(scanner.nextLine());
                    System.out.println("Свойства смартфона обновлены.");

                } else if (dev instanceof Tablet tablet) {
                    System.out.println("Свойства планшета");
                    System.out.print("Новый объем памяти (ГБ): ");
                    tablet.setStorageGB(Integer.parseInt(scanner.nextLine()));

                    System.out.print("Новое разрешение экрана: ");
                    tablet.setScreenResolution(scanner.nextLine());
                    System.out.println("Свойства планшета обновлены.");

                } else if (dev instanceof Computer comp) {
                    System.out.println("Свойства компьютера");
                    System.out.print("Новый объем ОЗУ (ГБ): ");
                    comp.setRamGB(Integer.parseInt(scanner.nextLine()));

                    System.out.print("Новая модель процессора: ");
                    comp.setCpuModel(scanner.nextLine());
                    System.out.println("Свойства компьютера обновлены.");
                }
            }
            case "4" -> {
                // ФУНКЦИОНАЛЬНАЯ РАБОТА
                manager.showAllDevices();
                System.out.print("Введите ID устройства для операций: ");
                String id = scanner.nextLine();
                var dev = manager.findDevice(id);
                if (dev == null) { System.out.println("Не найдено."); break; }

                System.out.print("1-Подключиться к интерфейсу, 2-Отключиться, 3-Сопряжение, 4-Уникальное действие: ");
                String subChoice = scanner.nextLine();
                switch (subChoice) {
                    case "1" -> {
                        System.out.print("Интерфейс (Wi-Fi/Bluetooth/Ethernet): ");
                        dev.connect(scanner.nextLine());
                    }
                    case "2" -> dev.disconnect();
                    case "3" -> {
                        System.out.print("Введите ID второго устройства для сопряжения: ");
                        var dev2 = manager.findDevice(scanner.nextLine());
                        if (dev2 != null) dev.pairWith(dev2);
                        else System.out.println("Второе устройство не найдено.");
                    }
                    // ВЫЗОВ УНИКАЛЬНЫХ МЕТОДОВ
                    case "4" -> {
                        if (dev instanceof Smartphone phone) {
                            System.out.println("Действия смартфона");
                            System.out.println("1. Сделать фото");
                            System.out.println("2. Установить приложение");
                            System.out.println("3. Обновить ОС");
                            System.out.println("4. Зарядить батарею");
                            System.out.print("Выберите действие: ");
                            String action = scanner.nextLine();
                            switch (action) {
                                case "1" -> phone.takePhoto();
                                case "2" -> {
                                    System.out.print("Название приложения: ");
                                    phone.installApp(scanner.nextLine());
                                }
                                case "3" -> {
                                    System.out.print("Новая версия ОС: ");
                                    phone.updateOS(scanner.nextLine());
                                }
                                case "4" -> phone.chargeBattery();
                                default -> System.out.println("Неверный выбор.");
                            }
                        } else if (dev instanceof Tablet tablet) {
                            System.out.println(" Действия планшета ");
                            System.out.println("1. Рисовать");
                            System.out.println("2. Смотреть фильм");
                            System.out.println("3. Подключить стилус");
                            System.out.println("4. Расширить память");
                            System.out.print("Выберите действие: ");
                            String action = scanner.nextLine();
                            switch (action) {
                                case "1" -> tablet.drawSketch();
                                case "2" -> tablet.watchMovie();
                                case "3" -> tablet.connectStylus();
                                case "4" -> {
                                    System.out.print("На сколько ГБ расширить: ");
                                    tablet.expandStorage(Integer.parseInt(scanner.nextLine()));
                                }
                                default -> System.out.println("Неверный выбор.");
                            }
                        } else if (dev instanceof Computer comp) {
                            System.out.println(" Действия компьютера ");
                            System.out.println("1. Скомпилировать код");
                            System.out.println("2. Запустить игру");
                            System.out.println("3. Апгрейд ОЗУ");
                            System.out.println("4. Подключить USB-устройство");
                            System.out.print("Выберите действие: ");
                            String action = scanner.nextLine();
                            switch (action) {
                                case "1" -> comp.compileCode();
                                case "2" -> {
                                    System.out.print("Название игры: ");
                                    comp.playGame(scanner.nextLine());
                                }
                                case "3" -> {
                                    System.out.print("Сколько ГБ добавить: ");
                                    comp.upgradeRAM(Integer.parseInt(scanner.nextLine()));
                                }
                                case "4" -> {
                                    System.out.print("Название устройства: ");
                                    comp.connectExternalDevice(scanner.nextLine());
                                }
                                default -> System.out.println("Неверный выбор.");
                            }
                        }
                    }
                    default -> System.out.println("Неверный выбор.");
                }
            }
            case "5" -> manager.showAllDevices();
            case "6" -> {
                // РАНДОМИЗАЦИЯ
                manager.getDevices().forEach(NetworkDevice::randomizeProperties);
                System.out.println("Все свойства успешно рандомизированы!");
                manager.showAllDevices();
            }
            case "7" -> {
                // БОНУС
                manager.showAllDevices();
                System.out.print("ID источника: ");
                String src = scanner.nextLine();
                System.out.print("ID получателя: ");
                String trg = scanner.nextLine();
                manager.simulateDataTransfer(src, trg);
            }
            case "0" -> {
                System.out.println("Выход...");
                return;
            }
            default -> System.out.println("Неверный ввод.");
        }
    }
}