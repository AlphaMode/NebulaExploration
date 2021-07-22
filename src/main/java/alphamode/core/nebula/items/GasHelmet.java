package alphamode.core.nebula.items;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;

public class GasHelmet extends ArmorItem {
    public GasHelmet(ArmorMaterial material, Settings settings) {
        super(material, EquipmentSlot.HEAD, settings);
    }
}
