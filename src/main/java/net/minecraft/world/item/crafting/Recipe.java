package net.minecraft.world.item.crafting;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public interface Recipe<C extends Container> {
   boolean matches(C p_44002_, Level p_44003_);

   ItemStack assemble(C p_44001_, RegistryAccess p_267165_);

   boolean canCraftInDimensions(int p_43999_, int p_44000_);

   ItemStack getResultItem(RegistryAccess p_267052_);

   default NonNullList<ItemStack> getRemainingItems(C p_44004_) {
      NonNullList<ItemStack> nonnulllist = NonNullList.withSize(p_44004_.getContainerSize(), ItemStack.EMPTY);

      for(int i = 0; i < nonnulllist.size(); ++i) {
         Item item = p_44004_.getItem(i).getItem();
         if (item.hasCraftingRemainingItem()) {
            nonnulllist.set(i, new ItemStack(item.getCraftingRemainingItem()));
         }
      }

      return nonnulllist;
   }

   default NonNullList<Ingredient> getIngredients() {
      return NonNullList.create();
   }

   default boolean isSpecial() {
      return false;
   }

   default boolean showNotification() {
      return true;
   }

   default String getGroup() {
      return "";
   }

   default ItemStack getToastSymbol() {
      return new ItemStack(Blocks.CRAFTING_TABLE);
   }

   RecipeSerializer<?> getSerializer();

   RecipeType<?> getType();

   default boolean isIncomplete() {
      NonNullList<Ingredient> nonnulllist = this.getIngredients();
      return nonnulllist.isEmpty() || nonnulllist.stream().anyMatch((p_151268_) -> {
         return p_151268_.getItems().length == 0;
      });
   }
}