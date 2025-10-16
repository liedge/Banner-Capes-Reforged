package liedge.bannercapes.datagen;

import liedge.bannercapes.BannerCapes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import top.theillusivec4.curios.api.CuriosDataProvider;

import java.util.concurrent.CompletableFuture;

class CuriosDataGen extends CuriosDataProvider
{
    CuriosDataGen(PackOutput output, ExistingFileHelper helper, CompletableFuture<HolderLookup.Provider> registries)
    {
        super(BannerCapes.MODID, output, helper, registries);
    }

    @Override
    public void generate(HolderLookup.Provider registries, ExistingFileHelper helper)
    {
        createEntities("capes").addPlayer().addSlots("back");
    }
}
