package com.easyclient.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class EasyClientMod implements ClientModInitializer {

    public static KeyBinding openMenuKey;
    private boolean wasPressed = false;

    @Override
    public void onInitializeClient() {
        openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.easyclient.open_menu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_CONTROL,
                "category.easyclient"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            boolean isPressed = openMenuKey.isPressed();
            if (isPressed && !wasPressed) {
                if (client.player != null && client.currentScreen == null) {
                    client.setScreen(new EasyClientScreen());
                }
            }
            wasPressed = isPressed;
        });
    }
}
