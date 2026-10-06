# XojoUsbAttach

A tiny Android library that lets a **Xojo Android** app open, with the USB permission already granted, when a USB serial device is plugged in: a Meshtastic node, an ESP32 or RP2040 board, a CP210x, CH34x, FTDI or PL2303 adapter.

Without it, Android asks for the USB permission every time the device is plugged in again. With it, Android asks once "Open *your app* to handle *the device*?"; choosing the app grants the permission, and with **Always** the app opens by itself on every later plug, without any question.

It's made for Xojo, whose Android builds can't add a manifest intent filter or a resource file, but it works in any Android app.

## Use

In Xojo: **Build Settings → Android → Dependencies**, add the line:

```
com.github.Kongduino:XojoUsbAttach:1.0.0
```

That's all: Gradle (through [JitPack](https://jitpack.io/#Kongduino/XojoUsbAttach)) merges the library's manifest and resources into the app. Nothing to call from Xojo code. To talk to the device, use for example [usb-serial-for-android](https://github.com/mik3y/usb-serial-for-android), or the `USBSerial` class of [MQTT_Xojo](https://github.com/Kongduino/MQTT_Xojo), which wraps it.

## What's inside

- `UsbAttachedActivity`: an invisible activity with the `android.hardware.usb.action.USB_DEVICE_ATTACHED` intent filter. Android starts it when a listed device is plugged in; it opens the app's own launcher activity (or brings the running app to the front) and closes. It knows nothing about the app, so the same library works in any app.
- `res/xml/usbattach_device_filter.xml`: the devices it reacts to, by USB vendor id:

  | Vendor id | Maker |
  |---|---|
  | 0x239A | Adafruit (nRF52 boards: RAK4631, T-Echo, T1000-E...) |
  | 0x2886 | Seeed Studio |
  | 0x303A | Espressif (ESP32-S2/S3/C3 native USB) |
  | 0x2E8A | Raspberry Pi (RP2040, RP2350) |
  | 0x1915 | Nordic Semiconductor |
  | 0x10C4 | Silicon Labs CP210x |
  | 0x1A86 | WCH CH34x |
  | 0x0403 | FTDI |
  | 0x067B | Prolific PL2303 |

- `<uses-feature android:name="android.hardware.usb.host" android:required="false" />`.

For another device, open an issue or fork and add its vendor id (decimal) to the filter.

## Notes

- The permission is granted per device and per plug: Android forgets it when the device is unplugged, which is why the "Always" choice matters.
- The app must still ask for the permission itself (`UsbManager.requestPermission`) when it was already running before the device was plugged in and the user didn't go through the dialog.

## License

GPL-3.0, like [MQTT_Xojo](https://github.com/Kongduino/MQTT_Xojo).
