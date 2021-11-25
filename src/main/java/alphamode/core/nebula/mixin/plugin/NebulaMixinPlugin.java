package alphamode.core.nebula.mixin.plugin;

import alphamode.core.nebula.NebulaMod;
import org.apache.logging.log4j.LogManager;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import java.util.List;
import java.util.Set;

public class NebulaMixinPlugin implements IMixinConfigPlugin {

    public static AbstractInsnNode findFirstInstructionAfter(MethodNode method, int opCode, int startIndex) {
        for (int i = Math.max(0, startIndex); i < method.instructions.size(); i++) {
            AbstractInsnNode ain = method.instructions.get(i);
            if (ain.getOpcode() == opCode) {
                return ain;
            }
        }
        return null;
    }

    /**
     * Builds a new {@link InsnList} out of the specified AbstractInsnNodes
     * @param nodes The nodes you want to add
     * @return A new list with the nodes
     */
    public static InsnList listOf(AbstractInsnNode... nodes) {
        InsnList list = new InsnList();
        for (AbstractInsnNode node : nodes)
            list.add(node);
        return list;
    }

    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        LogManager.getLogger().info(targetClassName);
        if(targetClassName.equals("net.minecraft.client.sound.MusicTracker")) {
            for(MethodNode node : targetClass.methods) {
                if(node.name.equals("tick")) {
                    node.instructions.insert(findFirstInstructionAfter(node, Opcodes.INVOKEVIRTUAL, 0), listOf(
                                    new MethodInsnNode(
                                            Opcodes.INVOKESTATIC,
                                            "alphamode/core/nebula/NebulaMod",
                                            "music",
                                            "(Lnet/minecraft/sound/MusicSound;)Lnet/minecraft/sound/MusicSound;",
                                            false
                                    )
                            )
                    );
                }
            }
        }
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
