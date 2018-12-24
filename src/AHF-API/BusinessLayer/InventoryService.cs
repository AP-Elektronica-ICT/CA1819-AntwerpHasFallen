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

        public Inventory getInventory(int id)
        {
            try
            {
                return context.Inventories.Include(i => i.Ingredients).ThenInclude(i=>i.Item).Include(i => i.Items).ThenInclude(i => i.Item).Single(i => i.Id == id);
            }catch (ArgumentNullException)
            {
                return null;
            }
            catch (InvalidOperationException)
            {
                return null;
            }
        }

        public Inventory addItem(int inventoryId, int itemId)
        {
            Inventory inventory = getInventory(inventoryId);
            Item shopItem = context.Items.Find(itemId);

            if (inventory != null && shopItem != null)
            {
                foreach(InventoryItem item in inventory.Items)
                {
                    if(item.Item.Id == itemId)
                    {
                        item.Quantity++;
                        context.SaveChanges();
                        return inventory;
                    }
                }
                InventoryItem newItem = new InventoryItem()
                {
                    Item = context.Items.Find(itemId),
                    Quantity = 1
                };
                inventory.Items.Add(newItem);
                context.SaveChanges();
                return inventory;
            }
            return null;
        }

        public Inventory addIngredient(int inventoryId, int ingredientId)
        {
            Inventory inventory = getInventory(inventoryId);
            Item ingredient = context.Ingredients.Find(ingredientId);

            if (inventory != null && ingredient != null)
            {
                foreach (InventoryItem item in inventory.Ingredients)
                {
                    if (item.Item.Id == ingredientId)
                    {
                        item.Quantity++;
                        context.SaveChanges();
                        return inventory;
                    }
                }
                InventoryItem newItem = new InventoryItem()
                {
                    Item = context.Ingredients.Find(ingredientId),
                    Quantity = 1
                };
                inventory.Ingredients.Add(newItem);
                context.SaveChanges();
                return inventory;
            }
            return null;
        }
    }
}
