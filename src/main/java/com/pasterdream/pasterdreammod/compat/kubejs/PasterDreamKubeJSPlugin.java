package com.pasterdream.pasterdreammod.compat.kubejs;

import com.pasterdream.pasterdreammod.PasterDreamTipsManager;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;

import java.util.List;

public class PasterDreamKubeJSPlugin extends KubeJSPlugin {

    @Override
    public void registerBindings(BindingsEvent event) {
        event.add("PasterDreamTips", new TipsBinding());
    }

    public static class TipsBinding {

        public List<String> getTips() {
            return PasterDreamTipsManager.INSTANCE.getActiveTips();
        }

        public List<String> getCustomTips() {
            return PasterDreamTipsManager.INSTANCE.getCustomTips();
        }

        public List<String> getDefaultTips() {
            return PasterDreamTipsManager.INSTANCE.getDefaultTips();
        }

        public void add(String tip) {
            PasterDreamTipsManager.INSTANCE.addTip(tip);
        }

        public void remove(int index) {
            PasterDreamTipsManager.INSTANCE.removeTip(index);
        }

        public void clear() {
            PasterDreamTipsManager.INSTANCE.clearCustomTips();
        }

        public void reset() {
            PasterDreamTipsManager.INSTANCE.resetToDefaults();
        }
    }
}
