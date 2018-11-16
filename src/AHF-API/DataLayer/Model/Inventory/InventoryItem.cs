using System;
using System.Collections.Generic;
using System.Text;

namespace DataLayer.Model.Inventory
{
    public abstract class InventoryItem
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public int Quantity { get; set; }
    }
}
