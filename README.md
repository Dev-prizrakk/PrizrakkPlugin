# PrizrakkPlugin - Pre-release


**Описания**
```
Плагин который будет совмещать в себе множество функций!
```


# Технической составляющие!
**API** ```SpigotAPI```, ```JavaCord Discord Api```\
**Зависимости** ```MySQL```\
**Сборщик** ```Maven```\

# Конфигурации
**message.yml**
```yml
message:
  system:
    prefix: §6[§4PrizrakkPlugin§6] §f
    no-perm: У вас не прав на эту команду!
    reload: Плагин перезапущен!
    no-console: Команда доступна только игроку!
    offline: Этот игрок оффлайн
    error: Произошла ошибка при выполнение команды!
  admin:
    add-warn: 'Вы успешно выдали варн игроку: %player%'
    del-warn: 'Вы успешно удалили варн игроку: %player%'
    add-warn-view: Вы получили варн! от Администратора %admin% !
    del-warn-view: Вам сняли варн! Ведите себя хорошо!
    reason:
      warn-count: Вы были кикнуты т.к у вас допущено более %warncount% варнов
  rep:
    add-rep: 'Вы успешно выдали репутацию игроку: %target-player%'
    del-rep: 'Вы успешно удалили репутацию игроку: %target-player%'
    add-rep-view: Вы получили репутацию! от игрока %player% !
    del-rep-view: Вам сняли репутацию это плохо :(!
  time:
    day: Установлено дневное время
    night: Установлено ночное время
  event:
    player-join: '%prefix% %player% зашел на сервер'
    player-left: '%prefix% %player% вышел с сервера'
  other:
    health: Вы исцелены!
    healed: 'Вы исцелили: {player}'
    feed: Вы были покормлены!
    feeded: 'Вы покормили: {player}'
    gm: Ваш игровой режим изменился на %game-mode%
```
**prefix.yml** (Это пример заполнение этого файла там может быть и не 2 и не 3 а хоть 40 видов префикса) (Эта часть была создана для одного проекта как сюда попала не ебу честно слово XD)
```yml
president:
  prefix: §6§lПрезидент §f
  chat-format: '%prefix% %player% &6>>&f %message%'
default:
  prefix: §8§lЖитель §f
  chat-format: '%prefix% %player% &6>>&f %message%'
```
**config.yml** 
```yml
# § для использования цветов
# Версия конфигиа 0.3

config:
  enable: true
  debug: false
  warncount: 3
  console-out-log-command: true
  mysql:
    user: "bebra"
    password: "zebra"
    url: "jdbc:mysql://bebrahub.com:3306/bebra?autoReconnect=true"
# это бетта версия и дискорд нихуя не робит или робит но через N-вермя отключает плагин
  discord:
    enable: false
    token: ""
    chat: ""
    console: ""
```
# Команды
/heal /feed лечат кормят (также работает с пингом пример /heal prizrakk)\
/admin главная админ команда на которой следующие команды будут зависить\
    - addwarn prizrakk - дает варн игроку (ВНИМАНИЕ БЕЗ БАЗЫ ДАННЫХ АДМИН КОМАНДЫ РАБОТАТЬ НЕ БУДУТ КАК И СТАТИСТИКА)\
    - delwarn prizrakk - удаляет варн\
/prizrakk - главная команда \
    - help - список команд и также прав\
    - reload - перезагружает конфигурации\
/stats - статистика игрока работает еще с пингом /stats prizrakk (НЕ РАБОТАЕТ БЕЗ БАЗЫ ДАННЫХ)\

# ВНИМАНИЕ!!!!!
Плагин сырой не везде применен цветовой код & где-то может быть
# Ссылки

[**Discord Support**](https://discord.gg/U6H9Zw7Fhg)
