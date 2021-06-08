package alphamode.core.nebula.gases;

import alphamode.core.nebula.NebulaRegistry;
import org.jetbrains.annotations.Nullable;

import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;

public class Gas {

    @Nullable
    private String translationKey;
    private final int color;

    //Only to make it show up in REI


    public Gas(int gasColor) {
        color = gasColor;
    }

    public Gas() {
        color = 0xFFFFFFFF;
    }



    public int getColor() {
        return color;
    }

    public String getTranslationKey() {
        if (this.translationKey == null) {
            this.translationKey = Util.createTranslationKey("gas", NebulaRegistry.GAS.getId(this));
        }
        return this.translationKey;
    }

    public Text getName() {
        return new TranslatableText(getTranslationKey());
    }

}
