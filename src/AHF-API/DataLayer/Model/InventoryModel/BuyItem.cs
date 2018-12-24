using System;
using System.Collections.Generic;
using System.Text;

namespace DataLayer.Model.InventoryModel
{
    public class BuyItem
    {
        public int Id { get; set; }
        public int Price { get; set; }
        public Item Item { get; set; }
    }
}
