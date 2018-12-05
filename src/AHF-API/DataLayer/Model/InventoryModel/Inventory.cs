using System;
using System.Collections.Generic;
using System.Text;

namespace DataLayer.Model.InventoryModel
{
    public class Inventory
    {
        public Inventory()
        {
            ShopItems = new List<InventoryItem>();
            Ingredients = new List<InventoryItem>();
        }

        public int Id { get; set; }
        public List<InventoryItem> ShopItems { get; set; }
        public List<InventoryItem> Ingredients { get; set; }
    }
}
