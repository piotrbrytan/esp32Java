# Raspberry PI
## Clean installation of Raspbian Lite
[https://www.raspberrypi.org/downloads/raspbian/](https://www.raspberrypi.org/downloads/raspbian/)

## Setting up a Raspberry Pi as a Wireless Access Point 
[https://www.raspberrypi.org/documentation/configuration/wireless/access-point.md](https://www.raspberrypi.org/documentation/configuration/wireless/access-point.md)

## Mosquitto installation
```bash
sudo apt install mosquitto
sudo apt install mosquitto-clients

# test messaging
mosquitto_sub -t "test"
mosquitto_pub -h 192.168.4.1 -m "hello" -t "test"
```
