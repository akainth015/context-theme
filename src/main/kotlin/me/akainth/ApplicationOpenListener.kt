package me.akainth

import com.intellij.ide.ApplicationInitializedListener
import com.intellij.openapi.application.Application
import com.intellij.openapi.util.SystemInfo
import com.registry.RegistryKey
import com.registry.RegistryWatcher
import java.io.File

class ApplicationOpenListener : ApplicationInitializedListener {
    override fun componentsInitialized() {
        if (SystemInfo.isWin10OrNewer) {
            val arch = System.getProperty("os.arch", "x86")
            val library = if (arch.equals("x86", ignoreCase = true)) "reg.dll" else "reg_x64.dll"
            javaClass.getResourceAsStream("/$library").also { libraryStream ->
                val regDll = File.createTempFile("reg", ".dll")
                val out = regDll.outputStream()
                libraryStream.copyTo(out)
                libraryStream.close()
                out.close()
                System.load(regDll.absolutePath)
            }

            RegistryWatcher.addRegistryListener(WindowsRegistryListener())
            val personalizeKey = RegistryKey("SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize")
            if (personalizeKey.getValue("AppsUseLightTheme").byteData.first() - 1 == 0) {
                switchToLightMode()
            } else {
                switchToDarkMode()
            }
            RegistryWatcher.watchKey(personalizeKey)
        }
    }
}
