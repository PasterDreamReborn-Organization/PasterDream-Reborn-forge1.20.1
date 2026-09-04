package com.pasterdream.pasterdreammod.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 修正「直接注入 setTarget」导致的悬空仇恨目标。
 *
 * 原版 Mob 在目标死亡/移除后并不会自动清空 target 字段——这个清理职责由
 * TargetGoal 系列 AI 在 stop() 里调用 setTarget(null) 承担。而纷争标记
 * （ConflictMarkEffect）通过 mob.setTarget(target) 绕过目标选择 AI 直接注入目标，
 * 没有对应的 TargetGoal 兜底，一旦被标记实体死亡/消失，怪物手上的 target 就变成
 * 指向已死亡实体的悬空引用，远程怪（骷髅/掠夺者等）会继续朝旧目标位置射击。
 *
 * 这里在 tick 头部统一校验：目标死亡或已移除时立即清空，等价于把 TargetGoal.stop()
 * 里的清理做成无条件兜底，不影响原版正常仇恨流程。
 */
@Mixin(Mob.class)
public class MobDeadTargetClearMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void pd$clearDeadTarget(CallbackInfo ci) {
        Mob self = (Mob) (Object) this;
        LivingEntity target = self.getTarget();
        if (target != null && !target.isAlive()) {
            self.setTarget(null);
        }
    }
}
