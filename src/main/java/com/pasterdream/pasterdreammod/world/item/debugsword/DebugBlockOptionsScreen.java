package com.pasterdream.pasterdreammod.world.item.debugsword;

import com.pasterdream.pasterdreammod.helper.renderhelper.GUIBackGroundRender;
import com.pasterdream.pasterdreammod.init.ModNetwork;
import com.pasterdream.pasterdreammod.network.debugsword.DebugBlockActionPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.lwjgl.glfw.GLFW;

public class DebugBlockOptionsScreen extends Screen
{
    private final BlockPos pos;
    private final Player player;
    private final Level level;

    public DebugBlockOptionsScreen(BlockPos pos, Player player, Level level)
    {
        super(Component.empty());
        this.pos = pos;
        this.player = player;
        this.level = level;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick)
    {
        GUIBackGroundRender.rendMinecraftGUIBackground(guiGraphics, width / 2 - 128, height / 2 - 64, 256, 128);

        guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable("option.pasterdream.Q: 模拟无工具破坏"), width / 2 - 122, height / 2 - 58, 0x000000, false);
        guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable("option.pasterdream.W: 模拟下界合金镐破坏"), width / 2 - 122, height / 2 - 48, 0x000000, false);
        guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable("option.pasterdream.E: 模拟下界合金镐时运III破坏"), width / 2 - 122, height / 2 - 38, 0x000000, false);
        guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable("option.pasterdream.A: 模拟下界合金镐精准采集破坏"), width / 2 - 122, height / 2 - 28, 0x000000, false);
        guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable("option.pasterdream.S: 获取对应的BlockItem并掉落，并将方块设置为空气"), width / 2 - 122, height / 2 - 18, 0xFF0000, false);
        guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable("option.pasterdream.D: 将方块设置为空气的同时不触发方块更新"), width / 2 - 122, height / 2 - 8, 0xFF0000, false);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE)
        {
            this.onClose();
            return true;
        }

        int option = switch (keyCode)
        {
            case GLFW.GLFW_KEY_Q -> 0;
            case GLFW.GLFW_KEY_W -> 1;
            case GLFW.GLFW_KEY_E -> 2;
            case GLFW.GLFW_KEY_A -> 3;
            case GLFW.GLFW_KEY_S -> 4;
            case GLFW.GLFW_KEY_D -> 5;
            default -> -1;
        };

        if (option >= 0)
        {
            ModNetwork.CHANNEL.sendToServer(new DebugBlockActionPacket(pos, option));
            this.onClose();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean isPauseScreen()
    {
        return false;
    }
}
