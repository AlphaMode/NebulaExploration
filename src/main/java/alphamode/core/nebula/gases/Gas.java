package alphamode.core.nebula.gases;

import alphamode.core.nebula.NebulaRegistry;
import org.jetbrains.annotations.Nullable;

import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;

public class Gas {
    @Nullable
    private String translationKey;
    public String getTranslationKey() {
        if (this.translationKey == null) {
            this.translationKey = Util.createTranslationKey("item", NebulaRegistry.GAS.getId(this));
        }
        return this.translationKey;
    }

    public Text getName() {
        return new TranslatableText(translationKey);
    }
}
