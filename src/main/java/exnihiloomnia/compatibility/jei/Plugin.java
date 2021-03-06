package exnihiloomnia.compatibility.jei;

import exnihiloomnia.blocks.ENOBlocks;
import exnihiloomnia.compatibility.jei.categories.barrel.BarrelCraftingRecipeCategory;
import exnihiloomnia.compatibility.jei.categories.barrel.BarrelCraftingRecipeHandler;
import exnihiloomnia.compatibility.jei.categories.barrel.JEIBarrelCraftingRecipe;
import exnihiloomnia.compatibility.jei.categories.compost.CompostRecipeCategory;
import exnihiloomnia.compatibility.jei.categories.compost.CompostRecipeHandler;
import exnihiloomnia.compatibility.jei.categories.compost.JEICompostRecipe;
import exnihiloomnia.compatibility.jei.categories.crook.CrookRecipeCategory;
import exnihiloomnia.compatibility.jei.categories.crook.CrookRecipeHandler;
import exnihiloomnia.compatibility.jei.categories.crook.JEICrookRecipe;
import exnihiloomnia.compatibility.jei.categories.crucible.CrucibleRecipeCategory;
import exnihiloomnia.compatibility.jei.categories.crucible.CrucibleRecipeHandler;
import exnihiloomnia.compatibility.jei.categories.crucible.JEICrucibleRecipe;
import exnihiloomnia.compatibility.jei.categories.hammer.HammerRecipeCategory;
import exnihiloomnia.compatibility.jei.categories.hammer.HammerRecipeHandler;
import exnihiloomnia.compatibility.jei.categories.hammer.JEIHammerRecipe;
import exnihiloomnia.compatibility.jei.categories.sieve.JEISieveRecipe;
import exnihiloomnia.compatibility.jei.categories.sieve.SieveRecipeCategory;
import exnihiloomnia.compatibility.jei.categories.sieve.SieveRecipeHandler;
import exnihiloomnia.items.ENOItems;
import exnihiloomnia.registries.barrel.BarrelCraftingRegistry;
import exnihiloomnia.registries.barrel.BarrelCraftingTrigger;
import exnihiloomnia.registries.composting.CompostRegistry;
import exnihiloomnia.registries.composting.CompostRegistryEntry;
import exnihiloomnia.registries.crook.CrookRegistry;
import exnihiloomnia.registries.crook.CrookRegistryEntry;
import exnihiloomnia.registries.crucible.CrucibleRegistry;
import exnihiloomnia.registries.crucible.CrucibleRegistryEntry;
import exnihiloomnia.registries.hammering.HammerRegistry;
import exnihiloomnia.registries.hammering.HammerRegistryEntry;
import exnihiloomnia.registries.sifting.SieveRegistry;
import exnihiloomnia.registries.sifting.SieveRegistryEntry;
import exnihiloomnia.util.enums.EnumWood;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;

@JEIPlugin
public class Plugin implements IModPlugin{
    private static final ItemStack dirt = new ItemStack(Blocks.DIRT);

    @Override
    public void registerIngredients(IModIngredientRegistration registry) {
    }

    @Override
    public void register(@Nonnull IModRegistry registry) {
        registry.addRecipeCategories(
                new SieveRecipeCategory(registry.getJeiHelpers().getGuiHelper()),
                new HammerRecipeCategory(registry.getJeiHelpers().getGuiHelper()),
                new CrookRecipeCategory(registry.getJeiHelpers().getGuiHelper()),
                new CrucibleRecipeCategory(registry.getJeiHelpers().getGuiHelper()),
                new CompostRecipeCategory(registry.getJeiHelpers().getGuiHelper()),
                new BarrelCraftingRecipeCategory(registry.getJeiHelpers().getGuiHelper())
        );

        registry.addRecipeHandlers(
                new SieveRecipeHandler(),
                new HammerRecipeHandler(),
                new CrookRecipeHandler(),
                new CrucibleRecipeHandler(),
                new CompostRecipeHandler(),
                new BarrelCraftingRecipeHandler()
        );

        registerSieveRecipes(registry);
        registerCrucibleRecipes(registry);
        registerHammerRecipes(registry);
        registerCrookRecipes(registry);

        registerCompostRecipes(registry);
        registerFluidCraftingRecipes(registry);

        registerCraftingItems(registry);

    }

    private void registerCraftingItems(IModRegistry registry) {
        for (EnumWood wood : EnumWood.values()) {
            registry.addRecipeCategoryCraftingItem(new ItemStack(ENOBlocks.SIEVE_WOOD, 1, wood.getMetadata()), SieveRecipeCategory.UID);
            registry.addRecipeCategoryCraftingItem(new ItemStack(ENOBlocks.BARREL_WOOD, 1, wood.getMetadata()), CompostRecipeCategory.UID);
            registry.addRecipeCategoryCraftingItem(new ItemStack(ENOBlocks.BARREL_WOOD, 1, wood.getMetadata()), BarrelCraftingRecipeCategory.UID);
        }
        for (EnumDyeColor color : EnumDyeColor.values()) {
            registry.addRecipeCategoryCraftingItem(new ItemStack(ENOBlocks.BARREL_GLASS_COLORED, 1, color.getMetadata()), CompostRecipeCategory.UID);
            registry.addRecipeCategoryCraftingItem(new ItemStack(ENOBlocks.BARREL_GLASS_COLORED, 1, color.getMetadata()), BarrelCraftingRecipeCategory.UID);
        }
        registry.addRecipeCategoryCraftingItem(new ItemStack(ENOBlocks.BARREL_GLASS), CompostRecipeCategory.UID);
        registry.addRecipeCategoryCraftingItem(new ItemStack(ENOBlocks.BARREL_GLASS), BarrelCraftingRecipeCategory.UID);
        registry.addRecipeCategoryCraftingItem(new ItemStack(ENOBlocks.BARREL_STONE), CompostRecipeCategory.UID);
        registry.addRecipeCategoryCraftingItem(new ItemStack(ENOBlocks.BARREL_STONE), BarrelCraftingRecipeCategory.UID);

        registry.addRecipeCategoryCraftingItem(new ItemStack(ENOItems.HAMMER_DIAMOND), HammerRecipeCategory.UID);
        registry.addRecipeCategoryCraftingItem(new ItemStack(ENOItems.HAMMER_GOLD), HammerRecipeCategory.UID);
        registry.addRecipeCategoryCraftingItem(new ItemStack(ENOItems.HAMMER_IRON), HammerRecipeCategory.UID);
        registry.addRecipeCategoryCraftingItem(new ItemStack(ENOItems.HAMMER_STONE), HammerRecipeCategory.UID);
        registry.addRecipeCategoryCraftingItem(new ItemStack(ENOItems.HAMMER_WOOD), HammerRecipeCategory.UID);

        registry.addRecipeCategoryCraftingItem(new ItemStack(ENOBlocks.CRUCIBLE), CrucibleRecipeCategory.UID);

        registry.addRecipeCategoryCraftingItem(new ItemStack(ENOItems.CROOK_BONE), CrookRecipeCategory.UID);
        registry.addRecipeCategoryCraftingItem(new ItemStack(ENOItems.CROOK_WOOD), CrookRecipeCategory.UID);
    }

    private void registerCompostRecipes(IModRegistry registry) {
        ArrayList<JEICompostRecipe> compostRecipes = new ArrayList<>();

        for (CompostRegistryEntry entry : CompostRegistry.INSTANCE.getEntries().values()) {
            Block block = Block.getBlockFromItem(entry.getInput().getItem());

            if (block != null && block.getMaterial(null) == Material.LEAVES)
                compostRecipes.add(new JEICompostRecipe(entry, entry.getInput(), new ItemStack(Blocks.DIRT, 1, 2)));
            else if (entry.getInput().getItem() == Items.GOLDEN_APPLE)
                compostRecipes.add(new JEICompostRecipe(entry, entry.getInput(), new ItemStack(Blocks.GRASS)));
            else if (entry.getInput().getItem() == Items.ROTTEN_FLESH)
                compostRecipes.add(new JEICompostRecipe(entry, entry.getInput(), new ItemStack(Blocks.DIRT, 1, 1)));
            else
                compostRecipes.add(new JEICompostRecipe(entry, entry.getInput(), dirt));
        }
        registry.addRecipes(compostRecipes);
    }

    private void registerSieveRecipes(IModRegistry registry) {
        ArrayList<JEISieveRecipe> sieveRecipes = new ArrayList<>();

        for (SieveRegistryEntry entry : SieveRegistry.getEntryMap().values())
            sieveRecipes.add(new JEISieveRecipe(entry));

        registry.addRecipes(sieveRecipes);
    }

    private void registerCrucibleRecipes(IModRegistry registry) {
        ArrayList<JEICrucibleRecipe> crucibleRecipes = new ArrayList<>();
        for (CrucibleRegistryEntry entry : CrucibleRegistry.INSTANCE.getEntries().values()) {
            crucibleRecipes.add(new JEICrucibleRecipe(entry));
        }
        registry.addRecipes(crucibleRecipes);
    }

    private void registerHammerRecipes(IModRegistry registry) {
        ArrayList<JEIHammerRecipe> hammerRecipes = new ArrayList<>();

        for (HammerRegistryEntry entry : HammerRegistry.INSTANCE.getEntries().values()) {
            hammerRecipes.add(new JEIHammerRecipe(entry));
        }
        registry.addRecipes(hammerRecipes);
    }

    private void registerCrookRecipes(IModRegistry registry) {
        ArrayList<JEICrookRecipe> crookRecipes = new ArrayList<>();

        for (CrookRegistryEntry entry : CrookRegistry.INSTANCE.getEntries().values()) {
            crookRecipes.add(new JEICrookRecipe(entry));
        }
        registry.addRecipes(crookRecipes);
    }

    private void registerFluidCraftingRecipes(IModRegistry registry) {
        ArrayList<JEIBarrelCraftingRecipe> fluidCraftingRecipes = new ArrayList<>();

        for (BarrelCraftingTrigger entry : BarrelCraftingRegistry.INSTANCE.getEntries().values()) {
            fluidCraftingRecipes.add(new JEIBarrelCraftingRecipe(entry));
        }
        registry.addRecipes(fluidCraftingRecipes);
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
    }

    @Override
    public void onRuntimeAvailable(@Nonnull IJeiRuntime jeiRuntime) {
    }
}
