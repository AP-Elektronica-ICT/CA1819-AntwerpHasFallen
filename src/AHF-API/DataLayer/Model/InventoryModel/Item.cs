using System;
using System.Collections.Generic;
using System.Text;

namespace DataLayer.Model.InventoryModel
{
    public abstract class Item
    {
        public int Id { get; set; }
        public string Name { get; set; }
    }
}
