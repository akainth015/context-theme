package me.akainth

import com.intellij.ide.actions.QuickChangeLookAndFeel
import com.intellij.ide.ui.LafManager
import com.intellij.ide.ui.UIThemeProvider
import com.intellij.ide.ui.laf.UIThemeBasedLookAndFeelInfo
import com.intellij.ide.ui.laf.darcula.DarculaLookAndFeelInfo

fun switchToLightMode() {
    for (provider in UIThemeProvider.EP_NAME.extensionList) {
        if ("JetBrainsLightTheme" == provider.id) {
            val theme = provider.createTheme()
            if (theme != null) {
                QuickChangeLookAndFeel.switchLafAndUpdateUI(LafManager.getInstance(), UIThemeBasedLookAndFeelInfo(theme), true)
            }
        }
    }
}

fun switchToDarkMode() {
    QuickChangeLookAndFeel.switchLafAndUpdateUI(LafManager.getInstance(), DarculaLookAndFeelInfo(), true)
}
