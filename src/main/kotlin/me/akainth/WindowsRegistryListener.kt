package me.akainth

import com.registry.event.RegistryEvent
import com.registry.event.RegistryListener

class WindowsRegistryListener : RegistryListener {
    override fun notifyChange(event: RegistryEvent) {
        if (event.key.getValue("AppsUseLightTheme").byteData.first() - 1 == 0) {
            switchToLightMode()
        } else {
            switchToDarkMode()
        }
    }
}
