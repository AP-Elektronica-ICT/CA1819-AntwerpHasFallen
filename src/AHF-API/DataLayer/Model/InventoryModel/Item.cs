using System;
using System.Collections.Generic;
using System.Text;

namespace DataLayer.Model.InventoryModel
{
    public class Item
    {
        public static string TYPE_INGREDIENT { get; } = "ingredient";
        public static string TYPE_ITEM { get; } = "item";
        public int Id { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
        public string Type { get; set; }
    }
}
