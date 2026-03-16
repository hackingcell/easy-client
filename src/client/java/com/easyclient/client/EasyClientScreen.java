package com.easyclient.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class EasyClientScreen extends Screen {

    private TextFieldWidget commandField;
    private final List<String> history = new ArrayList<>();
    private int historyIndex = -1;

    private static final String[] VERSIONS = {
        "1.21.1", "1.21.2", "1.21.3", "1.21.4",
        "1.21.5", "1.21.6", "1.21.7", "1.21.8",
        "1.21.9", "1.21.10", "1.21.11"
    };
    private int selectedVersionIndex = 0;

    private static final int PANEL_WIDTH  = 420;
    private static final int PANEL_HEIGHT = 160;

    public EasyClientScreen() {
        super(Text.literal("Easy Client"));
    }

    @Override
    protected void init() {
        int px = (this.width  - PANEL_WIDTH)  / 2;
        int py = (this.height - PANEL_HEIGHT) / 2;

        this.addDrawableChild(ButtonWidget.builder(Text.literal("<"), btn -> {
            selectedVersionIndex = (selectedVersionIndex - 1 + VERSIONS.length) % VERSIONS.length;
        }).dimensions(px + 10, py + 35, 20, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal(">"), btn -> {
            selectedVersionIndex = (selectedVersionIndex + 1) % VERSIONS.length;
        }).dimensions(px + 130, py + 35, 20, 20).build());

        commandField = new TextFieldWidget(
                this.textRenderer,
                px + 10, py + 70,
                PANEL_WIDTH - 20, 20,
                Text.literal("Command")
        );
        commandField.setMaxLength(256);
        commandField.setPlaceholder(Text.literal("Enter command (with or without /)..."));
        commandField.setFocused(true);
        this.addSelectableChild(commandField);
        this.setFocused(commandField);

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Send"), btn -> sendCommand())
                .dimensions(px + PANEL_WIDTH - 90, py + 105, 80, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Close"), btn -> this.close())
                .dimensions(px + 10, py + 105, 80, 20).build());
    }

    @Override
    public void render(DrawContext ctx, int mx, int my, float delta) {
        this.renderBackground(ctx, mx, my, delta);

        int px = (this.width  - PANEL_WIDTH)  / 2;
        int py = (this.height - PANEL_HEIGHT) / 2;

        ctx.fill(px, py, px + PANEL_WIDTH, py + PANEL_HEIGHT, 0xD0000000);
        ctx.drawBorder(px, py, PANEL_WIDTH, PANEL_HEIGHT, 0xFF444444);

        ctx.drawCenteredTextWithShadow(this.textRenderer,
                Text.literal("§b✦ Easy Client"), this.width / 2, py + 10, 0xFFFFFF);

        ctx.drawTextWithShadow(this.textRenderer,
                Text.literal("§7Server Version:"), px + 10, py + 22, 0xAAAAAA);

        String ver = VERSIONS[selectedVersionIndex];
        int verX = px + 10 + 20 + ((120 - this.textRenderer.getWidth(ver)) / 2);
        ctx.drawTextWithShadow(this.textRenderer,
                Text.literal("§e" + ver), verX, py + 41, 0xFFFFFF);

        ctx.drawTextWithShadow(this.textRenderer,
                Text.literal("§7Command:"), px + 10, py + 58, 0xAAAAAA);

        commandField.render(ctx, mx, my, delta);
        super.render(ctx, mx, my, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 257 || keyCode == 335) {
            sendCommand();
            return true;
        }
        if (keyCode == 265 && !history.isEmpty()) {
            historyIndex = Math.min(historyIndex + 1, history.size() - 1);
            commandField.setText(history.get(historyIndex));
            return true;
        }
        if (keyCode == 264) {
            historyIndex = Math.max(historyIndex - 1, -1);
            commandField.setText(historyIndex == -1 ? "" : history.get(historyIndex));
            return true;
        }
        if (keyCode == 256) {
            this.close();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private void sendCommand() {
        String raw = commandField.getText().trim();
        if (raw.isEmpty()) return;

        String command = raw.startsWith("/") ? raw.substring(1) : raw;

        if (this.client != null && this.client.getNetworkHandler() != null) {
            this.client.getNetworkHandler().sendChatCommand(command);
            history.add(0, raw);
            if (history.size() > 20) history.remove(history.size() - 1);
            historyIndex = -1;
            commandField.setText("");
            this.close();
        }
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
