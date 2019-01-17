using DataLayer;
using DataLayer.Model;
using DataLayer.Model.InventoryModel;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace BusinessLayer
{
    public class ShopService
    {
        private readonly GameContext context;
        private readonly InventoryService inventoryService;

        public ShopService(GameContext context)
        {
            this.context = context;
            this.inventoryService = new InventoryService(context);
        }

        public List<ShopItem> GetShopItems()
        {
            return context.ShopItems.Include(s => s.Item).ToList();
        }

        public ShopItem GetShopItemById(int id)
        {
            return context.ShopItems.Include(i => i.Item).SingleOrDefault(i => i.Id == id);
        }

        public List<Item> GetIngredients()
        {
            return context.Ingredients.Where(i => i.Type == Item.TYPE_INGREDIENT).ToList();
        }

        public List<Item> GetItems()
        {
            return context.Items.Where(i => i.Type == Item.TYPE_ITEM).ToList();
        }

        public Inventory buyShopItem(int shopItemId, int teamId)
        {
            ShopItem shopItem = GetShopItemById(shopItemId);
            Item item = null;
            if (shopItem != null)
                item = shopItem.Item;
            else return null;

            Team team = context.Teams.Include(t => t.Inventory).SingleOrDefault(t => t.Id == teamId);
            if (team != null)
            {
                if (team.Money - shopItem.Price >= 0)
                {
                    team.Money -= shopItem.Price;
                    if (shopItem.Item.Type == Item.TYPE_INGREDIENT)
                        team.Inventory = inventoryService.addIngredient(team.Inventory.Id, shopItem.Item.Id);
                    if (shopItem.Item.Type == Item.TYPE_ITEM)
                        team.Inventory = inventoryService.addItem(team.Inventory.Id, shopItem.Item.Id);
                    context.SaveChanges();
                    return team.Inventory;
                }
                else return null;
            }
            else return null;
        }
    }
}
