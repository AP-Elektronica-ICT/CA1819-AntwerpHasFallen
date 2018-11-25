using DataLayer;
using DataLayer.Model.InventoryModel;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace BusinessLayer
{
    public class InventoryService
    {
        private readonly GameContext context;

        public InventoryService(GameContext context)
        {
            this.context = context;
        }

        public List<ShopItem> GetShopItems()
        {
            return context.ShopItems.ToList();
        }

        public List<Ingredient> GetIngredients()
        {
            return context.Ingredients.ToList();
        }

        public Inventory getInventory(int id)
        {
            try
            {
                return context.Inventories.Include(i => i.Ingredients).Include(i => i.ShopItems).Single(i => i.Id == id);
            }catch (ArgumentNullException)
            {
                return null;
            }
            catch (InvalidOperationException)
            {
                return null;
            }
        }

        public Inventory addShopItem(int inventoryId, int itemId)
        {
            Inventory inventory = getInventory(inventoryId);
            ShopItem item = context.ShopItems.Find(itemId);

            if (inventory != null && item != null)
            {
                inventory.ShopItems.Add(item);
                context.SaveChanges();
                return inventory;
            }
            return null;
        }

        public Inventory addIngredient(int inventoryId, int ingredientId)
        {
            Inventory inventory = getInventory(inventoryId);
            Ingredient ingredient = context.Ingredients.Find(ingredientId);

            if (inventory != null && ingredient != null)
            {
                inventory.Ingredients.Add(ingredient);
                context.SaveChanges();
                return inventory;
            }
            return null;
        }
    }
}
