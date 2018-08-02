### Info Collector

Collects information about an Android device, particularly Wi-Fi and network
related.

##### Tests

The application was tested on these platforms / devices

* Android 5.1.1, Zebra Technologies TC8000
* Android 8.1.0, Moto x4
* Android 8.?.?, Pixel (??)

##### Future Considerations

* Android Oreo (8.x) returns MAC address of 02:00:00:00:00:00 through the Wi-Fi
  Manager Service

* If Wi-Fi Manager provides an IP address, the network interface can be retrieved
  that matches the IP address. Once that network interface is obtained, the MAC
  address can be acquired through it.

* Getting the serial number requires READ_PHONE_STATE since Android 8.x. This
  sample code requests that permission if it cannot get the serial number through
  the deprecated Build.SERIAL. But requiring the READ_PHONE_STATE generates these
  questions:

  * Will there ever be a time (e.g., a version of Android) that will require the
    user to grant the permission before proceeding?
  * Will an MDM application be able to allow that? It looks like G Suite does:
    https://gsuiteupdates.googleblog.com/2018/02/manage-app-runtime-permissions.html

* The logic for getting the MAC address needs to
  * Account for when the value is 02:\*. If so, try to get it through the network
    interface.
  * When everything fails and the MAC value is 02:\*, null, empty, or some other
    unknown or unusual value, then set it to "N/A".
  * Account for when the Wi-Fi is off. Can the MAC address even be read through
    the Wi-Fi Management Service or the Network Interface?
  * Account for when the Wi-Fi is on but unable to connect to Wi-Fi which means
    the IP address is unknown and the version of Android doesn't provide a MAC
    address through the Wi-Fi service. Could it use the enumerated list of network
    interfaces and search for "wlan0" and use the MAC address from it? This
    assumes that "wlan0" is what Android always uses (and will use) to name the
    network interface. It looks like `ip route` can be used to detect the name
    of the network interface.
