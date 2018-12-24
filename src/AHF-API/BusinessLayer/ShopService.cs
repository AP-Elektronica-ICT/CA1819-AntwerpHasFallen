using DataLayer;
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

        public ShopService(GameContext context)
        {
            this.context = context;
        }

        public List<ShopItem> GetShopItems()
        {
            return context.ShopItems.Include(s => s.Item).ToList();
        }

        public List<Item> GetIngredients()
        {
            return context.Ingredients.Where(i => i.Type == Item.TYPE_INGREDIENT).ToList();
        }

        public List<Item> GetItems()
        {
            return context.Items.Where(i => i.Type == Item.TYPE_ITEM).ToList();
        }
    }
}
