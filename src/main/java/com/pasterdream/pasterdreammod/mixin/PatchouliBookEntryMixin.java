package com.pasterdream.pasterdreammod.mixin;

import com.pasterdream.pasterdreammod.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.patchouli.client.book.BookEntry;
import vazkii.patchouli.common.book.Book;

/**
 * 让《旧梦归引》指南书（帕秋莉）在创造模式下默认解锁全部词条。
 * <p>
 * 帕秋莉的 {@code BookEntry#isLocked} 是所有「词条是否锁定」判定的唯一汇聚点（分类树、目录、
 * 词条按钮、词条打开逻辑、悬浮提示等全部走它），因此在 HEAD 处短路即可一次性解锁全部内容。
 * 用 HEAD + cancellable 而非 @Overwrite，可保留原方法体与其他模组对帕秋莉的注入。
 */
@Mixin(value = BookEntry.class, remap = false)
public class PatchouliBookEntryMixin
{
    /** 本模组指南书在帕秋莉中的 ID（对应 data/pasterdream/patchouli_books/seniors_dream/book.json）。 */
    private static final ResourceLocation PASTERDREAM_BOOK_ID = ResourceLocation.fromNamespaceAndPath("pasterdream", "seniors_dream");

    //创造模式下默认解锁《旧梦归引》全部词条，由配置项 unlockAllPatchouliEntriesInCreative 控制。
    //只对本模组的书生效，不影响其他模组（或其引用原版进度）的帕秋莉书。
    @Inject(method = "isLocked", at = @At("HEAD"), cancellable = true)
    private void pasterdream$unlockAllEntriesInCreative(CallbackInfoReturnable<Boolean> cir)
    {
        if (!Config.unlockAllPatchouliEntriesInCreative) return;

        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || !player.isCreative()) return;

        Book book = ((BookEntry) (Object) this).getBook();
        if (book == null || !PASTERDREAM_BOOK_ID.equals(book.id)) return;

        cir.setReturnValue(false);
    }
}
