using System;
using System.Collections.Generic;
using System.Text;

namespace DataLayer.Model.InventoryModel
{
    public class Inventory
    {
        public Inventory()
        {
            ShopItems = new List<ShopItem>();
            Ingredients = new List<Ingredient>();
        }

        public int Id { get; set; }
        public List<ShopItem> ShopItems { get; set; }
        public List<Ingredient> Ingredients { get; set; }
    }
}
