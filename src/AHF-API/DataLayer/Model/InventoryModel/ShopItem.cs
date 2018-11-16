using System;
using System.Collections.Generic;
using System.Text;

namespace DataLayer.Model.InventoryModel
{
    public abstract class ShopItem : InventoryItem
    {
        public string Description { get; set; }
        public abstract void Use();
    }
}
